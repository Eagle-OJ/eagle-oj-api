package com.eagleoj.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.dao.UserMapper;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.service.AttachmentService;
import com.eagleoj.web.service.UserService;
import com.eagleoj.web.util.FileUtil;
import com.eagleoj.web.util.WebUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private AttachmentService attachmentService;

    @Value("${eagle-oj.default.avatar}")
    private String DEFAULT_AVATAR;

    @Override
    public int register(String email, String nickname, String password) throws WebErrorException {
        UserEntity userEntity = getUserByEmail(email);
        WebUtil.assertNull(userEntity, "邮箱已被注册");

        return save(email, nickname, password);
    }

    @Override
    public UserEntity login(String email, String password) throws WebErrorException{
        UserEntity data = userMapper.getByEmailPassword(email, new Md5Hash(password).toString());
        if (data == null) {
            throw new WebErrorException("用户名或密码错误");
        }
        return data;
    }

    @Override
    public UserEntity getUserByUid(Integer uid) {
        UserEntity userEntity = userMapper.getByUid(uid);
        WebUtil.assertNotNull(userEntity, "不存在此用户");
        return userEntity;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userMapper.getByEmail(email);
    }

    @Override
    public void updateUserProfile(Integer uid, String nickname, String motto, Integer gender) throws WebErrorException {
        UserEntity userEntity = new UserEntity();
        userEntity.setNickname(nickname);
        userEntity.setMotto(motto);
        userEntity.setGender(gender);
        if (userMapper.updateByUid(uid, userEntity) != 1) {
            throw new WebErrorException("用户个人信息更新失败");
        }
    }

    @Override
    public void updateUserTimes(Integer uid, UserEntity userEntity) {
        if (userMapper.updateByUid(uid, userEntity) != 1) {
            throw new WebErrorException("更新用户提交记录失败");
        }
    }

    @Transactional
    @Override
    public void uploadUserAvatar(Integer uid, MultipartFile file) {
        try {
            String filePath = fileUtil.uploadAvatar(file.getInputStream(), "jpg");
            int aid = attachmentService.save(uid, filePath);
            UserEntity userEntity = new UserEntity();
            userEntity.setAvatar(aid);
            userMapper.updateByUid(uid, userEntity);
        } catch (Exception e) {
            throw new WebErrorException("头像更新失败");
        }

    }

    private int save(String email, String nickname, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setNickname(nickname);
        userEntity.setPassword(new Md5Hash(password).toString());
        userEntity.setPermission(new JSONArray());
        userEntity.setRegisterTime(System.currentTimeMillis());

        boolean result = userMapper.save(userEntity) == 1;
        return result ? userEntity.getUid() : 0;
    }
}
