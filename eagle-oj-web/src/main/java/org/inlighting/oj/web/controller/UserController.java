package org.inlighting.oj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.controller.format.user.UpdateUserProfileFormat;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.entity.UserEntity;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.AttachmentService;
import org.inlighting.oj.web.service.UserService;
import org.inlighting.oj.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    private UserService userService;

    private AttachmentService attachmentService;

    @Value("${eagle-oj.default.avatar}")
    private String DEFAULT_AVATAR;

    private FileUtil fileUtil;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @Autowired
    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @ApiOperation("获取用户的所有信息")
    @GetMapping("/info")
    @RequiresAuthentication
    public ResponseEntity getUserInfo() {
        int uid = SessionHelper.get().getUid();
        UserEntity userEntity = userService.getUserByUid(uid);
        String avatar;
        if (userEntity.getAvatar() == 0) {
            avatar = DEFAULT_AVATAR;
        } else {
            avatar = attachmentService.get(userEntity.getAvatar()).getUrl();
        }
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(userEntity));
        jsonObject.remove("password");
        jsonObject.replace("avatar", avatar);
        return new ResponseEntity(jsonObject);
    }

    @ApiOperation("更新用户的信息")
    @PostMapping("/profile/edit")
    @RequiresAuthentication
    public ResponseEntity updateUserProfile(@Valid @RequestBody UpdateUserProfileFormat format) {
        int uid = SessionHelper.get().getUid();
        if (! userService.updateUserProfile(uid, format.getNickname(), format.getRealName(), format.getMotto(),
                format.getGender())) {
            throw new WebErrorException("更新用户失败");
        }
        return new ResponseEntity("更新成功");
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

        int aid = attachmentService.add(uid, filePath);
        if (! userService.updateUserAvatar(uid, aid)) {
            throw new WebErrorException("头像更新失败");
        }
        return new ResponseEntity("头像更新成功");
    }
}
