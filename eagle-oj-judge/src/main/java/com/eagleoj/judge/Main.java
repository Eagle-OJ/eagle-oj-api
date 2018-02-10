package com.eagleoj.judge;

import com.alibaba.fastjson.JSON;
import com.eagleoj.judge.judger.Judger;
import com.eagleoj.judge.entity.RequestEntity;
import com.eagleoj.judge.entity.ResponseEntity;
import com.eagleoj.judge.entity.TestCaseRequestEntity;
import com.eagleoj.judge.judger.eagle.Eagle;
import com.eagleoj.judge.judger.judge0.Judge0;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Smith
 **/
public class Main {
    public static void main(String[] args) {
        List<TestCaseRequestEntity> list = new ArrayList<>(2);
        TestCaseRequestEntity testCaseRequestEntity1 = new TestCaseRequestEntity(null, "hll\n");
        TestCaseRequestEntity testCaseRequestEntity2 = new TestCaseRequestEntity(null, "hello");
        list.add(testCaseRequestEntity1);
        list.add(testCaseRequestEntity2);
        /*RequestEntity requestEntity = new RequestEntity(LanguageEnum.PYTHON35, "print(\"hello\")", 3,public class Main {public static void main(String[] args) {System.out.println(\"hll\");}}
                128, list);*/
        RequestEntity requestEntity = new RequestEntity(LanguageEnum.JAVA8, "public class Main {public static void main(String[] args) {System.out.println(\"hll\");}}", 3,
                128, list);
        Judger judger = eagle(requestEntity);
        ResponseEntity responseEntity = judger.judge();
    }

    private static Judger judge0(RequestEntity requestEntity) {
        return new Judger("http://www.funnytu.com:3000", requestEntity, new Judge0());
    }

    private static Judger eagle(RequestEntity requestEntity) {
        return new Judger("http://101.132.164.120:5000", requestEntity, new Eagle());
    }
}
