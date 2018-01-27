package com.eagleoj.web.dao;

import com.eagleoj.web.entity.TestCaseEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Smith
 **/
@Repository
public interface TestCasesMapper {
    int save(TestCaseEntity testCaseEntity);

    List<TestCaseEntity> listTestCasesByPid(int pid);

    // todo wait to refactor
    int updateByTidPid(@Param("tid") int tid, @Param("pid")int pid, @Param("data") TestCaseEntity data);

    int deleteByTid(int tid);

    int countByPid(int pid);
}
