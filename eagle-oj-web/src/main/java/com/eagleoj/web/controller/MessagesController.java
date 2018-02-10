package com.eagleoj.web.controller;

import com.eagleoj.web.util.WebUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageRowBounds;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MessagesController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/user")
    @RequiresAuthentication
    public ResponseEntity getMessages(@RequestParam("page") int page,
                                     @RequestParam("page_size") int pageSize) {
        int owner = SessionHelper.get().getUid();
        Page pager = PageHelper.startPage(page, pageSize);
        return new ResponseEntity(WebUtil.generatePageData(pager, messageService.listUserMessages(owner)));
    }
}
