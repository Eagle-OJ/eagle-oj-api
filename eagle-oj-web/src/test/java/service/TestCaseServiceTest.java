package service;

import org.inlighting.oj.web.WebApplication;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.inlighting.oj.web.service.TestCaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author = ygj
 **/
@SpringBootTest(classes = WebApplication.class)
@RunWith(SpringRunner.class)
public class TestCaseServiceTest {
    @Autowired
    TestCaseService testCaseService = null;


    @Test
    public void addTestCaseTest(){
        int pid = 1;
        String stdin = "1";
        String stdout = "2";
        int strength = 26;
        testCaseService.addTestCase(pid,stdin,stdout,strength);
    }

    @Test
    public void getTestCaseCountTest(){
        int num = testCaseService.getTestCaseCountByPid(1);
        System.out.println(num);
    }

    @Test
    public void getAllTestCaseByPidTest(){
        List<TestCaseEntity> list = testCaseService.getAllTestCasesByPid(1);
        System.out.println(list);
    }

    @Test
    public void updateTestByTidTest(){
        int tid = 1;
        String stdin = "2";
        String stdout = "4";
        int strength = 26;
        testCaseService.updateTestCaseByTid(tid,stdin,stdout,strength);
    }


    @Test
    public void deleteTestCaseByTidTest(){
        int tid = 2;
        testCaseService.deleteTestCaseByTid(tid);
    }
}
