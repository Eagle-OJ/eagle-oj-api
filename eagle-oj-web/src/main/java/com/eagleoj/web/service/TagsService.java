package com.eagleoj.web.service;

import com.eagleoj.web.entity.TagEntity;

import java.util.List;

/**
 * @author Smith
 **/
public interface TagsService {

    List<TagEntity> listAll();

    boolean addUsedTimes(int tid);
}
