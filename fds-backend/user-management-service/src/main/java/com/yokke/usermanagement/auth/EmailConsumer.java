package com.yokke.usermanagement.auth;


//@Component
//@QueueDefinition(
//        queue = "email_queue",
//        exchange = RabbitConfig.TOPIC_EXCHANGE,
//        routingKey = "notification.email.#"
//)
//@Slf4j
//@RequiredArgsConstructor
//public class EmailConsumer {
//    private final JavaMailSender javaMailSender;
//    private final TemplateEngine templateEngine;
//    private Integer retryAttempt = 0;
//
//    @RabbitListener(queues = "email_queue")
//    public void consumeEmailMessage(EmailRequest emailRequest) throws MessagingException {
//        if (retryAttempt == 0) {
//            log.info("failed one");
//            retryAttempt += 1;
//            throw new MessagingException("failed");
//        }
//        log.info("processing the second");
//        // Email processing logic
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        MimeMessageHelper mailMessage = new MimeMessageHelper(mimeMessage, true);
//        mailMessage.setFrom("no-reply@yokke.co.id");
//        mailMessage.setTo(emailRequest.getEmail());
//        mailMessage.setSubject("Complete Registration!");
////    mailMessage.setFrom("noreply@yokke.co.id");
//        Context context = new Context();
////        context.setVariable("verificationLink", confirmEmailUrl + "?token="
////                + code);
////        context.setVariable("recipientName", password);
//        String htmlContent = templateEngine.process("email-confirm", context);
//        mimeMessage.setContent(htmlContent, "text/html");
//        javaMailSender.send(mimeMessage);
//
//
//    }
//}