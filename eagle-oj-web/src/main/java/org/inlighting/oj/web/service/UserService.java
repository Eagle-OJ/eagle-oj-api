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

    public int addUser(String email, String nickname, String password, long registerTime) {
        SqlSession session = DataHelper.getSession();
        if (userDao.getUserByEmail(session, email)!=null) {
            throw new RuntimeException("用户已经存在");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setNickname(nickname);
        userEntity.setPassword(password);
        userEntity.setPermission(new JSONArray());
        userEntity.setRegisterTime(registerTime);

        boolean result = userDao.addUser(session, userEntity);
        session.close();
        return result ? userEntity.getUid() : 0;
    }

    public UserEntity getUserByLogin(String email, String password) {
        SqlSession session = DataHelper.getSession();
        UserEntity userEntity = userDao.getUserByLogin(session, email, password);
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
