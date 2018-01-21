package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.SettingDao;
import org.inlighting.oj.web.entity.SettingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class SettingService {

    @Autowired
    private SettingDao settingDao;

    @Autowired
    private SqlSession sqlSession;

    public List<SettingEntity> getAll() {
        return settingDao.getAll(sqlSession);
    }
}
