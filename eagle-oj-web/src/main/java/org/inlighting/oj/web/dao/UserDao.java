package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.inlighting.oj.web.data.DataHelper;
import org.inlighting.oj.web.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@Repository
public class UserDao {

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    /**
     * @param userEntity 注册用户数据
     * @return 是否成功
     */
    public boolean addUser(SqlSession session, UserEntity userEntity) {
        return session.insert("user.insertUser", userEntity) == 1;
    }

    /**
     *
     * @param email 邮箱
     * @return 不存在则返回空
     */
    public UserEntity getUserByEmail(SqlSession session, String email) {
        return session.selectOne("user.selectUserByEmail", email);
    }

    public UserEntity getUserByUid(SqlSession session, int uid) {
        return session.selectOne("user.selectUserByUid", uid);
    }

    public UserEntity getUserByLogin(SqlSession session, String email, String password) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        return session.selectOne("user.selectUserByLogin", map);
    }

    public List<UserEntity> getUserList(int currentPage, int pageSize) {
        // todo
        return null;
    }
}
