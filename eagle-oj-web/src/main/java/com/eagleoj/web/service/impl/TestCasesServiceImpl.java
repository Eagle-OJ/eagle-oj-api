package com.eagleoj.web.service.impl;

import com.eagleoj.web.dao.TestCasesMapper;
import com.eagleoj.web.entity.TestCaseEntity;
import com.eagleoj.web.service.TestCasesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Smith
 **/
@Service
public class TestCasesServiceImpl implements TestCasesService {

    @Autowired
    private TestCasesMapper testCasesMapper;

    @Override
    public int save(int pid, String stdin, String stdout, int strength) {
        // 添加一个TestCase
        TestCaseEntity testCaseEntity = new TestCaseEntity();
        testCaseEntity.setPid(pid);
        testCaseEntity.setStdin(stdin);
        testCaseEntity.setStdout(stdout);
        testCaseEntity.setStrength(strength);
        testCaseEntity.setCreateTime(System.currentTimeMillis());
        boolean flag = testCasesMapper.save(testCaseEntity) == 1;
        return flag ? testCaseEntity.getTid() : 0;
    }

    @Override
    public int countProblemTestCases(int pid) {
        return testCasesMapper.countByPid(pid);
    }

    @Override
    public List<TestCaseEntity> listProblemTestCases(int pid){
        //通过pid来查询所有的TestCase
        return testCasesMapper.listTestCasesByPid(pid);
    }

    @Override
    public boolean updateTestCaseByTidPid(int tid, int pid, String stdin, String stdout, int strength) {
        //通过TestCase的ID来修改TestCase
        TestCaseEntity testCaseEntity = new TestCaseEntity();
        testCaseEntity.setStdin(stdin);
        testCaseEntity.setStdout(stdout);
        testCaseEntity.setStrength(strength);
        return testCasesMapper.updateByTidPid(tid, pid, testCaseEntity) == 1;
    }

    @Override
    public boolean deleteTestCase(int tid) {
        //通过TestCase的ID删除TestCase
        return testCasesMapper.deleteByTid(tid) == 1;
    }
}
