package org.inlighting.oj.judge;

import com.alibaba.fastjson.JSON;
import org.inlighting.oj.judge.entity.RequestEntity;
import org.inlighting.oj.judge.entity.ResponseEntity;
import org.inlighting.oj.judge.entity.TestCaseRequestEntity;
import org.inlighting.oj.judge.judger.Judger;
import org.inlighting.oj.judge.judger.judge0.Judge0;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Smith
 **/
public class Main {
    public static void main(String[] args) {
        List<TestCaseRequestEntity> list = new ArrayList<>(2);
        TestCaseRequestEntity testCaseRequestEntity1 = new TestCaseRequestEntity(null, "hello");
        TestCaseRequestEntity testCaseRequestEntity2 = new TestCaseRequestEntity(null, "world");
        list.add(testCaseRequestEntity1);
        list.add(testCaseRequestEntity2);
        RequestEntity requestEntity = new RequestEntity(LanguageEnum.PYTHON36, "print(\"hello\")", 3,
                128, list);
        Judger judger = new Judger("http://www.funnytu.com:3000", requestEntity, new Judge0());
        ResponseEntity responseEntity = judger.judge();
        System.out.println(JSON.toJSONString(responseEntity));
    }
}
