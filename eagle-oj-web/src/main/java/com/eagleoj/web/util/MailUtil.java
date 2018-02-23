package com.eagleoj.web.util;

import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.mail.MailEntity;
import com.eagleoj.web.setting.SettingEnum;
import com.eagleoj.web.setting.SettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Smith
 **/
@Component
public class MailUtil {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private SettingService settingService;

    public MailUtil(SettingService settingService) {
        this.settingService = settingService;
    }

    public boolean send(MailEntity entity) {
        try {
            JavaMailSenderImpl sender = getSender();
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(sender.getUsername());
            helper.setTo(entity.getReceiver());
            helper.setSubject(entity.getTitle());
            helper.setText(entity.getText(), true);
            sender.send(message);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    public boolean test(String host, int port, String username, String password, String receiver) {
        try {
            JavaMailSenderImpl sender = new JavaMailSenderImpl();
            sender.setHost(host);
            sender.setPort(port);
            sender.setUsername(username);
            sender.setPassword(password);
            sender.testConnection();
            MimeMessage message = sender.createMimeMessage();
            MimeMessageHelper helper =  new MimeMessageHelper(message);
            helper.setFrom(username);
            helper.setSubject("Eagle-oj-mail-test");
            helper.setText("This is a test mail for Eagle-oj system!");
            helper.setTo(receiver);
            sender.send(message);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    private JavaMailSenderImpl getSender() throws MessagingException {
        List<SettingEnum> keys = new ArrayList<>(4);
        keys.add(SettingEnum.MAIL_HOST);
        keys.add(SettingEnum.MAIL_PORT);
        keys.add(SettingEnum.MAIL_USERNAME);
        keys.add(SettingEnum.MAIL_PASSWORD);
        List<String> values = settingService.listSettings(keys);
        String host = values.get(0);
        int port = Integer.valueOf(values.get(1));
        String username = values.get(2);
        String password = values.get(3);
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        sender.setPassword(password);
        return sender;
    }
}
