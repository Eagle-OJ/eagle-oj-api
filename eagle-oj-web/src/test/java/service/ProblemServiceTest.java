package service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.inlighting.oj.web.WebApplication;
import org.inlighting.oj.web.service.ProblemService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@SpringBootTest(classes = WebApplication.class)
@RunWith(SpringRunner.class)
public class ProblemServiceTest {

    @Autowired
    private ProblemService problemService;

    @Test
    public void addProblem() {
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        JSONArray codeLanguage =new JSONArray(list);

        Map<String, Object> sampleMap = new HashMap<>();
        sampleMap.put("input", "1,2,3");
        sampleMap.put("output", "3,4,6");
        JSONObject jsonObject1 = new JSONObject(sampleMap);
        JSONObject jsonObject2 = new JSONObject(sampleMap);
        JSONArray sample = new JSONArray();
        sample.add(jsonObject1);
        sample.add(jsonObject2);

        JSONArray moderator = codeLanguage;

        List<Object> list1 = new ArrayList<>();
        list1.add("链表");
        list1.add("data structure");
        JSONArray tag = new JSONArray(list1);

        boolean result = problemService.addProblem(1, codeLanguage,  "A+B Test Problem", "这是一道很简单的测试题，做A+B",
                1, "输入规范", "输出规范", "条件约束", sample,
                moderator, tag, 1,System.currentTimeMillis());

        Assert.assertEquals(result, true);
    }
}
