package service;

import org.inlighting.oj.web.WebApplication;
import org.inlighting.oj.web.dao.GroupUserInfoDao;
import org.inlighting.oj.web.entity.GroupUserInfoEntity;
import org.inlighting.oj.web.service.GroupUserInfoService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author = ygj
 **/
@SpringBootTest(classes = WebApplication.class)
@RunWith(SpringRunner.class)
public class GroupUserInfoServiceTest {

    private GroupUserInfoService groupUserInfoService;

    private int testGid =2;
    private int testUid = 2;

    @Autowired
    public void setGroupUserInfoService(GroupUserInfoService groupUserInfoService) {
        this.groupUserInfoService = groupUserInfoService;
    }


    @Before
    public void addTest(){
        boolean result = groupUserInfoService.add(testGid,testUid,System.currentTimeMillis());
        Assert.assertEquals(true,result);
    }


    @Test
    public void getTest(){
        GroupUserInfoEntity groupUserInfoEntity = groupUserInfoService.getByGidAndUid(testGid,testUid);
        Assert.assertEquals(true,groupUserInfoEntity!=null);
    }

    @After
    public void deleteTest(){
        boolean result = groupUserInfoService.deleteByGidAndUid(testGid,testUid);
        Assert.assertEquals(true,result);
    }
}
