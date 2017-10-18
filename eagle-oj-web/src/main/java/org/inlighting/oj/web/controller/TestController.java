package org.inlighting.oj.web.controller;

import org.inlighting.oj.judge.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    //private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/hello")
    public String hello() {
        //LOGGER.error("error");
        HttpUtil.get("ef", "fe");
        return "hello";
    }
}
