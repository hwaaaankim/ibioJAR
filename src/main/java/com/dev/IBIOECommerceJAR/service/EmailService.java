package com.dev.IBIOECommerceJAR.service;

import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final JavaMailSender mailSender;
    private final EmailSendService  emailSendService ;

    public EmailService(JavaMailSender mailSender, EmailSendService emailSendService) {
        this.mailSender = mailSender;
        this.emailSendService = emailSendService;
    }

    public void sendEmails(String[] to, String subject, String message) throws MailSendException, InterruptedException {
    	emailSendService.send(to, subject, message);
    }
    
}
