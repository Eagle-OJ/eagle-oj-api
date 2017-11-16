package org.inlighting.oj.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.inlighting.oj.web.controller.format.index.TestSubmitCodeFormat;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.inlighting.oj.web.judger.JudgerQueue;
import org.inlighting.oj.web.judger.JudgerTaskResultEntity;
import org.inlighting.oj.web.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/code", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CodeSubmitController {

    private Logger LOGGER = LogManager.getLogger(this.getClass());

    private JudgerQueue judgerQueue;

    @Autowired
    public void setJudgerQueue(JudgerQueue judgerQueue) {
        this.judgerQueue = judgerQueue;
    }

    @PostMapping("/submit")
    public ResponseEntity guestSubmitTest(@RequestBody @Valid TestSubmitCodeFormat format) {
        return new ResponseEntity("提交成功", submitTest(format, 0));
    }

    @PostMapping("/user/submit")
    public ResponseEntity userSubmitTest(@RequestBody @Valid TestSubmitCodeFormat format) {
        return new ResponseEntity("提交成功", submitTest(format, 1));
    }

    private String submitTest(TestSubmitCodeFormat format, int priority) {
        if (format.getTestCases().size() == 0) {
            throw new RuntimeException("没有测试用例");
        }
        // 组装测试用例
        List<TestCaseEntity> testCaseEntities = new ArrayList<>(3);
        for (int i=0; i<format.getTestCases().size(); i++) {
            JSONObject obj = format.getTestCases().getJSONObject(i);
            TestCaseEntity testCaseEntity = new TestCaseEntity(0, obj.getString("stdin"),
                    obj.getString("stdout"), 0, System.currentTimeMillis());
            testCaseEntities.add(testCaseEntity);
        }

        String uuid = judgerQueue.addTask(priority, 0, 0, 0, 0,0,
                format.getCodeLanguage(), format.getCodeSource(), true, testCaseEntities);
        return uuid;
    }

    @GetMapping("/status/{uuid}")
    public ResponseEntity getStatus(@PathVariable String uuid) {
        JudgerTaskResultEntity resultEntity = judgerQueue.getResult(uuid);
        if (resultEntity == null) {
            throw new RuntimeException("不存在任务");
        }

        return new ResponseEntity("获取成功", resultEntity);
    }

    /*private JudgerRequestBean requestBeanLoader(SubmitCodeFormat format) {
        int pid = format.getPid();

        String[] stdin;
        String[] expectResult;
        if (format.isTestMode()) {
            stdin = new String[format.getTestCase().size()];
            expectResult = new String[format.getTestCase().size()];
            for (int i=0; i<format.getTestCase().size(); i++) {
                JSONObject jsonObject = format.getTestCase().getJSONObject(i);
                stdin[i] = jsonObject.getString("stdin");
                expectResult[i] = jsonObject.getString("stdout");
            }
        } else {
            List<TestCaseEntity> testCaseEntities = testCaseService.getAllTestCasesByPid(pid);
            stdin = new String[testCaseEntities.size()];
            expectResult = new String[testCaseEntities.size()];
            for (int i=0; i<testCaseEntities.size(); i++) {
                stdin[i] = testCaseEntities.get(i).getStdin();
                expectResult[i] = testCaseEntities.get(i).getStdout();
            }
        }


        JudgerRequestBean stdRequestBean = new JudgerRequestBean();
        stdRequestBean.setTestCaseNumber(stdin.length);
        stdRequestBean.setLanguage(format.getCodeLanguage());
        stdRequestBean.setSourceCode(format.getSourceCode());
        stdRequestBean.setStdin(stdin);
        stdRequestBean.setExpectResult(expectResult);
        stdRequestBean.setTimeLimit(3);
        stdRequestBean.setMemoryLimit(128);
        return stdRequestBean;
    }

    private JSONArray judgeResultLoader(JudgeResponseBean stdResponseBean) {
        JSONArray result = new JSONArray();
        for (int i=0; i<stdResponseBean.getTestCaseNumber(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("stderr", stdResponseBean.getStderr()[i]);
            jsonObject.put("status", stdResponseBean.getProblemStatusEnums()[i]);
            result.add(jsonObject);
        }
        return result;
    }*/



}
