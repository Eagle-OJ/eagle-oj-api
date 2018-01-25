package com.eagleoj.web.dao;

import com.eagleoj.web.entity.SettingEntity;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.entity.SettingEntity;
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

    public boolean addAll(SqlSession sqlSession, List<SettingEntity> list) {
        return sqlSession.insert("setting.insertAll", list) > 0;
    }

    public boolean updateOne(SqlSession sqlSession, SettingEntity entity) {
        return sqlSession.update("setting.updateOne", entity) == 1;
    }
}
