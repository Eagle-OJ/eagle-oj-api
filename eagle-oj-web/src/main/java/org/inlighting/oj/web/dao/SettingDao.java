package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.SettingEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public class SettingDao {

    public List<SettingEntity> getAll(SqlSession sqlSession) {
        return sqlSession.selectList("setting.getAll");
    }
}
