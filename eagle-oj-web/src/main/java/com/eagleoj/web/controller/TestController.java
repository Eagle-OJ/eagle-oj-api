package com.eagleoj.web.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import com.eagleoj.web.entity.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Smith
 **/
@RestController
public class TestController {

    /*@Autowired
    private Test testService;

    @GetMapping("/test")
    public ResponseEntity test1() {
        Page page = PageHelper.startPage(1, 10);
        Object data = testService.getUser(1);
        return new ResponseEntity(page.getTotal());
    }*/

}
