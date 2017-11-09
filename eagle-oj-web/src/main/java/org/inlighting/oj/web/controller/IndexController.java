package org.inlighting.oj.web.controller;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.ehcache.Cache;
import org.inlighting.oj.web.cache.CacheController;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.entity.UserEntity;
import org.inlighting.oj.web.controller.format.index.IndexLoginFormat;
import org.inlighting.oj.web.controller.format.index.IndexRegisterFormat;
import org.inlighting.oj.web.service.UserService;
import org.inlighting.oj.web.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class IndexController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("用户注册")
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody @Valid IndexRegisterFormat format) {
        if (userService.addUser(format.getEmail(),
                format.getNickname(),
                new Md5Hash(format.getPassword()).toString(),
                System.currentTimeMillis())) {
            return new ResponseEntity("注册成功");
        } else {
            throw new RuntimeException("注册失败");
        }
    }

    @ApiOperation("用户登入")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid IndexLoginFormat format) {
        UserEntity userEntity = userService.getUserByLogin(format.getEmail(),
                new Md5Hash(format.getPassword()).toString());
        if (userEntity == null)
            throw new RuntimeException("用户名密码错误");

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
