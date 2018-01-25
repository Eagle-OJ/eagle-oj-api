package com.eagleoj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.crypto.hash.Md5Hash;
import com.eagleoj.web.dao.UserDao;
import com.eagleoj.web.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author Smith
 **/
@Service
public class UserService {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private UserDao userDao;

    @Value("${eagle-oj.default.avatar}")
    private String DEFAULT_AVATAR;

    public int addUser(String email, String nickname, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setNickname(nickname);
        userEntity.setPassword(new Md5Hash(password).toString());
        userEntity.setPermission(new JSONArray());
        userEntity.setRegisterTime(System.currentTimeMillis());

        boolean result = userDao.addUser(sqlSession, userEntity);
        return result ? userEntity.getUid() : 0;
    }

    public UserEntity getUserByLogin(String email, String password) {
        return userDao.getUserByLogin(sqlSession, email, new Md5Hash(password).toString());
    }

    public UserEntity getUserByUid(int uid) {
        return userDao.getUserByUid(sqlSession, uid);
    }

    public UserEntity getUserByEmail(String email) {
        return userDao.getUserByEmail(sqlSession, email);
    }

    public List<HashMap<String, Object>> getModeratorsInUidList(List<Integer> uidList) {
        List<HashMap<String, Object>> moderators = userDao.getModeratorsInUidList(sqlSession, uidList);
        for (HashMap<String, Object> map: moderators) {
            if (! map.containsKey("avatar")) {
                map.put("avatar", DEFAULT_AVATAR);
            }
        }
        return moderators;
    }

    public boolean updateUserProfile(int uid, String nickname, String motto, int gender) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUid(uid);
        userEntity.setNickname(nickname);
        userEntity.setMotto(motto);
        userEntity.setGender(gender);
        return userDao.updateUserProfile(sqlSession, userEntity);
    }

    public boolean updateUserAvatar(int uid, int avatar) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUid(uid);
        userEntity.setAvatar(avatar);
        return userDao.updateUserAvatar(sqlSession, userEntity);
    }

    public boolean updateUserTimes(int uid, UserEntity userEntity) {
        userEntity.setUid(uid);
        return userDao.updateUserTimes(sqlSession, userEntity);
    }
}
