package com.eagleoj.web.service;

import com.eagleoj.web.entity.AnnouncementEntity;

import java.util.List;

/**
 * @author Smith
 **/
public interface AnnouncementService {

    void saveAnnouncement(String title, String content);

    List<AnnouncementEntity> listAllAnnouncements();

    void updateAnnouncement(int aid, String title, String content);

    void deleteAnnouncement(int aid);

}
