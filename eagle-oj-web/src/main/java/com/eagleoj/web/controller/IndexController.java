package com.eagleoj.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.index.IndexLoginFormat;
import com.eagleoj.web.controller.format.index.IndexRegisterFormat;
import com.eagleoj.web.service.SettingService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.ehcache.Cache;
import com.eagleoj.web.cache.CacheController;
import com.eagleoj.web.entity.AttachmentEntity;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.service.AttachmentService;
import com.eagleoj.web.service.UserService;
import com.eagleoj.web.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private SettingService settingService;

    @ApiOperation("用户注册")
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody @Valid IndexRegisterFormat format) {
        // 检查用户是否存在
        UserEntity userEntity = userService.getUserByEmail(format.getEmail());
        if (userEntity != null) {
            throw new WebErrorException("邮箱已经注册");
        }

        // 注册用户
        int uid = userService.save(format.getEmail(),
                format.getNickname(),
                new Md5Hash(format.getPassword()).toString());
        if (uid == 0) {
            throw new WebErrorException("注册失败");
        }
        return new ResponseEntity("注册成功", uid);
    }

    @ApiOperation("用户登入")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid IndexLoginFormat format) {
        UserEntity userEntity = userService.getUserByEmailPassword(format.getEmail(), format.getPassword());
        if (userEntity == null)
            throw new WebErrorException("用户名密码错误");

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

    @GetMapping("/avatar")
    public void getAvatar(@RequestParam("aid") int aid,
                          HttpServletResponse response) throws IOException {
        AttachmentEntity entity = attachmentService.getByAid(aid);
        String url;
        if (entity == null) {
            throw new WebErrorException("不存在此头像");
        }
        String OSS_URL = settingService.getSystemConfig().getOssConfig().getURL();
        url = OSS_URL+entity.getUrl();
        response.sendRedirect(url);
    }

    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity unauthorized() {
        return new ResponseEntity(401, "请求未授权", null);
    }

    @RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity notFound() {
        return new ResponseEntity(404, "此页面不存在", null);
    }
}
