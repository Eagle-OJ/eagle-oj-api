package com.eagleoj.web.controller;

import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/announcements", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AnnouncementsController {
    @Autowired
    private AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity get() {
        return new ResponseEntity(announcementService.listAllAnnouncements());
    }
}
