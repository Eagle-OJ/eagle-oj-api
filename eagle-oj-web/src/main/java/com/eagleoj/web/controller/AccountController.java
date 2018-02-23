package com.eagleoj.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.eagleoj.web.cache.CacheController;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.index.ForgetPasswordFormat;
import com.eagleoj.web.controller.format.index.IndexLoginFormat;
import com.eagleoj.web.controller.format.index.IndexRegisterFormat;
import com.eagleoj.web.controller.format.index.ResetPasswordFormat;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.mail.MailService;
import com.eagleoj.web.service.UserService;
import com.eagleoj.web.util.JWTUtil;
import io.swagger.annotations.ApiOperation;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @ApiOperation("用户注册")
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody @Valid IndexRegisterFormat format) {
        // 注册用户
        userService.register(format.getEmail(), format.getNickname(), format.getPassword());
        return new ResponseEntity("注册成功");
    }

    @ApiOperation("用户登入")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid IndexLoginFormat format) {
        UserEntity userEntity = userService.login(format.getEmail(), format.getPassword());
        JSONArray array = userEntity.getPermission();
        Iterator<Object> it = array.iterator();
        Set<String> permission = new HashSet<>();
        while (it.hasNext()) {
            permission.add(it.next().toString());
        }
        String token = JWTUtil.sign(userEntity.getUid(), userEntity.getRole(), permission, userEntity.getPassword());
        Cache<String, String> authCache = CacheController.getAuthCache();
        authCache.put(token, userEntity.getPassword());

        return new ResponseEntity("登入成功", token);
    }

    @ApiOperation("忘记密码")
    @PostMapping("/forget_password")
    public ResponseEntity forgetPassword(@RequestBody @Valid ForgetPasswordFormat format) {
        UserEntity userEntity = userService.getUserByEmail(format.getEmail());
        if (! mailService.sendForgetPasswordMessage(format.getUrl(), userEntity)) {
            throw new WebErrorException("邮件发送失败");
        }
        return new ResponseEntity("邮件发送成功");
    }

    @ApiOperation("重设密码")
    @PostMapping("/reset_password")
    public ResponseEntity resetPassword(@RequestBody @Valid ResetPasswordFormat format) {
        userService.resetUserPassword(format.getEmail(), format.getPassword(), format.getCode());
        return new ResponseEntity("密码重置成功");
    }
}
