package org.inlighting.oj.web.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.controller.format.user.AddProblemTestCaseFormat;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.controller.format.user.AddProblemFormat;
import org.inlighting.oj.web.entity.UserEntity;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.security.UserSession;
import org.inlighting.oj.web.service.AttachmentService;
import org.inlighting.oj.web.service.ProblemService;
import org.inlighting.oj.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    private AttachmentService attachmentService;

    @Value("${eagle-oj.default.avatar}")
    private String DEFAULT_AVATAR;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @ApiOperation("获取用户的所有信息")
    @GetMapping("/info")
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


}
