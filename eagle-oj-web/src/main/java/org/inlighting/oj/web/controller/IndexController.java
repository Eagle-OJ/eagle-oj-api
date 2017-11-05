package org.inlighting.oj.web.controller;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.ehcache.Cache;
import org.ehcache.spi.loaderwriter.CacheWritingException;
import org.inlighting.oj.web.cache.CacheController;
import org.inlighting.oj.web.dao.UserDao;
import org.inlighting.oj.web.data.DataHelper;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.entity.UserEntity;
import org.inlighting.oj.web.service.UserService;
import org.inlighting.oj.web.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Smith
 **/
@RestController
@RequestMapping("/")
public class IndexController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestParam("email") String email,
                                   @RequestParam("nickname") String nickname,
                                   @RequestParam("password") String password) {
        if (userService.addUser(email, nickname, password)) {
            return new ResponseEntity("注册成功");
        } else {
            throw new RuntimeException("注册失败");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam("email") String email,
                                @RequestParam("password") String password) {
        UserEntity userEntity = userService.getUserByLogin(email, password);
        if (userEntity == null)
            throw new RuntimeException("用户名密码错误");

        String token = null;
        try {
            JSONArray array = userEntity.getPermission();
            Iterator<Object> it = array.iterator();
            Set<String> permission = new HashSet<>();
            while (it.hasNext()) {
                permission.add(it.next().toString());
            }
            token = JWTUtil.sign(userEntity.getUid(), userEntity.getRole(), permission, userEntity.getPassword());
            Cache<String, String> authCache = CacheController.getAuthCache();
            authCache.put(token, userEntity.getPassword());
        } catch (CacheWritingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity(token);
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
