package service;

import com.alibaba.fastjson.JSONArray;
import org.inlighting.oj.web.WebApplication;
import org.inlighting.oj.web.entity.ContestEntity;
import org.inlighting.oj.web.service.ContestService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author = ygj
 **/
@SpringBootTest(classes = WebApplication.class)
@RunWith(SpringRunner.class)
public class ConTestServiceTest {

    private int needKey = -1;

    private ContestService contestService = null;

    @Autowired
    public void setContestService(ContestService contestService) {
        this.contestService = contestService;
    }

    /*@Before
    public void addContestTest(){
        ArrayList<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        needKey= contestService.addContest("第二次考试",1,"first","very difficult",
                System.currentTimeMillis()+86400,System.currentTimeMillis()+86400*2,"123456",
                0,2,System.currentTimeMillis());
        Assert.assertEquals(true,needKey>0);
    }*/

    @Test
    public void getContestByCidTest(){
        ContestEntity contestEntity = contestService.getContestByCid(needKey);
        Assert.assertEquals(true,contestEntity!=null);
    }

    @Test
    public void getAllTest(){
        List<ContestEntity> list = contestService.getAll();
        Assert.assertEquals(true,!list.isEmpty());
    }
    @After
    public void deleteContestByCidTest(){
        boolean flag = contestService.deleteContestByCid(needKey);
        Assert.assertEquals(true,flag);
    }

}
