package com.dmdd.revenuemanagement.sendemail;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderService {
    private JavaMailSender javaMailSender;

    public void sendSimpleMessage(String to,String subject,String text) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("duyminh95@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}