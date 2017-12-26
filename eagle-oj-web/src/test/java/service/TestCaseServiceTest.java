package service;

import org.inlighting.oj.web.WebApplication;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.inlighting.oj.web.service.TestCasesService;
import org.junit.*;
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
    TestCasesService testCaseService;

    private int currentTid = 0;

    private int pid = 998;


    @Before
    public void setUp() {
        currentTid = testCaseService.addTestCase(pid, "stdin", "stdout", 3);
        Assert.assertEquals(true, currentTid > 0);
    }

    @After
    public void after() {
        boolean result = testCaseService.deleteTestCaseByTid(currentTid);
        Assert.assertEquals(true, result);
    }

    @Test
    public void getTestCaseCountTest(){
        int num = testCaseService.getTestCaseCountByPid(pid);
        Assert.assertEquals(1, num);
    }

    @Test
    public void getAllTestCaseByPidTest(){
        List<TestCaseEntity> list = testCaseService.getAllTestCasesByPid(pid);
        Assert.assertEquals(true, list.size()>0);
    }

    /*@Test
    public void updateTestByTidTest(){
        String stdin = "2";
        String stdout = "4";
        int strength = 26;
        Assert.assertEquals(true, testCaseService.updateTestCaseByTid(currentTid, stdin, stdout, strength));
    }*/
}
