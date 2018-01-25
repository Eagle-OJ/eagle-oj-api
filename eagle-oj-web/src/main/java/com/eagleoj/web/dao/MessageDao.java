package com.eagleoj.web.dao;

import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.entity.MessageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public class MessageDao {

    public boolean add(SqlSession sqlSession, MessageEntity entity) {
        return sqlSession.insert("message.insert", entity) == 1;
    }

    public List<MessageEntity> get(SqlSession sqlSession, int owner, PageRowBounds pager) {
        return sqlSession.selectList("message.get", owner, pager);
    }
}
