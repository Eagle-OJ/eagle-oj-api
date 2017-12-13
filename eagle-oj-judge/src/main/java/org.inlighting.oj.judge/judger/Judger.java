package org.inlighting.oj.judge.judger;

import com.alibaba.fastjson.JSON;
import org.inlighting.oj.judge.ResultEnum;
import org.inlighting.oj.judge.entity.RequestEntity;
import org.inlighting.oj.judge.entity.ResponseEntity;
import org.inlighting.oj.judge.entity.TestCaseResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Smith
 **/
public class Judger extends AbstractJudger {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private RequestEntity requestEntity;

    private String url;

    public Judger(String url, RequestEntity requestEntity, JudgerApi judgerApi) {
        super(judgerApi);
        this.url = url;
        this.requestEntity = requestEntity;
    }

    @Override
    public ResponseEntity judge() {
        try {
            return judgerApi.judge(url, requestEntity);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return getSEResponseEntity(requestEntity);
        }
    }

    private ResponseEntity getSEResponseEntity(RequestEntity requestEntity) {
        List<TestCaseResponseEntity> testCases = new ArrayList<>(requestEntity.getTestCases().size());
        for (int i=0; i<requestEntity.getTestCases().size(); i++) {
            TestCaseResponseEntity responseEntity = new TestCaseResponseEntity(ResultEnum.SE, ResultEnum.SE.getName());
            testCases.add(responseEntity);
        }
        return new ResponseEntity(0, 0, ResultEnum.SE, testCases);
    }
}
