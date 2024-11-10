package com.yokke.usermanagement.auth;

import com.yokke.base.exception.response_status.NotFoundException;
import com.yokke.usermanagement.auth.request.*;
import com.yokke.usermanagement.auth.response.LoginResponse;
import com.yokke.usermanagement.auth.response.RegisterResponse;
import com.yokke.usermanagement.auth.response.ResetPasswordResponse;
import com.yokke.usermanagement.auth.response.SetupPasswordResponse;
import com.yokke.usermanagement.auth.token.TokenValidity;
import com.yokke.usermanagement.auth.token.TokenValidityRepository;
import com.yokke.usermanagement.role.RoleDTO;
import com.yokke.usermanagement.role.RoleRepository;
import com.yokke.usermanagement.role.RoleService;
import com.yokke.usermanagement.security.CookieAuthenticationService;
import com.yokke.usermanagement.security.JwtService;
import com.yokke.usermanagement.user_account.*;
import com.yokke.usermanagement.user_verification.UserVerification;
import com.yokke.usermanagement.user_verification.UserVerificationRepository;
import com.yokke.usermanagement.user_verification.UserVerificationService;
import com.yokke.usermanagement.user_verification.type.UserVerificationStatusEnum;
import com.yokke.usermanagement.user_verification.type.UserVerificationTypeEnum;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserAccountRepository userAccountRepository;
    private final JwtService jwtService;
    private final UserVerificationService userVerificationService;
    private final UserAccountService userAccountService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserAccountMapper userAccountMapper;
    private final TokenValidityRepository tokenValidityRepository;
    private final UserVerificationRepository userVerificationRepository;
    private final CookieAuthenticationService cookieAuthenticationService;
    private final RoleService roleService;


    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        UserAccount userAccount;
        if (request.getEmail() != null) {
            userAccount = userAccountRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Username or password is wrong, please try again"));
        } else {
            userAccount = userAccountRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Username or password is wrong, please try again"));
        }
        if (userAccount.getLoginAttempt() == 0) {
            userVerificationService.sendLockedUser(
                    userAccount.getUsername(),
                    userAccount.getEmail()
            );
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Your account is locked, please contact administrator"
            );
        }
        if (!passwordEncoder.matches(request.getPassword(), userAccount.getPassword())) {
            userAccount.setLoginAttempt(userAccount.getLoginAttempt() - 1);
            userAccountRepository.save(userAccount);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Username or password is wrong, please try again");
        }

        String accessToken = jwtService.generateAccessToken(userAccount);
        String refreshToken = jwtService.generateRefreshToken(userAccount);

        cookieAuthenticationService.setTokenCookies(response, accessToken, refreshToken);
        tokenValidityRepository.save(new TokenValidity(
                userAccount.getUserAccountId(),
                accessToken
        ));
//        userAccount.setToken(accessToken);
        userAccount.setLoginAttempt(5);
        userAccountRepository.save(userAccount);
        UserAccountDTO userAccountResponse = userAccountMapper.mapToDTO(userAccount, new UserAccountDTO());

        return LoginResponse.builder()
                .userAccount(userAccountResponse)
                .build();
    }

    @Cacheable(value = "userAccounts"

            , key = "T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().name + '-' + 'status'"
    )
    public UserAccountDTO getAuthStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }
        UserAccount userAccount;
        try {
            userAccount = (UserAccount) authentication.getPrincipal();

        } catch (ClassCastException c) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    ""
            );
        }
        UserAccount res = userAccountRepository.findByUserAccountId(userAccount.getUserAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<RoleDTO> role = roleService.readByUserAccountId(res.getUserAccountId());

        return userAccountMapper.mapToDTO(
                res,
                new UserAccountDTO(),
                role
        );
    }

    public void logout(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authenticated");
        }
        UserAccount userAccount;
        try {
            userAccount = (UserAccount) authentication.getPrincipal();

        } catch (ClassCastException c) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    ""
            );
        }
        UserAccount res = userAccountRepository.findByUserAccountId(userAccount.getUserAccountId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        tokenValidityRepository.deleteById(res.getUserAccountId());
        cookieAuthenticationService.clearTokenCookies(response);
    }


//    public ModelAndView confirmEmail(String verificationToken) {
//        UserVerificationDTO userAccount = userVerificationService.verifyEmail(verificationToken);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("message", "Email verified successfully");
//        modelAndView.addObject("loginUrl", loginUrl);
//        modelAndView.setViewName("email-verification");
//        return modelAndView;
//    }

    public SetupPasswordResponse setupPassword(SetupPasswordRequest setupPasswordRequest) {
        UserVerification userVerification = userVerificationRepository.findByVerificationCode(setupPasswordRequest.getToken())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Invalid Token"
                        )
                );

        if (userVerification.getIsVerified()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Setup password has been verified for this request, please direct to login."
            );
        }
        UserAccount userAccount = userAccountRepository.findByUserAccountId(userVerification.getUserAccount().getUserAccountId())
                .orElseThrow(
                        () -> new NotFoundException("OKK")
                );
//        if (isPasswordHasBeenUsed(setupPasswordRequest.getPassword())) {
//            throw new ResponseStatusException(
//                    HttpStatus.BAD_REQUEST,
//                    "Please use new password that hasn't been used"
//            );
//        }
        userVerification.setIsVerified(true);
        userVerification.setVerificationStatus(UserVerificationStatusEnum.VERIFIED.name());
        userVerification.setPassword(passwordEncoder.encode(setupPasswordRequest.getPassword()));
        userVerificationRepository.save(userVerification);

        userAccount.setPasswordDateChanged(LocalDateTime.now());
        userAccount.setPassword(passwordEncoder.encode(setupPasswordRequest.getPassword()));
        userAccountRepository.save(userAccount);

        return SetupPasswordResponse.builder()
                .message("Success setting up password")
                .build();

    }


    public RegisterResponse forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {

        long countForgotPassword = userVerificationRepository.countByVerificationTypeAndCreatedDate(
                UserVerificationTypeEnum.FORGOT_PASSWORD.name(),
                OffsetDateTime.now().minusHours(24),
                forgotPasswordRequest.getEmail()
        );

        if (countForgotPassword > 5) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Too many forgot password request in last 24 hours, please try again later."
            );
        }

        UserAccount userAccount = userAccountRepository.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        userVerificationService.sendForgotPasswordEmail(userAccount);

        return RegisterResponse.builder()
                .message("Password reset link sent to your email")
                .build();
    }

    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
        UserVerification userVerification = userVerificationRepository.findByVerificationCodeAndVerificationType(
                resetPasswordRequest.getToken(),
                UserVerificationTypeEnum.FORGOT_PASSWORD.name()
        ).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Token is invalid for reset password."
                )
        );
        if (userVerification.getIsVerified()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Reset password has been verified for this request, please direct to login."
            );
        }
        UserAccount userAccount = userAccountRepository.findUserAccountByUserAccountId(userVerification.getUserAccount().getUserAccountId())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "User account not found."
                        )
                );
        if (isPasswordHasBeenUsed(resetPasswordRequest.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Please use new password that hasn't been used"
            );
        }
        userVerification.setIsVerified(true);
        userVerification.setVerificationStatus(UserVerificationStatusEnum.VERIFIED.name());
        userVerification.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        userVerificationRepository.save(userVerification);

        if (userAccount.getLoginAttempt() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Your account is locked, please contact administrator"
            );
        }
        userAccount.setPasswordDateChanged(LocalDateTime.now());
        userAccount.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        userAccountRepository.save(userAccount);
        return ResetPasswordResponse.builder().message("Password has been successfully reset.")
                .build();

    }

    public UserAccountDTO changePassword(ChangePasswordRequest changePasswordRequest) {
        UserAccount userAccount = userAccountService.getCurrentUserAccount();

        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(),
                userAccount.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The old password is incorrect.");
        }

//        if (changePasswordRequest.getPassword().matches(
//                changePasswordRequest.getOldPassword())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The new password cannot be the same as the old password.");
//        }

        if (isPasswordHasBeenUsed(changePasswordRequest.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please choose a new password that has not been used previously.");
        }
        userAccount.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
        userAccount.setPasswordDateChanged(LocalDateTime.now());
        userAccountRepository.save(userAccount);

        UserVerification userVerification = UserVerification.builder()
                .verificationType(UserVerificationTypeEnum.PASSWORD_CHANGE.name())
                .verificationCode(null)
                .verificationStatus(UserVerificationStatusEnum.VERIFIED.name())
                .verificationExpiry(OffsetDateTime.now())
                .isVerified(true)
                .password(passwordEncoder.encode(changePasswordRequest.getPassword()))
                .userAccount(userAccount)
                .build();
        userVerificationRepository.save(userVerification);
        return UserAccountDTO.builder()
                .username(userAccount.getUsername())
                .email(userAccount.getEmail())
                .build();
    }

    public Boolean isPasswordHasBeenUsed(String password) {
        List<UserVerification> userVerifications = userVerificationRepository.findFirst4Password(
                List.of(new String[]{UserVerificationTypeEnum.EMAIL_VERIFICATION.name(), UserVerificationTypeEnum.PASSWORD_CHANGE.name(), UserVerificationTypeEnum.FORGOT_PASSWORD.name()}),
                true,
                UserVerificationStatusEnum.VERIFIED.name(),
                PageRequest.of(0, 4)
        );
        return userVerifications.stream()
                .anyMatch(userVerification -> passwordEncoder.matches(
                        password, userVerification.getPassword()
                ));
    }

    public void raiseIfEmailOrUsernameExist(String email, String username) {
        if (userAccountRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Email %s already exist", email));
        } else if (userAccountRepository.existsByUsernameIgnoreCase(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    String.format("Username %s already exist", username));
        }
    }
}
