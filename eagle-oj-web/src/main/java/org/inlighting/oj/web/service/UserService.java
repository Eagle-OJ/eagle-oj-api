package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.inlighting.oj.web.dao.UserDao;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean addUser(String email, String nickname, String password) {
        if (userDao.getUserByEmail(email)!=null) {
            throw new RuntimeException("用户已经存在");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setNickname(nickname);
        userEntity.setPassword(new Md5Hash(password).toString());
        userEntity.setPermission(new JSONArray());
        userEntity.setRegisterTime(System.currentTimeMillis());
        if (userDao.addUser(userEntity)) {
            return true;
        } else {
            throw new RuntimeException("用户注册失败");
        }
    }

    public UserEntity getUserByLogin(String email, String password) {
        return userDao.getUserByLogin(email, new Md5Hash(password).toString());
    }

    public UserEntity getUserByUid(int uid) {
        return userDao.getUserByUid(uid);
    }
}
