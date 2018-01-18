package org.inlighting.oj.web.service;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.AnnouncementDao;
import org.inlighting.oj.web.entity.AnnouncementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class AnnouncementService {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private AnnouncementDao announcementDao;

    public boolean addOne(String title, String content) {
        AnnouncementEntity entity = new AnnouncementEntity();
        entity.setTitle(title);
        entity.setContent(content);
        entity.setCreateTime(System.currentTimeMillis());
        return announcementDao.addOne(sqlSession, entity);
    }

    public List<AnnouncementEntity> getAll() {
        return announcementDao.getAll(sqlSession);
    }

    public boolean updateOne(int aid, String title, String content) {
        AnnouncementEntity entity = new AnnouncementEntity();
        entity.setAid(aid);
        entity.setTitle(title);
        entity.setContent(content);
        return announcementDao.updateOne(sqlSession, entity);
    }

    public boolean deleteOne(int aid) {
        return announcementDao.deleteOne(sqlSession, aid);
    }
}
