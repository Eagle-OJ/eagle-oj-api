package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.AnnouncementMapper;
import com.eagleoj.web.entity.AnnouncementEntity;
import com.eagleoj.web.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementMapper mapper;

    @Override
    public boolean save(String title, String content) {
        AnnouncementEntity data = new AnnouncementEntity();
        data.setTitle(title);
        data.setContent(content);
        data.setCreateTime(System.currentTimeMillis());
        return mapper.save(data) == 1;
    }

    @Override
    public List<AnnouncementEntity> listAll() {
        return mapper.listAll();
    }

    @Override
    public boolean updateByAid(int aid, String title, String content) {
        AnnouncementEntity data = new AnnouncementEntity();
        data.setTitle(title);
        data.setContent(content);
        return mapper.updateByAid(aid, data) == 1;
    }

    @Override
    public boolean deleteByAid(int aid) {
        return mapper.deleteByAid(aid) == 1;
    }
}
