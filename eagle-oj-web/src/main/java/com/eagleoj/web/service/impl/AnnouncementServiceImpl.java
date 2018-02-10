package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.AnnouncementMapper;
import com.eagleoj.web.entity.AnnouncementEntity;
import com.eagleoj.web.service.AnnouncementService;
import com.eagleoj.web.util.WebUtil;
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
    public void saveAnnouncement(String title, String content) {
        AnnouncementEntity data = new AnnouncementEntity();
        data.setTitle(title);
        data.setContent(content);
        data.setCreateTime(System.currentTimeMillis());
        WebUtil.assertIsSuccess(mapper.save(data) == 1, "保存公告失败");
    }

    @Override
    public List<AnnouncementEntity> listAllAnnouncements() {
        return mapper.listAll();
    }

    @Override
    public void updateAnnouncement(int aid, String title, String content) {
        AnnouncementEntity data = new AnnouncementEntity();
        data.setTitle(title);
        data.setContent(content);
        WebUtil.assertIsSuccess(mapper.updateByAid(aid, data) == 1, "修改公告失败");
    }

    @Override
    public void deleteAnnouncement(int aid) {
        WebUtil.assertIsSuccess(mapper.deleteByAid(aid) == 1, "删除公告失败");
    }
}
