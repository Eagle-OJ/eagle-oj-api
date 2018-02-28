package com.eagleoj.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eagleoj.web.DefaultConfig;
import com.eagleoj.web.controller.exception.UnauthorizedException;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.admin.ProblemAuditingFormat;
import com.eagleoj.web.controller.format.user.*;
import com.eagleoj.web.data.status.ProblemStatus;
import com.eagleoj.web.data.status.RoleStatus;
import com.eagleoj.web.service.ProblemModeratorService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import com.eagleoj.web.entity.*;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.*;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/problem", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProblemModeratorService problemModeratorService;

    @Autowired
    private TestCasesService testCasesService;

    @ApiOperation("随机获取一个题目ID")
    @GetMapping("/random")
    public ResponseEntity getRandomProblem() {
        return new ResponseEntity(problemService.getRandomPid());
    }

    @ApiOperation("获取指定题目的信息")
    @GetMapping("/{pid}")
    public ResponseEntity get(@PathVariable int pid) {
        ProblemEntity problemEntity = problemService.getProblem(pid);
        UserEntity userEntity = userService.getUserByUid(problemEntity.getOwner());
        Map<String, Object> dataMap = new HashMap<>(2);
        dataMap.put("problem", problemEntity);
        Map<String, Object> userMap = new HashMap<>(2);
        userMap.put("nickname", userEntity.getNickname());
        userMap.put("avatar", userEntity.getAvatar());
        dataMap.put("author", userMap);
        return new ResponseEntity(dataMap);
    }

    @ApiOperation("获取该题的所有标签")
    @GetMapping("/{pid}/tags")
    public ResponseEntity getProblemTags(@PathVariable("pid") int pid) {
        return new ResponseEntity(problemService.listProblemTags(pid));
    }

    @ApiOperation("创建题目")
    @RequiresAuthentication
    @PostMapping
    public ResponseEntity addProblem(@RequestBody @Valid AddProblemFormat format) {
        int owner = SessionHelper.get().getUid();
        checkProblemFormat(format);

        int pid = problemService.save(format.getTags(), owner, format.getTitle(), format.getDescription(), format.getInputFormat(),
                format.getOutputFormat(), format.getDifficult(), format.getSamples(),
                DefaultConfig.PROGRAM_USED_TIME, DefaultConfig.PROGRAM_USED_MEMORY, ProblemStatus.EDITING);

        return new ResponseEntity("题目添加成功", pid);
    }

    @ApiOperation("更新题目")
    @RequiresAuthentication
    @PutMapping("/{pid}")
    public ResponseEntity updateProblem(@PathVariable("pid") int pid,
                                                   @RequestBody @Valid UpdateProblemFormat format) {
        ProblemEntity problemEntity = problemService.getProblem(pid);
        accessToEditProblem(problemEntity);

        ProblemEntity newProblemEntity = new ProblemEntity();

        if (format.getTitle() != null) {
            newProblemEntity.setTitle(format.getTitle());
        }
        if (format.getDescription() != null) {
            newProblemEntity.setDescription(format.getDescription());
        }
        if (format.getInputFormat() != null) {
            newProblemEntity.setInputFormat(format.getInputFormat());
        }
        if (format.getOutputFormat() != null) {
            newProblemEntity.setOutputFormat(format.getOutputFormat());
        }
        if (format.getDifficult() != null) {
            newProblemEntity.setDifficult(format.getDifficult());
        }
        if (format.getSamples() != null) {
            checkProblemSamples(format.getSamples());
            newProblemEntity.setSamples(format.getSamples());
        }
        JSONArray tags = null;
        if (format.getTags() != null) {
            checkProblemTags(format.getTags());
            tags = format.getTags();
        }
        if (format.getLang() != null) {
            checkProblemLang(format.getLang());
            newProblemEntity.setLang(format.getLang());
        }
        if (format.getTime() != null) {
            newProblemEntity.setTime(format.getTime());
        }
        if (format.getMemory() != null) {
            newProblemEntity.setMemory(format.getMemory());
        }
        Integer status = problemEntity.getStatus();
        if (format.getShared() != null) {
            if (format.getShared()) {
                if (status == ProblemStatus.EDITING.getNumber()) {
                    status = ProblemStatus.AUDITING.getNumber();
                }
            } else {
                status = ProblemStatus.EDITING.getNumber();
            }
            newProblemEntity.setStatus(status);
        }

        problemService.updateProblem(pid, tags, newProblemEntity);
        return new ResponseEntity("题目更新成功");
    }

    @ApiOperation("删除指定题目")
    @RequiresAuthentication
    @DeleteMapping("/{pid}")
    public ResponseEntity deleteProblem(@PathVariable int pid) {
        ProblemEntity problemEntity = problemService.getProblem(pid);
        accessToEditProblem(problemEntity);
        problemService.deleteProblem(pid);
        return new ResponseEntity("题目删除成功");
    }

    @ApiOperation("获取一道题目的测试用例")
    @RequiresAuthentication
    @GetMapping("/{pid}/test_cases")
    public ResponseEntity listProblemTestCase(@PathVariable int pid) {
        ProblemEntity problemEntity = problemService.getProblem(pid);

        accessToView(problemEntity);

        List<TestCaseEntity> testCaseEntities = testCasesService.listProblemTestCases(problemEntity.getPid());
        return new ResponseEntity(testCaseEntities);
    }

    @ApiOperation("添加一道题目的一个测试用例")
    @RequiresAuthentication
    @PostMapping("/{pid}/test_case")
    public ResponseEntity addProblemTestCase(
            @PathVariable("pid") int pid,
            @RequestBody @Valid AddProblemTestCaseFormat format) {
        ProblemEntity problemEntity = problemService.getProblem(pid);
        accessToEditProblem(problemEntity);

        // 添加test_case
        int tid = testCasesService.save(pid, format.getStdin(), format.getStdout(), format.getStrength());

        return new ResponseEntity("添加成功", tid);
    }

    @ApiOperation("删除指定的test_case")
    @RequiresAuthentication
    @DeleteMapping("/{pid}/test_case/{tid}")
    public ResponseEntity deleteProblemTestCase(@PathVariable("pid") int pid,
                                                @PathVariable("tid") int tid) {
        ProblemEntity problemEntity = problemService.getProblem(pid);
        accessToEditProblem(problemEntity);

        // 删除test_case
        testCasesService.deleteTestCase(tid, pid);

        return new ResponseEntity("删除成功");
    }

    @ApiOperation("更新指定的test_case")
    @RequiresAuthentication
    @PutMapping("/{pid}/test_case/{tid}")
    public ResponseEntity updateProblemTestCase(@PathVariable("pid") int pid,
                                                @PathVariable("tid") int tid,
                                                @RequestBody @Valid AddProblemTestCaseFormat format) {
        ProblemEntity problemEntity = problemService.getProblem(pid);
        accessToEditProblem(problemEntity);

        testCasesService.updateTestCaseByTidPid(tid, pid, format.getStdin(), format.getStdout(),
                format.getStrength());
        return new ResponseEntity("更新成功");
    }

    @ApiOperation("获取题目的moderator")
    @GetMapping("/{pid}/moderators")
    public ResponseEntity getProblemModerators(@PathVariable("pid") int pid) {
        List<Map<String, Object>> list = problemModeratorService.listProblemModerators(pid);
        return new ResponseEntity(list);
    }

    @ApiOperation("添加指定的moderator")
    @RequiresAuthentication
    @PostMapping("/{pid}/moderator")
    public ResponseEntity addProblemModerator(@PathVariable("pid") int pid,
                                              @RequestBody @Valid AddProblemModeratorFormat format) {
        ProblemEntity problemEntity = problemService.getProblem(pid);

        accessToEditModerator(problemEntity);

        problemModeratorService.addProblemModerator(pid, format.getEmail());
        return new ResponseEntity("添加成功");
    }

    @ApiOperation("删除指定的moderator")
    @RequiresAuthentication
    @DeleteMapping("/{pid}/moderator/{uid}")
    public ResponseEntity deleteProblemModerator(@PathVariable("pid") int pid,
                                                 @PathVariable("uid") int uid) {
        ProblemEntity problemEntity = problemService.getProblem(pid);
        accessToEditModerator(problemEntity);

        if (SessionHelper.get().getUid() != problemEntity.getOwner()) {
            throw new WebErrorException("非法操作");
        }

        problemModeratorService.deleteModerator(pid, uid);

        return new ResponseEntity("删除用户成功");
    }

    @ApiOperation("题目是否审核通过")
    @RequiresAuthentication
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @PostMapping("/{pid}/auditing")
    public ResponseEntity problemAuditing(@PathVariable int pid,
                                          @RequestBody @Valid ProblemAuditingFormat format) {
        ProblemEntity problemEntity = problemService.getProblem(pid);
        if (format.getAccepted()) {
            problemService.acceptProblem(problemEntity);
        } else {
            problemService.refuseProblem(problemEntity);

        }
        return new ResponseEntity("审核成功");
    }


    private void checkProblemFormat(AddProblemFormat format) {
        // valid sample
        checkProblemSamples(format.getSamples());

        // valid tags
        checkProblemTags(format.getTags());
    }

    private void checkProblemSamples(JSONArray samples) {
        if (samples.size()==0)
            throw new WebErrorException("样本不得为空");
        for (Object obj: samples) {
            boolean input = !((JSONObject) obj).containsKey("input");
            boolean output = !((JSONObject) obj).containsKey("output");
            if (input || output)
                throw new WebErrorException("样本格式不符");
        }
    }

    private void checkProblemTags(JSONArray tags) {
        if (tags.size()==0) {
            throw new WebErrorException("标签不得为空");
        }
    }

    private void checkProblemLang(JSONArray lang) {
        if (lang.size() == 0) {
            throw new WebErrorException("至少支持一种编程语言");
        }
    }

    // 题目一旦全局使用，则只有管理员才能修改
    private void accessToEditProblem(ProblemEntity problemEntity) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();

        if (problemEntity.getStatus() == ProblemStatus.SHARING.getNumber()) {
            if (role >= RoleStatus.ADMIN.getNumber()) {
                return;
            } else {
                throw new WebErrorException("题目已经全局分享，只有管理员能够进行修改");
            }
        }

        if (uid == problemEntity.getOwner()) {
            return;
        }

        if (role >= RoleStatus.ADMIN.getNumber()) {
            return;
        }

        if (problemModeratorService.isExistModeratorInProblem(problemEntity.getPid(), uid)) {
            return;
        }

        throw new UnauthorizedException("非法操作");
    }

    private void accessToEditModerator(ProblemEntity problemEntity) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();

        if (uid == problemEntity.getOwner()) {
            return;
        }

        if (role >= RoleStatus.ADMIN.getNumber()) {
            return;
        }
        throw new UnauthorizedException();
    }

    // 是否有权限查看
    private void accessToView(ProblemEntity problemEntity) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();

        if (uid == problemEntity.getOwner()) {
            return;
        }

        if (role >= RoleStatus.ADMIN.getNumber()) {
            return;
        }

        if (problemModeratorService.isExistModeratorInProblem(problemEntity.getPid(), uid)) {
            return;
        }

        throw new UnauthorizedException("非法操作");
    }
}
