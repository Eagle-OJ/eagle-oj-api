package com.eagleoj.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.eagleoj.web.DefaultConfig;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.index.SubmitCodeFormat;
import com.eagleoj.web.judger.JudgerManager;
import com.eagleoj.web.judger.JudgerResult;
import io.swagger.annotations.ApiOperation;
import com.eagleoj.judge.entity.TestCaseRequestEntity;
import com.eagleoj.web.DefaultConfig;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.index.SubmitCodeFormat;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.judger.JudgerManager;
import com.eagleoj.web.judger.JudgerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/code", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CodeController {

    @Autowired
    private JudgerManager judgerManager;

    @ApiOperation("测试提交判卷，优先级为0")
    @PostMapping
    public ResponseEntity submitCode(@RequestBody @Valid SubmitCodeFormat format) {
        int length = format.getTestCases().size();
        if (length == 0) {
            throw new WebErrorException("没有测试用例");
        }
        List<TestCaseRequestEntity> testCases = new ArrayList<>(length);
        for(int i=0; i<length; i++) {
            JSONObject obj = format.getTestCases().getJSONObject(i);
            TestCaseRequestEntity testCaseRequestEntity = new TestCaseRequestEntity(obj.getString("stdin"), obj.getString("stdout"));
            testCases.add(testCaseRequestEntity);
        }
        String id = judgerManager.addTask(true, 0, 0, 0,
                format.getLang(), format.getSourceCode(),
                DefaultConfig.PROGRAM_USED_TIME, DefaultConfig.PROGRAM_USED_MEMORY, testCases,
                null, null,
                null, null, null);
        return new ResponseEntity(null, id);
    }

    @ApiOperation("根据id获取判卷任务状况")
    @GetMapping("/{id}")
    public ResponseEntity getStatus(@PathVariable("id") String id) {
        JudgerResult result = judgerManager.getTask(id);
        if (result == null) {
            throw new WebErrorException("不存在此任务");
        }
        Map<String, Object> map = new HashMap<>(5);
        boolean testMode = result.getJudgerTask().isTestMode();
        map.put("response", result.getResponse());
        map.put("id", result.getId());
        map.put("status", result.getStatus().getMessage());
        map.put("test_mode", testMode);
        if (! testMode) {
            map.put("pid", result.getJudgerTask().getProblemId());
            map.put("title", result.getJudgerTask().getAddProblemEntity().getTitle());
        }
        return new ResponseEntity(map);
    }

    @ApiOperation("根据id获取判卷任务状况")
    @GetMapping("/debug/{id}")
    public ResponseEntity getDebugStatus(@PathVariable("id") String id) {
        JudgerResult result = judgerManager.getTask(id);
        if (result == null) {
            throw new WebErrorException("不存在此任务");
        }
        return new ResponseEntity(result);
    }
}
