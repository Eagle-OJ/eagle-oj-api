package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.inlighting.oj.web.dao.UserDao;
import org.inlighting.oj.web.data.DataHelper;
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
        SqlSession session = DataHelper.getSession();
        if (userDao.getUserByEmail(session, email)!=null) {
            throw new RuntimeException("用户已经存在");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setNickname(nickname);
        userEntity.setPassword(new Md5Hash(password).toString());
        userEntity.setPermission(new JSONArray());
        userEntity.setRegisterTime(System.currentTimeMillis());
        if (! userDao.addUser(session, userEntity)) {
            throw new RuntimeException("用户注册失败");
        }
        session.close();
        return true;
    }

    public UserEntity getUserByLogin(String email, String password) {
        SqlSession session = DataHelper.getSession();
        UserEntity userEntity = userDao.getUserByLogin(session, email, new Md5Hash(password).toString());
        session.close();
        return userEntity;
    }

    public UserEntity getUserByUid(int uid) {
        SqlSession session = DataHelper.getSession();
        UserEntity userEntity = userDao.getUserByUid(session, uid);
        session.close();
        return userEntity;
    }

}
