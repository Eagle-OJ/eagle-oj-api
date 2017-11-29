package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.inlighting.oj.web.dao.UserDao;
import org.inlighting.oj.web.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class UserService {

    private final SqlSession sqlSession;

    private UserDao userDao;

    public UserService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public int addUser(String email, String nickname, String password, long registerTime) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setNickname(nickname);
        userEntity.setPassword(new Md5Hash(password).toString());
        userEntity.setPermission(new JSONArray());
        userEntity.setRegisterTime(registerTime);

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

    public boolean updateUserProfile(int uid, String nickname, String realName, String motto, int gender) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUid(uid);
        userEntity.setNickname(nickname);
        userEntity.setRealName(realName);
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

    public boolean addUserSubmitTimes(int uid) {
        return userDao.addUserSubmitTimesByUid(sqlSession, uid);
    }

    public boolean addUserAcceptTimes(int uid) {
        return userDao.addUserAcceptTimesByUid(sqlSession, uid);
    }
}
