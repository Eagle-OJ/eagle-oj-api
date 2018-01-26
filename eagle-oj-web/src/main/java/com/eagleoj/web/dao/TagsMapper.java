package com.eagleoj.web.dao;

import com.eagleoj.web.entity.TagEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public interface TagsMapper {
    List<TagEntity> listAll();

    int addUsedTimesByTid(int tid);
}
