package org.inlighting.oj.web.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageRowBounds;
import org.apache.ibatis.session.SqlSession;
import org.inlighting.oj.web.dao.MessageDao;
import org.inlighting.oj.web.entity.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class MessageService {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private MessageDao messageDao;

    public int addMessage(int owner, int type, String content, JSONObject json) {
        MessageEntity entity = new MessageEntity();
        entity.setOwner(owner);
        entity.setType(type);
        entity.setContent(content);

        if (json == null) {
            entity.setJsonContent(new JSONObject());
        } else {
            entity.setJsonContent(json);
        }

        entity.setCreateTime(System.currentTimeMillis());
        return messageDao.add(sqlSession, entity)? entity.getMid(): 0;
    }

    public List<MessageEntity> getMessage(int owner, PageRowBounds pager) {
        return messageDao.get(sqlSession, owner, pager);
    }
}
