package com.eagleoj.web.controller;

import com.eagleoj.web.cache.CacheController;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.user.*;
import com.eagleoj.web.mail.MailService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import com.eagleoj.web.controller.format.user.UpdateUserProfileFormat;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.ContestUserService;
import com.eagleoj.web.service.GroupUserService;
import com.eagleoj.web.service.UserService;
import com.eagleoj.web.util.WebUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
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
    private GroupUserService groupUserService;

    @Autowired
    private ContestUserService contestUserService;

    @Autowired
    private MailService mailService;

    @ApiOperation("获取当前用户的所有信息")
    @GetMapping
    @RequiresAuthentication
    public ResponseEntity getUserInfo() {
        int uid = SessionHelper.get().getUid();
        UserEntity userEntity = userService.getUserByUid(uid);
        userEntity.setPassword(null);
        return new ResponseEntity(userEntity);
    }

    @ApiOperation("获取指定用户的所有信息")
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @GetMapping("/{uid}")
    public ResponseEntity getUserInfo(@PathVariable int uid) {
        UserEntity userEntity = userService.getUserByUid(uid);
        return new ResponseEntity(userEntity);
    }

    @ApiOperation("更新用户的信息")
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @PutMapping("/{uid}")
    public ResponseEntity updateUserInfo(@PathVariable int uid,
                                         @RequestBody @Valid UpdateUserFormat format) {
        if (format.getRole() != null) {
            int role = SessionHelper.get().getRole();
            if (format.getRole() > role) {
                throw new WebErrorException("你丫想上天");
            }
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(format.getEmail());
        userEntity.setNickname(format.getNickname());
        if (format.getPassword() != null) {
            userEntity.setPassword(new Md5Hash(format.getPassword()).toString());
        }
        userEntity.setRole(format.getRole());
        userEntity.setGender(format.getGender());
        userEntity.setMotto(format.getMotto());
        userService.updateUser(uid, userEntity);
        CacheController.getAuthCache().remove(SessionHelper.get().getToken());
        return new ResponseEntity("更新成功");
    }

    @ApiOperation("更新用户的信息")
    @RequiresAuthentication
    @PostMapping("/profile/edit")
    public ResponseEntity updateUserProfile(@Valid @RequestBody UpdateUserProfileFormat format) {
        int uid = SessionHelper.get().getUid();
        userService.updateUserProfile(uid, format.getNickname(), format.getMotto(), format.getGender());
        return new ResponseEntity("更新成功");
    }

    @ApiOperation("发送邮箱确认邮件")
    @RequiresAuthentication
    @PostMapping("/mail/check")
    public ResponseEntity checkUserEmail(@RequestBody @Valid SendMailFormat format) {
        if (format.getUrl() == null || format.getUrl().length() == 0) {
            throw new WebErrorException("url地址不得为空");
        }

        UserEntity userEntity = userService.getUserByUid(SessionHelper.get().getUid());
        if (! mailService.sendConfirmMessage(format.getUrl(), userEntity)) {
            throw new WebErrorException("邮件发送失败");
        }
        return new ResponseEntity("邮件发送成功");
    }

    @ApiOperation("进行邮箱确认")
    @RequiresAuthentication
    @PostMapping("/mail/verify")
    public ResponseEntity verifyUserEmail(@RequestBody @Valid VerifyUserEmailFormat format) {
        String code = format.getCode();
        userService.verifyUserEmail(SessionHelper.get().getUid(), code);
        return new ResponseEntity("邮箱验证成功");
    }

    @ApiOperation("更新邮箱")
    @RequiresAuthentication
    @PutMapping("/mail")
    public ResponseEntity updateUserEmail(@RequestBody @Valid SendMailFormat format) {
        String email = format.getMail();
        if (email == null || email.length() == 0) {
            throw new WebErrorException("邮箱不得为空");
        }

        userService.updateUserEmail(SessionHelper.get().getUid(), email);
        return new ResponseEntity("邮箱更新成功");
    }

    @ApiOperation("更新用户的密码")
    @RequiresAuthentication
    @PutMapping("/profile/password")
    public ResponseEntity updateUserPassword(@Valid @RequestBody UpdateUserPasswordFormat format) {
        int uid = SessionHelper.get().getUid();
        userService.updateUserPassword(uid, format.getOriginPassword(), format.getNewPassword());
        return new ResponseEntity("密码更新成功");
    }

    @ApiOperation("头像上传")
    @RequiresAuthentication
    @PostMapping("/profile/avatar")
    public ResponseEntity uploadAvatar(@RequestParam("file") MultipartFile file) throws IOException {

        int uid = SessionHelper.get().getUid();

        if (file.getSize()/1000 > 2*1024) {
            throw new WebErrorException("文件过大");
        }

        if (! file.getContentType().equals("image/jpeg")) {
            throw new WebErrorException("文件格式非法");
        }

        userService.uploadUserAvatar(uid, file);
        return new ResponseEntity("头像更新成功");
    }

    @ApiOperation("获取用户参加的比赛")
    @RequiresAuthentication
    @GetMapping("/joined_contests")
    public ResponseEntity getUserContests(@RequestParam(name = "page") int page,
                                          @RequestParam(name = "page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        int uid = SessionHelper.get().getUid();
        List<Map<String, Object>> list = contestUserService.listUserJoinedContests(uid);
        return new ResponseEntity(WebUtil.generatePageData(pager, list));
    }

    @ApiOperation("获取用户参加的小组")
    @RequiresAuthentication
    @GetMapping("/joined_groups")
    public ResponseEntity getUserGroups(@RequestParam(name = "page") int page,
                                        @RequestParam(name = "page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        int uid = SessionHelper.get().getUid();
        List<Map<String, Object>> list = groupUserService.listUserJoinedGroups(uid);
        return new ResponseEntity(WebUtil.generatePageData(pager, list));
    }
}
