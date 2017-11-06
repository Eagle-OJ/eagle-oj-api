package org.inlighting.oj.web.controller;

import org.inlighting.oj.web.entity.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Smith
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/add_problem")
    public ResponseEntity addProblem() {
        return null;
    }

    @GetMapping("/index")
    public String hello() {
        return "hello";
    }
}
