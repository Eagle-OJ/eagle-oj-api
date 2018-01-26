package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.TagsMapper;
import com.eagleoj.web.entity.TagEntity;
import com.eagleoj.web.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagsMapper tagsMapper;

    @Override
    public List<TagEntity> listAll() {
        return tagsMapper.listAll();
    }

    @Override
    public boolean addUsedTimes(int tid) {
        return tagsMapper.addUsedTimesByTid(tid) == 1;
    }
}
