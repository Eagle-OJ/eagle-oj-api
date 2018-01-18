package org.inlighting.oj.web.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.controller.format.admin.AddAnnouncementFormat;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.service.AnnouncementService;
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

    @GetMapping
    public ResponseEntity get() {
        return new ResponseEntity(announcementService.getAll());
    }

    @RequiresAuthentication
    @RequiresRoles("9")
    @PostMapping
    public ResponseEntity add(@RequestBody @Valid AddAnnouncementFormat format) {
        if(! announcementService.addOne(format.getTitle(), format.getContent())) {
            throw new WebErrorException("公告添加失败");
        }
        return new ResponseEntity("公告添加成功");
    }

    @RequiresAuthentication
    @RequiresRoles("9")
    @PutMapping("/{aid}")
    public ResponseEntity update(@PathVariable int aid,
                                 @RequestBody @Valid AddAnnouncementFormat format) {
        if (! announcementService.updateOne(aid, format.getTitle(), format.getContent())) {
            throw new WebErrorException("公告更新失败");
        }
        return new ResponseEntity("公告更新成功");
    }

    @RequiresAuthentication
    @RequiresRoles("9")
    @DeleteMapping("/{aid}")
    public ResponseEntity delete(@PathVariable int aid) {
        if (! announcementService.deleteOne(aid)) {
            throw new WebErrorException("公告删除失败");
        }
        return new ResponseEntity("公告删除成功");
    }
}
