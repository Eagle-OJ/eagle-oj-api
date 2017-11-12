package service;

import org.inlighting.oj.web.WebApplication;
import org.inlighting.oj.web.service.GroupService;
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
public class GroupServiceTest {

    private GroupService groupService;

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Test
    public void addTest(){
        int insertNum = groupService.createGroup(1,2,"testcover","123456",System.currentTimeMillis());
        Assert.assertEquals(true,insertNum >0);
    }
}
