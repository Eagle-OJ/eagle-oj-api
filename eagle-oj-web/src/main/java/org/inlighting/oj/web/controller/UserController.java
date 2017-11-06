package org.inlighting.oj.web.controller;

import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Smith
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    private ProblemService problemService;

    @Autowired
    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping("/problem")
    public ResponseEntity addProblem() {
        // todo
        return null;
    }

    @PutMapping("/problem")
    public ResponseEntity updateProblem() {
        // todo
        return null;
    }

    /**
     * 附带test_case个数
     */
    @GetMapping("/problem")
    public ResponseEntity getProblem() {
        // todo
        return null;
    }

    @PostMapping("/problem/test_case")
    public ResponseEntity addProblemTestCase() {
        // todo
        return null;
    }

    @GetMapping("/problem/test_case")
    public ResponseEntity getProblemTestCase() {
        // todo
        return null;
    }

    @PutMapping("/problem/test")
    public ResponseEntity updateProblemTestCase() {
        // todo
        return null;
    }
}
