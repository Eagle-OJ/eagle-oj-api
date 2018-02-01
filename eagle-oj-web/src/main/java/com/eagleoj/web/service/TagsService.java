package com.eagleoj.web.service;

import com.eagleoj.web.entity.TagEntity;

import java.util.List;

/**
 * @author Smith
 **/
public interface TagsService {

    TagEntity getByName(String name);

    void save(String name);

    void deleteTag(int tid);

    void updateTag(int tid, String name);

    List<TagEntity> listAll();

    void addUsedTimes(int tid);

    void updateTagName(int tid, String name);
}
