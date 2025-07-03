//package mz.org.clubemaritimo.sisclube.utils.email;//package org.cmd.sisclube.service.email;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ResourceUtils;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
//import jakarta.activation.DataHandler;
//import jakarta.activation.DataSource;
//import jakarta.mail.Message;
//import jakarta.mail.MessagingException;
//import jakarta.mail.Multipart;
//import jakarta.mail.internet.*;
//import jakarta.mail.util.ByteArrayDataSource;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//
//import static jakarta.mail.Part.ATTACHMENT;
//import static jakarta.mail.Part.INLINE;
//import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class EmailSenderService {
//    private final String          username;
//    private final JavaMailSender  mailSender;
//    private final ExecutorService executorService;
//    private final TemplateEngine  templateEngine;
//
//
//    /**
//     * Sends the sign-up email.
//     *
//     * @param to    {@link String} the email address to send the email to.
//     * @param token {@link String} the activation account token.
//     */
//    public void sendSignUpEmail(String to, String token, String redirectTo)
//    {
//        String subject              = "[AAA-Data®] Activation de compte";
//        String webContextUrl        = webContextService.getUrl();
//        String activationAccountUrl = webContextUrl + "/activate-account?token=" + token;
//
//        if (redirectTo != null && !redirectTo.trim().isEmpty())
//        {
//            activationAccountUrl += "&redirectTo=" + redirectTo;
//        }
//
//        Context context = new Context();
//        context.setVariable(TITLE, subject);
//        context.setVariable("activationAccountUrl", activationAccountUrl);
//
//        String content = this.processEmailTemplate(EmailTemplatePathEnum.SIGN_UP, context);
//
//        Email email = Email.builder()
//                .to(to)
//                .content(content)
//                .subject(subject)
//                .build();
//
//        String resourceLocation = CLASSPATH_URL_PREFIX + "images/auto-immat-logo-header-email.png";
//        try
//        {
//            File       file       = ResourceUtils.getFile(resourceLocation);
//            Attachment attachment = Attachment.builder().disposition(Attachment.Disposition.INLINE).file(file).fileName(file.getName()).build();
//
//            email.setAttachments(Collections.singletonList(attachment));
//
//            sendEmailAsynchronously(email);
//
//        } catch (Exception exception)
//        {
//            throw new RuntimeException("Failed to get file " + resourceLocation + ", email " + email + " send failed.");
//        }
//    }
//
//    public void sendAccountActiveEmail(String to, String redirectTo)
//    {
//        String subject       = "[AAA-Data®] Votre compte est actif";
//        String webContextUrl = webContextService.getUrl();
//        String loginUrl      = webContextUrl + "/login";
//
//        if (redirectTo != null && !redirectTo.trim().isEmpty())
//        {
//            loginUrl += "?redirectTo=" + redirectTo;
//        }
//
//        Context context = new Context();
//        context.setVariable(TITLE, subject);
//        context.setVariable("activationAccountUrl", loginUrl);
//
//        String content = this.processEmailTemplate(EmailTemplatePathEnum.ACCOUNT_ACTIVATION, context);
//
//        Email email = Email.builder()
//                .to(to)
//                .subject(subject)
//                .content(content)
//                .build();
//
//        String resourceLocation = CLASSPATH_URL_PREFIX + "images/auto-immat-logo-header-email.png";
//        try
//        {
//            File       file       = ResourceUtils.getFile(resourceLocation);
//            Attachment attachment = Attachment.builder().disposition(Attachment.Disposition.INLINE).file(file).fileName(file.getName()).build();
//
//            email.setAttachments(Collections.singletonList(attachment));
//
//            sendEmailAsynchronously(email);
//
//        } catch (Exception exception)
//        {
//            throw new RuntimeException("Failed to get file " + resourceLocation + ", email " + email + " send failed.");
//        }
//    }
//
//    /**
//     * Sends the reset password token email.
//     *
//     * @param to    {@link String} the email address to send the email to.
//     * @param token {@link String} the reset password token.
//     */
//    public void sendResetPasswordTokenEmail(String to, String token)
//    {
//        String subject          = "[AAA-Data®] Réinitialisation de votre mot de passe";
//        String webContextUrl    = webContextService.getUrl();
//        String resetPasswordUrl = webContextUrl + "/reset-password?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
//
//        Context context = new Context();
//        context.setVariable(TITLE, subject);
//        context.setVariable("resetPasswordUrl", resetPasswordUrl);
//
//        String content = this.processEmailTemplate(EmailTemplatePathEnum.RESET_PASSWORD_TOKEN, context);
//
//        Email email = Email.builder()
//                .to(to)
//                .subject(subject)
//                .content(content)
//                .build();
//
//        String resourceLocation = CLASSPATH_URL_PREFIX + "images/auto-immat-logo-header-email.png";
//        try
//        {
//            File       file       = ResourceUtils.getFile(resourceLocation);
//            Attachment attachment = Attachment.builder().disposition(Attachment.Disposition.INLINE).file(file).fileName(file.getName()).build();
//
//            email.setAttachments(Collections.singletonList(attachment));
//
//            sendEmailAsynchronously(email);
//
//        } catch (Exception exception)
//        {
//            throw new RuntimeException("Failed to get file " + resourceLocation + ", email " + email + " send failed.");
//        }
//    }
//
//    /**
//     * Sends initial password token email.
//     *
//     * @param to    {@link String} the email address to send the email to.
//     * @param token {@link String} the initial define password token.
//     */
//    public void sendInitialPasswordTokenEmail(String to, String token)
//    {
//        String subject          = "[AAA-Data®] Définir un mot de passe";
//        String webContextUrl    = webContextService.getUrl();
//        String resetPasswordUrl = webContextUrl + "/define-password?token=" + URLEncoder.encode(token, StandardCharsets.UTF_8);
//
//        Context context = new Context();
//        context.setVariable(TITLE, subject);
//        context.setVariable("resetPasswordUrl", resetPasswordUrl);
//
//        String content = this.processEmailTemplate(EmailTemplatePathEnum.INITIAL_PASSWORD_TOKEN, context);
//
//        Email email = Email.builder()
//                .to(to)
//                .subject(subject)
//                .content(content)
//                .build();
//
//        String resourceLocation = CLASSPATH_URL_PREFIX + "images/auto-immat-logo-header-email.png";
//        try
//        {
//            File       file       = ResourceUtils.getFile(resourceLocation);
//            Attachment attachment = Attachment.builder().disposition(Attachment.Disposition.INLINE).file(file).fileName(file.getName()).build();
//
//            email.setAttachments(Collections.singletonList(attachment));
//
//            sendEmailAsynchronously(email);
//
//        } catch (Exception exception)
//        {
//            throw new RuntimeException("Failed to get file " + resourceLocation + ", email " + email + " send failed.");
//        }
//    }
//
//    /**
//     * Sends the reset password confirmation email.
//     *
//     * @param to {@link String} the email address to send the email to.
//     */
//    public void sendResetPasswordConfirmationEmail(String to)
//    {
//        String subject       = "[AAA-Data®] Modification de votre mot de passe";
//        String webContextUrl = webContextService.getUrl();
//        String loginUrl      = webContextUrl + "/login";
//
//        Context context = new Context();
//        context.setVariable(TITLE, subject);
//        context.setVariable("loginUrl", loginUrl);
//
//        String content = this.processEmailTemplate(EmailTemplatePathEnum.RESET_PASSWORD_CONFIRMATION, context);
//
//        Email email = Email.builder()
//                .to(to)
//                .subject(subject)
//                .content(content)
//                .build();
//
//        String resourceLocation = CLASSPATH_URL_PREFIX + "images/auto-immat-logo-header-email.png";
//        try
//        {
//            File       file       = ResourceUtils.getFile(resourceLocation);
//            Attachment attachment = Attachment.builder().disposition(Attachment.Disposition.INLINE).file(file).fileName(file.getName()).build();
//
//            email.setAttachments(Collections.singletonList(attachment));
//
//            sendEmailAsynchronously(email);
//
//        } catch (Exception exception)
//        {
//            throw new RuntimeException("Failed to get file " + resourceLocation + ", email " + email + " send failed.");
//        }
//    }
//
//
//    public void sendNewClientPasswordRegeneratedEmail(
//            String userEmail,
//            List<String> allCdsUsersEmail,
//            String userName,
//            String clientUsername,
//            String clientDecodedPassword,
//            String clientDecodedOldPassword
//    )
//    {
//        String  subject = String.format("[AAA-Data®] L'utilisateur %s a régénéré le mot de passe du client %s", userName, clientUsername);
//        Context context = new Context();
//
//        context.setVariable(TITLE, subject);
//        context.setVariable("userName", userName);
//        context.setVariable("clientUsername", clientUsername);
//        context.setVariable("clientDecodedPassword", clientDecodedPassword);
//        context.setVariable("clientDecodedOldPassword", clientDecodedOldPassword);
//
//        String content = this.processEmailTemplate(EmailTemplatePathEnum.CLIENT_PASSWORD_REGENERATED, context);
//
//        Email email = Email
//                .builder()
//                .to(userEmail)
//                .cc(allCdsUsersEmail.toArray(String[]::new))
//                .content(content)
//                .subject(subject)
//                .build();
//
//        String resourceLocation = CLASSPATH_URL_PREFIX + "images/auto-immat-logo-header-email.png";
//        try
//        {
//            File       file       = ResourceUtils.getFile(resourceLocation);
//            Attachment attachment = Attachment.builder().disposition(Attachment.Disposition.INLINE).file(file).fileName(file.getName()).build();
//
//            email.setAttachments(Collections.singletonList(attachment));
//
//            sendEmailAsynchronously(email);
//
//        } catch (Exception exception)
//        {
//            throw new RuntimeException("Failed to get file " + resourceLocation + ", email " + email + " send failed.");
//        }
//    }
//
//    private String processEmailTemplate(EmailTemplatePathEnum emailTemplatePathEnum, Context context)
//    {
//        return thymeleafService.process(emailTemplatePathEnum.templateRelativePath, context);
//    }
//
//    /**
//     * Generic send email method.
//     *
//     * @param email {@link Email} the email representation
//     */
//    public void sendEmailAsynchronously(Email email)
//    {
//        this.sendEmail(email, true);
//    }
//
//    public void sendEmailSynchronously(Email email)
//    {
//        this.sendEmail(email, false);
//    }
//
//    private void sendEmail(Email email, boolean asynchronously)
//    {
//        try
//        {
//            MimeMessage message = mailSender.createMimeMessage();
//
//            message.setFrom(this.getFrom(email.getFrom()));
//            message.setRecipients(Message.RecipientType.TO, email.getTo());
//            message.setRecipients(Message.RecipientType.CC, email.getCc());
//            message.setRecipients(Message.RecipientType.BCC, email.getBcc());
//            message.setSubject(email.getSubject());
//
//            Multipart mimeMultipart = new MimeMultipart();
//
//            MimeBodyPart contentMimeBodyPart = new MimeBodyPart();
//            contentMimeBodyPart.setContent(email.getContent(), "text/html; charset=utf-8");
//            mimeMultipart.addBodyPart(contentMimeBodyPart);
//
//            for (Attachment attachment : email.getAttachments())
//            {
//                MimeBodyPart attachmentMimeBodyPart = this.convertToMimeBodyPart(attachment);
//                mimeMultipart.addBodyPart(attachmentMimeBodyPart);
//            }
//
//            message.setContent(mimeMultipart);
//
//            if (asynchronously)
//            {
//                log.info("Sending email asynchronously {}!", email);
//                executorService.submit(() ->
//                                       {
//                                           try
//                                           {
//                                               mailSender.send(message);
//                                           } catch (Exception e)
//                                           {
//                                               log.error("Failed to send email asynchronously {}", email, e);
//                                           }
//                                       });
//            }
//            else
//            {
//                log.info("Sending email synchronously {}!", email);
//                mailSender.send(message);
//            }
//
//        } catch (Exception e)
//        {
//            log.error("Failed to send email {}", email, e);
//            throw new RuntimeException("Failed to send email");
//        }
//    }
//
//    private InternetAddress getFrom(String from) throws AddressException
//    {
//        if (StringUtils.isBlank(from))
//        {
//            return new InternetAddress(username, false);
//        }
//        return new InternetAddress(from, false);
//    }
//
//    private MimeBodyPart convertToMimeBodyPart(Attachment attachment) throws MessagingException, IOException
//    {
//        File file = attachment.getFile();
//
//        MimeBodyPart mimeBodyPart = new MimeBodyPart();
//
//        FileInputStream fileInputStream = new FileInputStream(file);
//        DataSource      dataSource      = new ByteArrayDataSource(fileInputStream, "image/*");
//        DataHandler     dataHandler     = new DataHandler(dataSource);
//        mimeBodyPart.setDataHandler(dataHandler);
//
//        if (Attachment.Disposition.INLINE.equals(attachment.getDisposition()))
//        {
//            mimeBodyPart.addHeader("Content-ID", attachment.ContentId);
//            mimeBodyPart.setDisposition(INLINE);
//        }
//        else
//        {
//            mimeBodyPart.setFileName(attachment.getFileName());
//            mimeBodyPart.setDisposition(ATTACHMENT);
//        }
//
//        return mimeBodyPart;
//    }
//}
