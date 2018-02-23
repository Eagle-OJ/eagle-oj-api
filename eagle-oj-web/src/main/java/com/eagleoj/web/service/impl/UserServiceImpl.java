package com.eagleoj.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.eagleoj.web.cache.CacheController;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.dao.UserMapper;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.file.FileService;
import com.eagleoj.web.mail.MailService;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.AttachmentService;
import com.eagleoj.web.service.UserService;
import com.eagleoj.web.setting.SettingService;
import com.eagleoj.web.util.FileUtil;
import com.eagleoj.web.util.WebUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Service
public class UserServiceImpl implements UserService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private MailService mailService;

    @Override
    public void register(String email, String nickname, String password) throws WebErrorException {
        UserEntity origin = null;
        try {
            origin = getUserByEmail(email);
        } catch (Exception e) {}
        WebUtil.assertNull(origin, "邮箱已被注册");

        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setEmail(email);
        newUserEntity.setNickname(nickname);
        newUserEntity.setPassword(new Md5Hash(password).toString());
        newUserEntity.setPermission(new JSONArray());
        newUserEntity.setRegisterTime(System.currentTimeMillis());

        boolean flag = userMapper.save(newUserEntity) == 1;
        WebUtil.assertIsSuccess(flag, "用户注册失败");
    }

    @Override
    public int countUsers() {
        return userMapper.count();
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
        UserEntity userEntity = userMapper.getByEmail(email);
        WebUtil.assertNotNull(userEntity, "不存在此用户");
        return userEntity;
    }

    @Override
    public List<UserEntity> listAll() {
        return userMapper.listAll();
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
    public void updateUser(int uid, UserEntity userEntity) {
        WebUtil.assertIsSuccess(userMapper.updateByUid(uid, userEntity) == 1, "用户信息更新失败");
    }

    @Transactional
    @Override
    public void uploadUserAvatar(Integer uid, MultipartFile file) {
        InputStream is = null;
        try {
            is = file.getInputStream();
        } catch (IOException e) {
            throw new WebErrorException(e.getMessage());
        }
        String filePath = fileService.uploadAvatar(is, "jpg");
        int aid = attachmentService.save(uid, filePath);
        UserEntity userEntity = new UserEntity();
        userEntity.setAvatar(aid);
        boolean flag = userMapper.updateByUid(uid, userEntity) == 1;
        WebUtil.assertIsSuccess(flag, "头像更新失败");
    }

    @Override
    public void updateUserPassword(int uid, String oldPassword, String newPassword) {
        UserEntity userEntity = getUserByUid(uid);
        if (! userEntity.getPassword().equals(new Md5Hash(oldPassword).toString())) {
            throw new WebErrorException("原密码错误");
        }

        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setPassword(new Md5Hash(newPassword).toString());
        boolean flag = userMapper.updateByUid(uid, newUserEntity) == 1;
        WebUtil.assertIsSuccess(flag, "密码更新失败");
        CacheController.getAuthCache().remove(SessionHelper.get().getToken());
    }

    @Override
    public void updateUserEmail(int uid, String email) {
        UserEntity origin = null;
        try {
            origin = getUserByEmail(email);
        } catch (Exception e) {}
        WebUtil.assertNull(origin, "邮箱已被使用");

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setVerified(0);
        updateUser(uid, userEntity);
        CacheController.getAuthCache().remove(SessionHelper.get().getToken());
    }

    @Override
    public void verifyUserEmail(int uid, String code) {
        UserEntity userEntity = getUserByUid(uid);
        String rightCode = new Md5Hash(userEntity.getUid()+userEntity.getEmail()+userEntity.getPassword()).toString();
        if (! rightCode.equals(code)) {
            throw new WebErrorException("邮箱验证失败");
        }

        UserEntity update = new UserEntity();
        update.setVerified(1);
        updateUser(uid, update);
    }

    public void resetUserPassword(String email, String password, String code) {
        UserEntity userEntity = getUserByEmail(email);
        String secret = new Md5Hash(userEntity.getUid()+userEntity.getEmail()+userEntity.getPassword()).toString();
        if (! code.equals(secret)) {
            throw new WebErrorException("密钥错误，无法重置密码");
        }

        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setPassword(new Md5Hash(password).toString());
        updateUser(userEntity.getUid(), newUserEntity);
    }
}
