package com.eagleoj.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Smith
 **/
@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate template;

    @GetMapping("/redis")
    public String test() {
        //template.set
        return "hello";
    }
}
