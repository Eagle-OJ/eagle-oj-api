package com.eagleoj.web.controller;

import com.eagleoj.web.entity.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/judge", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class JudgerController {

    @GetMapping
    public ResponseEntity getJudgerList() {
        return new ResponseEntity("get judger list");
    }

    @PostMapping
    public ResponseEntity addJudger() {
        return new ResponseEntity("add judger");
    }

    @PutMapping
    public ResponseEntity updateJudge() {
        return new ResponseEntity("update judger");
    }

    @DeleteMapping
    public ResponseEntity deleteJudger() {
        return new ResponseEntity("delete judger");
    }
}
