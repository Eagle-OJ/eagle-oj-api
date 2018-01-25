package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.AttachmentMapper;
import com.eagleoj.web.entity.AttachmentEntity;
import com.eagleoj.web.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Smith
 **/
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentMapper mapper;

    @Override
    public int save(int owner, String url) {
        AttachmentEntity attachmentEntity = new AttachmentEntity();
        attachmentEntity.setOwner(owner);
        attachmentEntity.setUrl(url);
        attachmentEntity.setUploadTime(System.currentTimeMillis());
        boolean flag = mapper.save(attachmentEntity) == 1;
        return flag? attachmentEntity.getAid(): 0;
    }

    @Override
    public AttachmentEntity getByAid(int aid) {
        return mapper.getByAid(aid);
    }
}
