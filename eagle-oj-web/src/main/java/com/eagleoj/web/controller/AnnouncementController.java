package com.eagleoj.web.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.admin.AddAnnouncementFormat;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/announcement", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @RequiresAuthentication
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @PostMapping
    public ResponseEntity add(@RequestBody @Valid AddAnnouncementFormat format) {
        announcementService.saveAnnouncement(format.getTitle(), format.getContent());
        return new ResponseEntity("公告添加成功");
    }

    @RequiresAuthentication
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @PutMapping("/{aid}")
    public ResponseEntity update(@PathVariable int aid,
                                 @RequestBody @Valid AddAnnouncementFormat format) {
        announcementService.updateAnnouncement(aid, format.getTitle(), format.getContent());
        return new ResponseEntity("公告更新成功");
    }

    @RequiresAuthentication
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @DeleteMapping("/{aid}")
    public ResponseEntity delete(@PathVariable int aid) {
        announcementService.deleteAnnouncement(aid);
        return new ResponseEntity("公告删除成功");
    }
}
