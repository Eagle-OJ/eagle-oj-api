package service;

import com.eagleoj.web.WebApplication;
import com.eagleoj.web.entity.GroupUserEntity;
import com.eagleoj.web.service.GroupUserService;
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

    private GroupUserService groupUserInfoService;

    private int testGid =2;
    private int testUid = 2;

    @Autowired
    public void setGroupUserInfoService(GroupUserService groupUserInfoService) {
        this.groupUserInfoService = groupUserInfoService;
    }


    @Before
    public void addTest(){
        boolean result = groupUserInfoService.add(testGid,testUid);
        Assert.assertEquals(true,result);
    }


    @Test
    public void getTest(){
        GroupUserEntity groupUserInfoEntity = groupUserInfoService.getMember(testGid,testUid);
        Assert.assertEquals(true,groupUserInfoEntity!=null);
    }

    @After
    public void deleteTest(){
        boolean result = groupUserInfoService.deleteMember(testGid,testUid);
        Assert.assertEquals(true,result);
    }
}
