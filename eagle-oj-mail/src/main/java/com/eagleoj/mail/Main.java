package com.eagleoj.mail;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * @author Smith
 **/
public class Main {
    public static void main(String[] args) throws Exception {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtpdm.aliyun.com");
        sender.setPort(80);
        sender.setUsername("mail@eagleoj.com");
        sender.setPassword("v2l8CRlTPtLvz6ocp2Bv");
        sender.testConnection();
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo("chendingchao1@126.com");
        helper.setText("Thank you for ordering!");
        helper.setSubject("xx");
        helper.setFrom("mail@eagleoj.com");
        sender.send(message);
    }
}
