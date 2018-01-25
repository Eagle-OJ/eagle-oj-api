package com.eagleoj.web.service;

import com.eagleoj.web.entity.AttachmentEntity;

/**
 * @author Smith
 **/
public interface AttachmentService {

    int save(int owner, String url);

    AttachmentEntity getByAid(int aid);
}
