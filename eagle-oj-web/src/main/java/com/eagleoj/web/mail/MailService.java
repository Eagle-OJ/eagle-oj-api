package com.eagleoj.web.mail;

import com.eagleoj.web.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class MailService {

    @Autowired
    private MailUtil mailUtil;

    public boolean testMail(String host, int port, String username, String password, String receiver) {
        return mailUtil.test(host, port, username, password, receiver);
    }
}
