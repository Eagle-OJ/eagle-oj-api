package com.eagleoj.web.service;

import com.eagleoj.web.entity.AnnouncementEntity;

import java.util.List;

/**
 * @author Smith
 **/
public interface AnnouncementService {

    boolean save(String title, String content);

    List<AnnouncementEntity> listAll();

    boolean updateByAid(int aid, String title, String content);

    boolean deleteByAid(int aid);

}
