package com.eagleoj.web.controller;

import com.eagleoj.web.controller.exception.ForbiddenException;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.eagleoj.web.entity.ResponseEntity;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Smith
 **/
@RestController
public class TestController {



    @GetMapping("/test1")
    public ResponseEntity test1() {
        return new ResponseEntity("authorized");
    }

    @GetMapping("/test2")
    @RequiresAuthentication
    public ResponseEntity test2() {
        return new ResponseEntity("role");
    }

    @GetMapping("/test3")
    public ResponseEntity test3() {
        int a = 4;
        if (a == 4)
            throw new ForbiddenException();
        return new ResponseEntity("role");
    }

}
