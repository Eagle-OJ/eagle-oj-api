package com.eagleoj.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.web.DefaultConfig;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.index.SubmitCodeFormat;
import com.eagleoj.web.controller.format.user.UserSubmitCodeFormat;
import com.eagleoj.web.entity.TestCaseEntity;
import com.eagleoj.web.judger.JudgeResult;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.JudgeService;
import com.eagleoj.web.service.async.AsyncJudgeService;
import io.swagger.annotations.ApiOperation;
import com.eagleoj.web.entity.ResponseEntity;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
    private AsyncJudgeService asyncJudgeService;

    @Autowired
    private JudgeService judgeService;

    @ApiOperation("测试提交判卷，优先级为0")
    @PostMapping
    public ResponseEntity submitCode(@RequestBody @Valid SubmitCodeFormat format) {
        int length = format.getTestCases().size();
        if (length == 0) {
            throw new WebErrorException("没有测试用例");
        }
        List<TestCaseEntity> testCases = new ArrayList<>(length);
        for(int i=0; i<length; i++) {
            JSONObject obj = format.getTestCases().getJSONObject(i);
            String stdin = obj.getString("stdin");
            String stdout = obj.getString("stdout");
            if (stdout.length() == 0) {
                throw new WebErrorException("输出字符串不得为空");
            }
            TestCaseEntity testCaseEntity = new TestCaseEntity(0, stdin, stdout, 0, 0L);
            testCases.add(testCaseEntity);
        }
        String id = asyncJudgeService.addTestJudge(format.getSourceCode(), format.getLang(), DefaultConfig.PROGRAM_USED_TIME,
                DefaultConfig.PROGRAM_USED_MEMORY, testCases);
        return new ResponseEntity(null, id);
    }

    @ApiOperation("提交普通题目判卷-优先级1")
    @RequiresAuthentication
    @PostMapping("/user")
    public ResponseEntity submitCode(@RequestBody @Valid UserSubmitCodeFormat format) {
        int pid = format.getProblemId();
        int cid = format.getContestId();
        int gid = format.getGroupId();
        String sourceCode = format.getSourceCode();
        LanguageEnum lang = format.getLang();
        int owner = SessionHelper.get().getUid();
        String id;
        if (gid != 0 && cid != 0) {
            id = asyncJudgeService.addGroupJudge(sourceCode, lang, owner, pid, cid, gid);
        } else if (cid != 0) {
            id = asyncJudgeService.addContestJudge(sourceCode, lang, owner, pid, cid);
        } else {
            id = asyncJudgeService.addProblemJudge(sourceCode, lang, owner, pid);
        }
        return new ResponseEntity(null, id);
    }

    @ApiOperation("根据id获取判卷任务状况")
    @GetMapping("/{id}")
    public ResponseEntity getStatus(@PathVariable("id") String id) {
        JudgeResult result = judgeService.getJudgeResult(id);
        Map<String, Object> map = new HashMap<>(3);
        map.put("response", result.getResponse());
        map.put("id", result.getId());
        map.put("status", result.getStatus().getMessage());
        return new ResponseEntity(map);
    }

    @ApiOperation("根据id获取判卷任务状况-DEBUG模式")
    @GetMapping("/debug/{id}")
    public ResponseEntity getDebugStatus(@PathVariable("id") String id) {
        JudgeResult result = judgeService.getJudgeResult(id);
        if (result == null) {
            throw new WebErrorException("不存在此任务");
        }
        return new ResponseEntity(result);
    }
}
