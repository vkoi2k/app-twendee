package com.twendee.app.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class MailSender {
//    final
//    JavaMailSender javaMailSender;
//
//    public MailSender(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
//
//    public void send(String from, String to, String cc, String bcc,
//                     String subject, String body, String attachment){
//    try {
//        //create mail
//        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
//        //user class helper
//        MimeMessageHelper mimeMessageHelper=
//                new MimeMessageHelper(mimeMailMessage, true);
//    }catch (Exception e){
//        e.printStackTrace();
//    }
//
//
//    }
}
