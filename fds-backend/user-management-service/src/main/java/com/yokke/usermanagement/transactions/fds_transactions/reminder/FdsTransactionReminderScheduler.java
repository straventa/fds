package com.yokke.usermanagement.transactions.fds_transactions.reminder;

import com.yokke.usermanagement.transactions.fds_transactions.FdsTransactions;
import com.yokke.usermanagement.transactions.fds_transactions.FdsTransactionsRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FdsTransactionReminderScheduler {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final FdsTransactionsRepository fdsTransactionsRepository;
    @Value("${app.base-url}")
    private String baseUrl;

    @Scheduled(fixedRate = 60000) // Runs every 60,000 milliseconds (1 minute)
    public void sendReminder() {
        log.info("Triggered send reminder");
        LocalDateTime now = LocalDateTime.now();
        List<FdsTransactions> getReminder =
                fdsTransactionsRepository.findByDateTimeIgnoringSeconds(
                        now
                );
        for (FdsTransactions fdsTransactionsDto : getReminder) {
            try {
                log.info("Sending email to {}", fdsTransactionsDto.getConfirmedUserAccount().getEmail());
                String ruleName = fdsTransactionsDto.getRule().stream()
                        .map((e) -> e.getRuleName()) // Extract the name field
                        .collect(Collectors.joining(", ")); //             try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, true);
                mailMessage.setFrom("no-reply@yokke.co.id");
                mailMessage.setTo(fdsTransactionsDto.getConfirmedUserAccount().getEmail());
                mailMessage.setSubject("Reminder Alert");
                Context context = getContext(fdsTransactionsDto, ruleName);
                String htmlContent = templateEngine.process("remind", context);
                mimeMessage.setContent(htmlContent, "text/html");
                javaMailSender.send(mimeMessage);

            } catch (MessagingException e) {
                log.error("Error sending email", e);
            }
        }


    }

    private Context getContext(FdsTransactions fdsTransactionsDto, String ruleName) {
        Context context = new Context();
        context.setVariable("alertUrl", baseUrl + "/dashboard/alert/" + fdsTransactionsDto.getFdsTransactionId());
        context.setVariable("recipientName", fdsTransactionsDto.getConfirmedUserAccount().getEmail());
        context.setVariable("ruleName", ruleName);
        context.setVariable("mid", fdsTransactionsDto.getMid());
        context.setVariable("tid", fdsTransactionsDto.getTid());
        context.setVariable("merchantName", fdsTransactionsDto.getMerchantName());
        context.setVariable("authDate", fdsTransactionsDto.getAuthDate());
        context.setVariable("authTime", fdsTransactionsDto.getAuthTime());
        context.setVariable("authAmount", fdsTransactionsDto.getAuthAmount());
        context.setVariable("approvalCode", fdsTransactionsDto.getApprovalCode());
        context.setVariable("posEntryModeDetail", fdsTransactionsDto.getPosEntryModeDetail());
        context.setVariable("eciValue", fdsTransactionsDto.getEciValue());
        context.setVariable("responseCode", fdsTransactionsDto.getReasonContents());
        return context;
    }
}