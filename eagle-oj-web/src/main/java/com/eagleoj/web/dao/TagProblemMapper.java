package com.eagleoj.web.dao;

import com.eagleoj.web.entity.TagProblemEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public interface TagProblemMapper {
    int save(TagProblemEntity tagProblemEntity);

    List<TagProblemEntity> listByPid(int pid);

    int deleteByPid(int pid);

    int countByTid(int tid);
}
