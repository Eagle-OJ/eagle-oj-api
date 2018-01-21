package org.inlighting.oj.web.controller;

import org.inlighting.oj.web.config.SystemConfig;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/setting", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SettingController {

    @Autowired
    private SettingService settingService;

    @Autowired
    private SystemConfig systemConfig;

    @GetMapping
    public ResponseEntity getSetting() {
        return new ResponseEntity(systemConfig);
    }

    @PostMapping
    public ResponseEntity addSetting() {
        return null;
    }
}
