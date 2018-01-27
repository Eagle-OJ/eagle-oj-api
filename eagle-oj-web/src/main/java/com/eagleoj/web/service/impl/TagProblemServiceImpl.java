package com.eagleoj.web.service.impl;

import com.eagleoj.web.controller.exception.WebErrorException;
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
    public void saveProblemTag(int tid, int pid) {
        TagProblemEntity entity = new TagProblemEntity();
        entity.setTid(tid);
        entity.setPid(pid);
        boolean flag = tagProblemMapper.save(entity) == 1;
        if (! flag) {
            throw new WebErrorException("题目标签添加失败");
        }
    }

    @Override
    public void deleteProblemTags(int pid) {
        boolean flag = tagProblemMapper.deleteByPid(pid) > 0;
        if (! flag) {
            throw new WebErrorException("题目标签删除失败");
        }
    }

    @Override
    public List<TagProblemEntity> getProblemTags(int pid) {
        return tagProblemMapper.listByPid(pid);
    }
}
