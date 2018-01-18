package org.inlighting.oj.web.dao;

import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.entity.AnnouncementEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public class AnnouncementDao {

    public boolean addOne(SqlSession sqlSession, AnnouncementEntity entity) {
        return sqlSession.insert("announcement.addOne", entity) == 1;
    }

    public List<AnnouncementEntity> getAll(SqlSession sqlSession) {
        return sqlSession.selectList("announcement.getAll");
    }

    public boolean updateOne(SqlSession sqlSession, AnnouncementEntity entity) {
        return sqlSession.update("announcement.updateOne", entity) == 1;
    }

    public boolean deleteOne(SqlSession sqlSession, int aid) {
        return sqlSession.delete("announcement.deleteOne", aid) == 1;
    }
}
