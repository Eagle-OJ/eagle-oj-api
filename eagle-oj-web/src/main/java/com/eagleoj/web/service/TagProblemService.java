package com.eagleoj.web.service;

import com.eagleoj.web.entity.TagProblemEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public interface TagProblemService {

    void saveProblemTag(int tid, int pid);

    void deleteProblemTags(int pid);

    int countTagProblems(int tid);

    List<TagProblemEntity> getProblemTags(int pid);
}
