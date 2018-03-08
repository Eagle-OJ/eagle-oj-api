package com.eagleoj.web.mail;

import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.setting.SettingService;
import com.eagleoj.web.util.MailUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

/**
 * @author Smith
 **/
@Service
public class MailService {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private SettingService settingService;

    @Autowired
    private TemplateEngine templateEngine;

    public boolean testMail(String host, int port, String username, String password, String receiver) {
        return mailUtil.test(host, port, username, password, receiver);
    }

    public boolean sendConfirmMessage(String url, UserEntity userEntity) {
        checkIsOpen();
        String sb = userEntity.getUid()+userEntity.getEmail()+userEntity.getPassword();
        String secret = new Md5Hash(sb).toString();
        final Context ctx = new Context(Locale.getDefault());
        ctx.setVariable("nickname", userEntity.getNickname());
        ctx.setVariable("url", url+secret);
        String html = templateEngine.process("mail/confirm", ctx);
        MailEntity mailEntity = new MailEntity();
        mailEntity.setReceiver(userEntity.getEmail());
        mailEntity.setText(html);
        mailEntity.setTitle("邮箱验证");
        return mailUtil.send(mailEntity);
    }

    public boolean sendForgetPasswordMessage(String url, UserEntity userEntity) {
        checkIsOpen();
        String sb = userEntity.getUid()+userEntity.getEmail()+userEntity.getPassword();
        String secret = new Md5Hash(sb).toString();
        final Context ctx = new Context(Locale.getDefault());
        ctx.setVariable("email", userEntity.getEmail());
        ctx.setVariable("url", url+secret);
        String html = templateEngine.process("mail/forget_password", ctx);
        MailEntity mailEntity = new MailEntity();
        mailEntity.setReceiver(userEntity.getEmail());
        mailEntity.setText(html);
        mailEntity.setTitle("密码找回");
        return mailUtil.send(mailEntity);
    }

    private void checkIsOpen() {
        if (! settingService.isOpenMail()) {
            throw new WebErrorException("未开启邮件功能");
        }
    }
}
