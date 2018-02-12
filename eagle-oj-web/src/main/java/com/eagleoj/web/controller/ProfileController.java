package com.eagleoj.web.controller;

import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.service.UserService;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.service.SubmissionService;
import com.eagleoj.web.service.UserLogService;
import com.eagleoj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/{uid}")
    public ResponseEntity getProfile(@PathVariable int uid) {
        UserEntity userEntity = userService.getUserByUid(uid);
        userEntity.setPassword(null);
        userEntity.setEmail(null);
        userEntity.setRole(null);
        userEntity.setPermission(null);
        return new ResponseEntity(userEntity);
    }
}
