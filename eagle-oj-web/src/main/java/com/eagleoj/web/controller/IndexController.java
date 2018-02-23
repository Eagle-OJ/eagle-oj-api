package com.eagleoj.web.controller;

import com.eagleoj.web.setting.SettingEnum;
import com.eagleoj.web.setting.SettingService;
import com.eagleoj.web.entity.AttachmentEntity;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class IndexController {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private SettingService settingService;

    @GetMapping("/avatar")
    public void getAvatar(@RequestParam("aid") int aid,
                          HttpServletResponse response) throws IOException {
        AttachmentEntity entity = attachmentService.getAvatar(aid);
        if (! settingService.isOpenStorage()) {
            response.getWriter().append("未开启存储功能");
            return;
        }
        String OSS_URL = settingService.getSetting(SettingEnum.OSS_URL);
        response.sendRedirect(OSS_URL+entity.getUrl());
    }

    @RequestMapping("/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity unauthorized() {
        return new ResponseEntity(401, "请求未授权", null);
    }

    @RequestMapping("/404")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity notFound() {
        return new ResponseEntity(404, "此页面不存在", null);
    }
}
