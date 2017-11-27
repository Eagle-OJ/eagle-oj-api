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
import org.inlighting.oj.web.judger.Judger;
import org.inlighting.oj.web.service.UserService;
import org.inlighting.oj.web.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class IndexController {

    private UserService userService;

    private Judger judger;

    @Value("${eagle-oj.oss.url}")
    private String OSS_URL;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setJudger(Judger judger) {
        this.judger = judger;
    }

    @ApiOperation("用户注册")
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody @Valid IndexRegisterFormat format) {
        // 检查用户是否存在
        UserEntity userEntity = userService.getUserByEmail(format.getEmail());
        if (userEntity != null) {
            throw new RuntimeException("邮箱已经注册");
        }

        // 注册用户
        int uid = userService.addUser(format.getEmail(),
                format.getNickname(),
                new Md5Hash(format.getPassword()).toString(),
                System.currentTimeMillis());
        if (uid == 0) {
            throw new RuntimeException("注册失败");
        }
        return new ResponseEntity("注册成功", uid);
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

    @ApiOperation("编程语言配置默认文件")
    @GetMapping("/language")
    public ResponseEntity getLanguageConfig() {
        return new ResponseEntity(judger.getConfiguration());
    }

    @GetMapping("/setting")
    public ResponseEntity getWebConfig() {
        Map<String, Object> data = new HashMap<>();
        data.put("oss_url", OSS_URL);
        return new ResponseEntity(data);
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
