package com.digitaldesign.murashkina.services.email;

import jakarta.mail.MessagingException;


public interface EmailService {
    void sendMail(EmailDetails details) throws MessagingException;
}
