package com.digitaldesign.murashkina.services.email;

import com.digitaldesign.murashkina.services.exceptions.task.EmailNotSentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;
    private final SpringTemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSenderImpl javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendMail(EmailDetails details) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariables(details.getContext());
            String emailContent = templateEngine.process(details.getTemplateLocation(), context);

            mimeMessageHelper.setTo(details.getTo());
            mimeMessageHelper.setSubject(details.getSubject());
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setText(emailContent, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailNotSentException();
        }
    }
}
