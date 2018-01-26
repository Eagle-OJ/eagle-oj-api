package com.eagleoj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.user.UpdateUserProfileFormat;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.user.UpdateUserProfileFormat;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.AttachmentService;
import com.eagleoj.web.service.ContestUserService;
import com.eagleoj.web.service.GroupUserService;
import com.eagleoj.web.service.UserService;
import com.eagleoj.web.util.FileUtil;
import com.eagleoj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private ContestUserService contestUserService;

    @Autowired
    private FileUtil fileUtil;

    @ApiOperation("获取用户的所有信息")
    @GetMapping("/info")
    @RequiresAuthentication
    public ResponseEntity getUserInfo() {
        int uid = SessionHelper.get().getUid();
        UserEntity userEntity = userService.getUserByUid(uid);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(userEntity));
        jsonObject.remove("password");
        return new ResponseEntity(jsonObject);
    }

    @ApiOperation("更新用户的信息")
    @PostMapping("/profile/edit")
    @RequiresAuthentication
    public ResponseEntity updateUserProfile(@Valid @RequestBody UpdateUserProfileFormat format) {
        int uid = SessionHelper.get().getUid();
        if (! userService.updateUserProfile(uid, format.getNickname(), format.getMotto(),
                format.getGender())) {
            throw new WebErrorException("更新用户失败");
        }
        return new ResponseEntity("更新成功");
    }

    @ApiOperation("获取用户参加的比赛")
    @RequiresAuthentication
    @GetMapping("/contests")
    public ResponseEntity getUserContests(@RequestParam(name = "page") int page,
                                          @RequestParam(name = "page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        int uid = SessionHelper.get().getUid();
        List<Map<String, Object>> list = contestUserService.listUserContests(uid);
        return new ResponseEntity(WebUtil.generatePageData(pager, list));
    }

    @ApiOperation("获取用户参加的小组")
    @RequiresAuthentication
    @GetMapping("/groups")
    public ResponseEntity getUserGroups(@RequestParam(name = "page") int page,
                                        @RequestParam(name = "page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        int uid = SessionHelper.get().getUid();
        List<Map<String, Object>> list = groupUserService.listGroupMembers(uid);
        return new ResponseEntity(WebUtil.generatePageData(pager, list));
    }

    @ApiOperation("头像上传")
    @PostMapping("/profile/avatar")
    @RequiresAuthentication
    public ResponseEntity uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {

        int uid = SessionHelper.get().getUid();

        if (file.getSize()/1000 > 2*1024) {
            throw new WebErrorException("文件过大");
        }

        if (! file.getContentType().equals("image/jpeg")) {
            throw new WebErrorException("文件格式非法");
        }
        String filePath = fileUtil.uploadAvatar(file.getInputStream(), "jpg");

        if (filePath == null) {
            throw new WebErrorException("文件上传失败");
        }

        int aid = attachmentService.save(uid, filePath);
        if (! userService.updateUserAvatar(uid, aid)) {
            throw new WebErrorException("头像更新失败");
        }
        return new ResponseEntity("头像更新成功");
    }
}
