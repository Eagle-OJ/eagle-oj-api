package org.inlighting.oj.web.controller;

import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.entity.UserEntity;
import org.inlighting.oj.web.service.SubmissionService;
import org.inlighting.oj.web.service.UserLogService;
import org.inlighting.oj.web.service.UserService;
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

    private UserService userService;

    private SubmissionService submissionService;

    @Autowired
    public void setSubmissionService(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{uid}")
    public ResponseEntity getProfile(@PathVariable int uid) {
        UserEntity userEntity = userService.getUserByUid(uid);
        userEntity.setPassword(null);
        userEntity.setEmail(null);
        return new ResponseEntity(userEntity);
    }
}
