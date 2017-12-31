package org.inlighting.oj.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Smith
 **/
@RestController
public class TestController {

    @GetMapping("/test")
    public String test1() {
        Subject subject = SecurityUtils.getSubject();
        System.out.println("subject="+subject.isAuthenticated());
        return "hello";
    }

    @GetMapping("/test1")
    @RequiresAuthentication
    public String test2() {
        return "authorized";
    }
}
