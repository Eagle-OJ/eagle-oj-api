package service;

import org.inlighting.oj.web.WebApplication;
import org.inlighting.oj.web.entity.ContestUserEntity;
import org.inlighting.oj.web.service.ContestUserService;
import org.junit.Assert;
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
public class ContestUserInfoServiceTest {

    private ContestUserService contestUserInfoService;
    @Autowired
    public void setContestUserInfoService(ContestUserService contestUserInfoService) {
        this.contestUserInfoService = contestUserInfoService;
    }

    @Test
    public void addTest(){
        int cid = 1;
        int uid = 12;
        boolean flag = contestUserInfoService.add(cid,uid, System.currentTimeMillis());
        Assert.assertEquals(true,flag);
    }

    @Test
    public void getTest(){
        int cid = 4;
        int uid = 14;
        ContestUserEntity contestUserInfoEntity = contestUserInfoService.get(cid,uid);
        Assert.assertEquals(true,contestUserInfoEntity!=null);
    }
}
