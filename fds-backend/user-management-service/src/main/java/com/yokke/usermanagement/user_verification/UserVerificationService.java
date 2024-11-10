package com.yokke.usermanagement.user_verification;

import com.yokke.base.exception.response_status.NotFoundException;
import com.yokke.base.service.BaseService;
import com.yokke.usermanagement.user_account.UserAccount;
import com.yokke.usermanagement.user_account.UserAccountRepository;
import com.yokke.usermanagement.user_verification.type.UserVerificationStatusEnum;
import com.yokke.usermanagement.user_verification.type.UserVerificationTypeEnum;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor

public class UserVerificationService extends BaseService {

    private final UserVerificationRepository userVerificationRepository;
    private final UserAccountRepository userAccountRepository;
    @Autowired
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final UserVerificationMapper userVerificationMapper;

    @Value(value = "${app.reset-password-url}")
    private String resetPasswordUrl;


    @Value(value = "${app.setup-password-url}")
    private String setupPasswordUrl;

    public List<UserVerificationDTO> findAll() {
        final List<UserVerification> userVerifications = userVerificationRepository.findAll(Sort.by("userVerificationId"));
        return userVerifications.stream()
                .map(userVerification -> userVerificationMapper.mapToDTO(userVerification, new UserVerificationDTO()))
                .toList();
    }

    public UserVerificationDTO get(final Integer userVerificationId) {
        return userVerificationRepository.findById(userVerificationId)
                .map(userVerification -> userVerificationMapper.mapToDTO(userVerification, new UserVerificationDTO()))
                .orElseThrow(() -> new NotFoundException("OKK"));
    }

    public String create(final UserVerificationDTO userVerificationDTO) {
        final UserVerification userVerification = new UserVerification();
        userVerificationMapper.mapToEntity(userVerificationDTO, userVerification);
        return userVerificationRepository.save(userVerification).getUserVerificationId();
    }

    public String create(final UserAccount userAccount, final String code) {
        final UserVerification userVerification = new UserVerification();
        userVerification.setIsVerified(false);
        userVerification.setVerificationCode(code);
        userVerification.setVerificationExpiry(OffsetDateTime.now().plus(Duration.ofDays(1)));
        userVerification.setVerificationStatus(UserVerificationStatusEnum.PENDING.name());
        userVerification.setVerificationType(UserVerificationTypeEnum.EMAIL_VERIFICATION.name());
        userVerification.setUserAccount(userAccount);
        return userVerificationRepository.save(userVerification).getUserVerificationId();
    }

    public void update(final Integer userVerificationId,
                       final UserVerificationDTO userVerificationDTO) {
        final UserVerification userVerification = userVerificationRepository.findById(userVerificationId)
                .orElseThrow(() -> new NotFoundException("OKK"));
        userVerificationMapper.mapToEntity(userVerificationDTO, userVerification);
        userVerificationRepository.save(userVerification);
    }

    public void delete(final Integer userVerificationId) {
        userVerificationRepository.deleteById(userVerificationId);
    }

    //    public UserVerificationDTO sendEmailVerification(final UserAccount userAccount) {
//        String verificationCode = String.valueOf(UUID.randomUUID());
//
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, true);
//            mailMessage.setFrom("no-reply@yokke.co.id");
//            mailMessage.setFrom(new InternetAddress("No-Reply", "no-reply@yokke.co.id"));
//            mailMessage.setTo(userAccount.getEmail());
//            mailMessage.setSubject("Complete Registration!");
////    mailMessage.setFrom("noreply@yokke.co.id");
//            Context context = new Context();
//            context.setVariable("verificationLink", confirmEmailUrl + "?token="
//                    + verificationCode);
//            context.setVariable("recipientName", userAccount.getUsername());
//            String htmlContent = templateEngine.process("email-confirm", context);
//            mimeMessage.setContent(htmlContent, "text/html");
//            javaMailSender.send(mimeMessage);
//
//        } catch (MessagingException e) {
//            log.error("Error sending email", e);
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//        UserVerification verification = UserVerification.builder()
//                .verificationType(UserVerificationTypeEnum.EMAIL_VERIFICATION.name())
//                .verificationCode(verificationCode)
//                .verificationStatus(UserVerificationStatusEnum.PENDING.name())
//                .verificationExpiry(
//                        OffsetDateTime.now().plus(Duration.ofDays(1))
//                )
//                .isVerified(false)
//                .userAccount(userAccount)
//                .build();
//        userVerificationRepository.save(verification);
//        return userVerificationMapper.mapToDTO(verification, new UserVerificationDTO());
//    }
    public Boolean sendEmailVerification(final String username, final String email, final String password, final String roleName, final String code) {
        try {
            UserAccount sender = (UserAccount) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, true);
            mailMessage.setFrom("no-reply@yokke.co.id");
            mailMessage.setTo(email);
            mailMessage.setSubject("Yokke FDS Invitation");
//    mailMessage.setFrom("noreply@yokke.co.id");
            Context context = new Context();
            context.setVariable("verificationLink", setupPasswordUrl + "?token="
                    + code);
            context.setVariable("recipientName", username);
            context.setVariable("recepientEmail", email);
            context.setVariable("recepientRole", roleName);
            context.setVariable("senderName", sender.getUsername());
            String htmlContent = templateEngine.process("email-verification", context);
            mimeMessage.setContent(htmlContent, "text/html");
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            log.error("Error sending email", e);
        } catch (Exception e) {
            log.error("Error sending email", e);
        }

        return true;
    }

    public UserVerificationDTO verifyEmail(final String verificationCode) {
        final UserVerification userVerification = userVerificationRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new NotFoundException("Verification code not found"));
        if (userVerification.getVerificationExpiry().isBefore(OffsetDateTime.now())) {
            throw new NotFoundException("Verification code expired");
        }
        userVerification.setVerificationStatus(UserVerificationStatusEnum.VERIFIED.name());
        userVerification.setIsVerified(true);
        userVerificationRepository.save(userVerification);
        return userVerificationMapper.mapToDTO(userVerification, new UserVerificationDTO());
    }

    public Boolean sendLockedUser(
            final String username,
            final String email
    ) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, true);
            mailMessage.setFrom("no-reply@yokke.co.id");
//            mailMessage.setFrom(new InternetAddress("No Reply", "no-reply@yokke.co.id"));
            mailMessage.setTo(email);
            mailMessage.setSubject("Your Account Has Been Locked – Action Required");
            Context context = new Context();
            context.setVariable("recipientName", username);
            String htmlContent = templateEngine.process("user-locked", context);
            mimeMessage.setContent(htmlContent, "text/html");
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            log.error("Error sending email", e);
            return false;
        }
        return true;
    }

    public UserVerificationDTO sendForgotPasswordEmail(final UserAccount userAccount) {
        UserVerification verification = UserVerification.builder()
                .verificationType(UserVerificationTypeEnum.FORGOT_PASSWORD.name())
                .verificationCode(String.valueOf(UUID.randomUUID()))
                .verificationStatus(UserVerificationStatusEnum.PENDING.name())
                .verificationExpiry(
                        OffsetDateTime.now().plus(Duration.ofDays(1))
                )
                .isVerified(false)
                .userAccount(userAccount)
                .build();
        userVerificationRepository.save(verification);
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, true);
            mailMessage.setFrom("no-reply@yokke.co.id");
//            mailMessage.setFrom(new InternetAddress("No Reply", "no-reply@yokke.co.id"));
            mailMessage.setTo(userAccount.getEmail());
            mailMessage.setSubject("Reset Password");
            Context context = new Context();
            context.setVariable("resetPasswordUrl", resetPasswordUrl + "?token="
                    + verification.getVerificationCode());
            context.setVariable("recipientName", userAccount.getUsername());
            String htmlContent = templateEngine.process("forgot-password", context);
            mimeMessage.setContent(htmlContent, "text/html");
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            log.error("Error sending email", e);
        }
        return userVerificationMapper.mapToDTO(verification, new UserVerificationDTO());
    }

    public UserVerificationDTO verifyForgotPassword(final String verificationCode) {
        final UserVerification userVerification = userVerificationRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new NotFoundException("Verification code not found"));
        if (userVerification.getVerificationExpiry().isBefore(OffsetDateTime.now())) {
            throw new NotFoundException("Verification code expired");
        }
        userVerification.setVerificationStatus(UserVerificationStatusEnum.VERIFIED.name());
        userVerification.setIsVerified(true);
        userVerificationRepository.save(userVerification);
        return userVerificationMapper.mapToDTO(userVerification, new UserVerificationDTO());
    }


}
