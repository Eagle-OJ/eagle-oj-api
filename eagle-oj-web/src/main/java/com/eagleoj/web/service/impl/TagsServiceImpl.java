package com.eagleoj.web.service.impl;

import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.dao.TagsMapper;
import com.eagleoj.web.entity.TagEntity;
import com.eagleoj.web.service.TagProblemService;
import com.eagleoj.web.service.TagsService;
import com.eagleoj.web.util.WebUtil;
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

    @Autowired
    private TagProblemService tagProblemService;

    @Override
    public int save(String name) {
        TagEntity tagEntity = null;
        try {
            tagEntity = getByName(name);
        } catch (Exception e) { }
        WebUtil.assertNull(tagEntity, "已经存在此标签");
        TagEntity newEntity = new TagEntity();
        newEntity.setName(name);
        boolean flag = tagsMapper.save(newEntity) == 1;
        WebUtil.assertIsSuccess(flag, "保存标签失败");
        return newEntity.getTid();
    }

    @Override
    public TagEntity getByName(String name) {
        TagEntity tagEntity = tagsMapper.getByName(name);
        WebUtil.assertNotNull(tagEntity, "不存在此标签");
        return tagEntity;
    }

    @Override
    public void deleteTag(int tid) {
        int problems = tagProblemService.countTagProblems(tid);
        if (problems != 0) {
            throw new WebErrorException("此标签已经被使用，无法删除");
        }
        boolean flag = tagsMapper.deleteByTid(tid) == 1;
        WebUtil.assertIsSuccess(flag, "删除标签失败");
    }

    @Override
    public List<TagEntity> listAll() {
        return tagsMapper.listAll();
    }

    @Override
    public void addUsedTimes(int tid) {
        TagEntity tagEntity = new TagEntity();
        tagEntity.setUsed(1);
        boolean flag = tagsMapper.updateByTid(tid, tagEntity) == 1;
        WebUtil.assertIsSuccess(flag, "增加标签使用次数失败");
    }

    public void updateTagName(int tid, String name) {
        TagEntity tagEntity = null;
        try {
            tagEntity = getByName(name);
        } catch (Exception e) { }
        WebUtil.assertNull(tagEntity, "已经存在此标签");

        TagEntity newTagEntity = new TagEntity();
        newTagEntity.setName(name);
        boolean flag = tagsMapper.updateByTid(tid, newTagEntity) == 1;
        WebUtil.assertIsSuccess(flag, "更新标签名称失败");
    }
}
