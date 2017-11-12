package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.session.SqlSession;
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
        userEntity.setPassword(password);
        userEntity.setPermission(new JSONArray());
        userEntity.setRegisterTime(registerTime);

        boolean result = userDao.addUser(sqlSession, userEntity);
        return result ? userEntity.getUid() : 0;
    }

    public UserEntity getUserByLogin(String email, String password) {
        return userDao.getUserByLogin(sqlSession, email, password);
    }

    public UserEntity getUserByUid(int uid) {
        return userDao.getUserByUid(sqlSession, uid);
    }

    public UserEntity getUserByEmail(String email) {
        return userDao.getUserByEmail(sqlSession, email);
    }
}
