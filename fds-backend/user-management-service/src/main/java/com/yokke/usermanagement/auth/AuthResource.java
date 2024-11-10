package com.yokke.usermanagement.auth;

import com.yokke.usermanagement.auth.request.*;
import com.yokke.usermanagement.auth.response.LoginResponse;
import com.yokke.usermanagement.auth.response.RegisterResponse;
import com.yokke.usermanagement.auth.response.ResetPasswordResponse;
import com.yokke.usermanagement.auth.response.SetupPasswordResponse;
import com.yokke.usermanagement.user_account.UserAccountDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/fds/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tags(
        value = {
                @Tag(name = "User Management"),
        }
)
public class AuthResource {
    private final AuthService authService;
//    private final RabbitTemplate rabbitTemplate;

//    @PostMapping("/register")
//    public ResponseEntity<RegisterResponse> register(
//            @RequestBody @Valid final RegisterRequest request) {
//        return ResponseEntity.ok(authService.register(request));
//    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginRequest request,
            HttpServletResponse httpServletResponse
    ) {
        return ResponseEntity.ok(authService.login(request, httpServletResponse));
    }

    @GetMapping("/status")
    public ResponseEntity<UserAccountDTO> getAuthStatus() {
        return ResponseEntity.ok(authService.getAuthStatus());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.noContent().build();

    }

//    @GetMapping("/confirm-email")
//    public ModelAndView confirmEmail(
//            @RequestParam("token") String verificationToken) {
//        return authService.confirmEmail(verificationToken);
//    }

    @PostMapping("/setup-password")
    public ResponseEntity<SetupPasswordResponse> setupPassword(
            @RequestBody @Valid
            SetupPasswordRequest setupPasswordRequest
    ) {
        return ResponseEntity.ok(authService.setupPassword(setupPasswordRequest));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<RegisterResponse> forgotPassword(
            @RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest) {
        return ResponseEntity.ok(authService.forgotPassword(forgotPasswordRequest));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(
            @RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        return ResponseEntity.ok(authService.resetPassword(resetPasswordRequest));
    }

    @PostMapping("/change-password")
    public ResponseEntity<UserAccountDTO> changePassword(
            @RequestBody
            @Valid ChangePasswordRequest changePasswordRequest
    ) {
        return ResponseEntity.ok(authService.changePassword(changePasswordRequest));
    }


//    @PostMapping("/send")
//    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
//        rabbitTemplate.convertAndSend("email_exchange", "email_routing_key", emailRequest);
//        return ResponseEntity.ok("Email queued successfully");
//    }

}
