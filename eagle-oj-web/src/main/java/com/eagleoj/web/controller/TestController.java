package com.eagleoj.web.controller;

import com.eagleoj.web.controller.exception.ForbiddenException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.eagleoj.web.entity.ResponseEntity;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
public class TestController {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @GetMapping("/test")
    public ResponseEntity test1() {
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        map.put("username", username);
        map.put("password", password);
        return new ResponseEntity(map);
    }



}
