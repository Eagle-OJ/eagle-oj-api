package org.inlighting.oj.web.controller.user;

import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/user/contest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserContestController {

    @ApiOperation("创建比赛")
    @PostMapping
    public ResponseEntity createContest() {

        return null;
    }

    public ResponseEntity enterContest() {
        return null;
    }
}
