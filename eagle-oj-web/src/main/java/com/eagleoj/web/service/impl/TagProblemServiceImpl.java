package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.TagProblemMapper;
import com.eagleoj.web.entity.TagProblemEntity;
import com.eagleoj.web.service.TagProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class TagProblemServiceImpl implements TagProblemService {

    @Autowired
    private TagProblemMapper tagProblemMapper;

    @Override
    public boolean save(int tid, int pid) {
        TagProblemEntity entity = new TagProblemEntity();
        entity.setTid(tid);
        entity.setPid(pid);
        return tagProblemMapper.save(entity) == 1;
    }

    @Override
    public boolean delete(int pid) {
        return tagProblemMapper.deleteByPid(pid) == 1;
    }

    @Override
    public List<TagProblemEntity> getProblemTags(int pid) {
        return tagProblemMapper.listByPid(pid);
    }
}
