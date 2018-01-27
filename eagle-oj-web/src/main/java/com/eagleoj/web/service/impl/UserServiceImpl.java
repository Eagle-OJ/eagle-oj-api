package com.eagleoj.web.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.eagleoj.web.dao.UserMapper;
import com.eagleoj.web.entity.UserEntity;
import com.eagleoj.web.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    @Value("${eagle-oj.default.avatar}")
    private String DEFAULT_AVATAR;

    // todo
    @Override
    public int save(String email, String nickname, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setNickname(nickname);
        userEntity.setPassword(new Md5Hash(password).toString());
        userEntity.setPermission(new JSONArray());
        userEntity.setRegisterTime(System.currentTimeMillis());

        boolean result = userMapper.save(userEntity) == 1;
        return result ? userEntity.getUid() : 0;
    }

    @Override
    public UserEntity getUserByEmailPassword(String email, String password) {
        return userMapper.getByEmailPassword(email, new Md5Hash(password).toString());
    }

    @Override
    public UserEntity getUserByUid(int uid) {
        return userMapper.getByUid(uid);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userMapper.getByEmail(email);
    }

    @Override
    public List<Map<String, Object>> listModeratorsInUidList(List<Integer> uidList) {
        List<Map<String, Object>> moderators = userMapper.listModeratorsInUidList(uidList);
        for (Map<String, Object> map: moderators) {
            if (! map.containsKey("avatar")) {
                map.put("avatar", DEFAULT_AVATAR);
            }
        }
        return moderators;
    }

    @Override
    public boolean updateUserProfile(int uid, String nickname, String motto, int gender) {
        UserEntity userEntity = new UserEntity();
        userEntity.setNickname(nickname);
        userEntity.setMotto(motto);
        userEntity.setGender(gender);
        return userMapper.updateProfileByUid(uid, userEntity) == 1;
    }

    @Override
    public boolean updateUserAvatar(int uid, int avatar) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUid(uid);
        userEntity.setAvatar(avatar);
        return userMapper.updateAvatarByUid(uid, avatar) == 1;
    }

    @Override
    public boolean updateUserTimes(int uid, UserEntity userEntity) {
        return userMapper.updateTimesByUid(uid, userEntity) == 1;
    }
}
