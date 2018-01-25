package com.eagleoj.web.service;

import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.dao.AttachmentDao;
import com.eagleoj.web.entity.AttachmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class AttachmentService {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private AttachmentDao attachmentDao;

    public int add(int owner, String url) {
        // 添加attachment
        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setOwner(owner);
        attachmentEntity.setUrl(url);
        attachmentEntity.setUploadTime(System.currentTimeMillis());
        boolean flag = attachmentDao.addAttachment(sqlSession,attachmentEntity);

        return flag ? attachmentEntity.getAid():0;
    }

    public AttachmentEntity get(int aid) {
        return attachmentDao.getAttachment(sqlSession, aid);
    }
}
