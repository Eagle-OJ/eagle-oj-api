package com.eagleoj.web.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.service.Test;
import com.eagleoj.web.service.TestService;
import com.eagleoj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Smith
 **/
@RestController
public class TestController {

    @Autowired
    private Test testService;

    @GetMapping("/test")
    public ResponseEntity test1() {
        Page page = PageHelper.startPage(1, 10);
        Object data = testService.getUser(1);
        return new ResponseEntity(page.getTotal());
    }

}
