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
    public boolean addUser(UserEntity userEntity) {
        SqlSession session = DataHelper.getSession();
        try {
            return session.insert("user.insertUser", userEntity) == 1;
        } catch (Exception e) {
            LOGGER.info(e.getMessage(), e);
            return false;
        } finally {
            session.close();
        }
    }

    /**
     *
     * @param email 邮箱
     * @return 不存在则返回空
     */
    public UserEntity getUserByEmail(String email) {
        SqlSession session = DataHelper.getSession();
        try {
            return session.selectOne("user.selectUserByEmail", email);
        } catch (Exception e) {
            return null;
        } finally {
            session.close();
        }
    }

    public UserEntity getUserByUid(int uid) {
        SqlSession session = DataHelper.getSession();
        try {
            return session.selectOne("user.selectUserByUid", uid);
        } catch (Exception e) {
            return null;
        } finally {
            session.close();
        }
    }

    public UserEntity getUserByLogin(String email, String password) {
        SqlSession session = DataHelper.getSession();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);
            return session.selectOne("user.selectUserByLogin", map);
        } catch (Exception e) {
            return null;
        } finally {
            session.close();
        }
    }

    public List<UserEntity> getUserList(int currentPage, int pageSize) {
        // todo
        return null;
    }
}
