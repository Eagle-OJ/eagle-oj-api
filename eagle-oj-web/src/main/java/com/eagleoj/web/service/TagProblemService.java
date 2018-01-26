package com.eagleoj.web.service;

import org.apache.ibatis.session.SqlSession;
import com.eagleoj.web.dao.TagProblemDao;
import com.eagleoj.web.entity.TagProblemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public interface TagProblemService {

    boolean save(int tid, int pid);

    boolean delete(int pid);

    List<TagProblemEntity> getProblemTags(int pid);
}
