package service;

import org.inlighting.oj.web.WebApplication;
import org.inlighting.oj.web.entity.ContestUserInfoEntity;
import org.inlighting.oj.web.service.ContestUserInfoService;
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

    private ContestUserInfoService contestUserInfoService;
    @Autowired
    public void setContestUserInfoService(ContestUserInfoService contestUserInfoService) {
        this.contestUserInfoService = contestUserInfoService;
    }

    @Test
    public void addTest(){
        int cid = 1;
        int uid = 12;
        boolean flag = contestUserInfoService.add(cid,uid);
        Assert.assertEquals(true,flag);
    }

    @Test
    public void getTest(){
        int cid = 1;
        int uid = 14;
        ContestUserInfoEntity contestUserInfoEntity = contestUserInfoService.getByCidAndUid(cid,uid);
        Assert.assertEquals(true,contestUserInfoEntity!=null);
    }
}
