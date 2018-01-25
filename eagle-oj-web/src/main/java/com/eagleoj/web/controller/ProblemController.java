package com.eagleoj.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eagleoj.web.DefaultConfig;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.admin.ProblemAuditingFormat;
import com.eagleoj.web.controller.format.user.AddProblemFormat;
import com.eagleoj.web.controller.format.user.AddProblemModeratorFormat;
import com.eagleoj.web.controller.format.user.AddProblemTestCaseFormat;
import com.eagleoj.web.postman.MessageQueue;
import com.eagleoj.web.postman.task.SendProblemAcceptedMessageTask;
import com.eagleoj.web.postman.task.SendProblemRefusedMessageTask;
import com.eagleoj.web.service.ProblemModeratorService;
import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import com.eagleoj.web.DefaultConfig;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.admin.ProblemAuditingFormat;
import com.eagleoj.web.controller.format.user.AddProblemFormat;
import com.eagleoj.web.controller.format.user.AddProblemModeratorFormat;
import com.eagleoj.web.controller.format.user.AddProblemTestCaseFormat;
import com.eagleoj.web.controller.format.user.UpdateProblemSettingFormat;
import com.eagleoj.web.entity.*;
import com.eagleoj.web.postman.MessageQueue;
import com.eagleoj.web.postman.task.SendProblemAcceptedMessageTask;
import com.eagleoj.web.postman.task.SendProblemRefusedMessageTask;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.*;
import com.eagleoj.web.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private TagsService tagsService;

    @Autowired
    private TagProblemService tagProblemService;

    @Autowired
    private MessageQueue messageQueue;

    @ApiOperation("获取指定题目的信息")
    @GetMapping("/{pid}")
    public ResponseEntity get(@PathVariable int pid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
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
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
        return new ResponseEntity(problemService.getProblemTags(pid));
    }

    @ApiOperation("创建题目")
    @RequiresAuthentication
    @PostMapping
    public ResponseEntity addProblem(@RequestBody @Valid AddProblemFormat format) {
        int owner = SessionHelper.get().getUid();

        checkProblemFormat(format);

        List<Integer> tags = new ArrayList<>(format.getTags().size());
        for(int i=0; i<format.getTags().size(); i++) {
            int tid = format.getTags().getInteger(i);
            if (tagsService.addUsedTimes(tid)) {
                tags.add(tid);
            }
        }
        if (tags.size() == 0) {
            throw new WebErrorException("标签非法");
        }

        int pid = problemService.addProblem(owner, format.getTitle(), format.getDescription(), format.getInputFormat(),
                format.getOutputFormat(), format.getDifficult(), format.getSamples(), DefaultConfig.TIME, DefaultConfig.MEMORY);

        if (pid == 0) {
            throw new WebErrorException("添加题目失败");
        }

        // 添加题目和标签的关联
        for(Integer tid: tags) {
            tagProblemService.addTagProblem(tid, pid);
        }

        return new ResponseEntity("题目添加成功", pid);
    }

    @ApiOperation("更新题目")
    @RequiresAuthentication
    @PutMapping("/{pid}")
    public ResponseEntity updateProblem(@PathVariable("pid") int pid,
                                                   @RequestBody @Valid AddProblemFormat format) {
        // 检验数据
        checkProblemFormat(format);

        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
        havePermission(problemEntity);

        // tags 过滤
        List<Integer> originTags= tagProblemService.getProblemTags(pid)
                .stream()
                .map(TagProblemEntity::getTid).collect(Collectors.toList());
        JSONArray newTags = format.getTags();
        List<Integer> finalTags = new ArrayList<>(newTags.size());

        for(int i=0; i<newTags.size(); i++) {
            int tid = newTags.getInteger(i);
            if (originTags.contains(tid)) {
                finalTags.add(tid);
            } else {
                if (tagsService.addUsedTimes(tid)) {
                    finalTags.add(tid);
                }
            }
        }

        if (finalTags.size() == 0) {
            throw new WebErrorException("非法标签");
        }

        // 更新数据
        if (!problemService.updateProblemDescription(pid, format.getTitle(), format.getDescription(), format.getInputFormat(),
                format.getOutputFormat(), format.getSamples(), format.getDifficult())) {
            throw new WebErrorException("题目更新失败");
        }

        // 删除旧标签
        tagProblemService.deleteTagProblems(pid);
        for(Integer tid: finalTags) {
            tagProblemService.addTagProblem(tid, pid);
        }

        return new ResponseEntity("题目更新成功");
    }

    @ApiOperation("获取一道题目的测试用例")
    @RequiresAuthentication
    @GetMapping("/{pid}/test_cases")
    public ResponseEntity getProblemTestCase(@PathVariable int pid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
        havePermission(problemEntity);

        List<TestCaseEntity> testCaseEntities = testCasesService.getAllTestCasesByPid(problemEntity.getPid());
        return new ResponseEntity(testCaseEntities);
    }

    @ApiOperation("添加一道题目的一个测试用例")
    @RequiresAuthentication
    @PostMapping("/{pid}/test_cases")
    public ResponseEntity addProblemTestCase(
            @PathVariable("pid") int pid,
            @RequestBody @Valid AddProblemTestCaseFormat format) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
        havePermission(problemEntity);

        // 添加test_case
        int tid = testCasesService.addTestCase(pid, format.getStdin(), format.getStdout(), format.getStrength());

        if (tid == 0) {
            throw new WebErrorException("添加失败");
        }

        return new ResponseEntity("添加成功", tid);
    }

    @ApiOperation("删除指定的test_case")
    @RequiresAuthentication
    @DeleteMapping("/{pid}/test_case/{tid}")
    public ResponseEntity deleteProblemTestCase(@PathVariable("pid") int pid,
                                                @PathVariable("tid") int tid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
        havePermission(problemEntity);

        // 删除test_case
        if (! testCasesService.deleteTestCaseByTid(tid)) {
            throw new WebErrorException("删除失败");
        }

        return new ResponseEntity("删除成功");
    }

    @ApiOperation("更新指定的test_case")
    @RequiresAuthentication
    @PutMapping("/{pid}/test_case/{tid}")
    public ResponseEntity updateProblemTestCase(@PathVariable("pid") int pid,
                                                @PathVariable("tid") int tid,
                                                @RequestBody @Valid AddProblemTestCaseFormat format) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
        havePermission(problemEntity);

        if (! testCasesService.updateTestCaseByTidPid(tid, pid, format.getStdin(), format.getStdout(),
                format.getStrength())) {
            throw new WebErrorException("更新失败");
        }
        return new ResponseEntity("更新成功");
    }

    @ApiOperation("获取题目的moderator")
    @GetMapping("/{pid}/moderators")
    public ResponseEntity getProblemModerators(@PathVariable("pid") int pid) {
        List<Map<String, Object>> list = problemModeratorService.getModerators(pid);
        return new ResponseEntity(list);
    }

    @ApiOperation("添加指定的moderator")
    @RequiresAuthentication
    @PostMapping("/{pid}/moderator")
    public ResponseEntity addProblemModerator(@PathVariable("pid") int pid,
                                              @RequestBody @Valid AddProblemModeratorFormat format) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);

        if (SessionHelper.get().getUid() != problemEntity.getOwner()) {
            throw new WebErrorException("非法操作");
        }

        UserEntity userEntity = userService.getUserByEmail(format.getEmail());
        WebUtil.assertNotNull(userEntity, "此用户不存在");

        if (problemModeratorService.isExist(pid, userEntity.getUid())) {
            throw new WebErrorException("已存在此用户");
        }

        if (! problemModeratorService.add(pid, userEntity.getUid())) {
            throw new WebErrorException("添加用户失败");
        }


        return new ResponseEntity("添加成功");
    }

    @ApiOperation("删除指定的moderator")
    @RequiresAuthentication
    @DeleteMapping("/{pid}/moderator/{uid}")
    public ResponseEntity deleteProblemModerator(@PathVariable("pid") int pid,
                                                 @PathVariable("uid") int uid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);

        haveProblem(problemEntity);

        if (SessionHelper.get().getUid() != problemEntity.getOwner()) {
            throw new WebErrorException("非法操作");
        }

        if (! problemModeratorService.delete(pid, uid)) {
            throw new WebErrorException("删除用户失败");
        }

        return new ResponseEntity("删除用户成功");
    }

    @ApiOperation("更新题目设置")
    @RequiresAuthentication
    @PutMapping("/{pid}/setting")
    public ResponseEntity updateProblemSetting(@PathVariable("pid") int pid,
                                               @RequestBody @Valid UpdateProblemSettingFormat format) {
        // check data
        if (format.getLang().size() == 0) {
            throw new WebErrorException("至少支持一种编程语言");
        }
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
        havePermission(problemEntity);

        int status = 0;
        if (format.getShared()) {
            if (problemEntity.getStatus() == 0) {
                status = 1;
            } else {
                status = problemEntity.getStatus();
            }
        }

        if (! problemService.updateProblemSetting(pid, format.getLang(), format.getTime(), format.getMemory(), status)) {
            throw new WebErrorException("更新设置失败");
        }
        return new ResponseEntity("更新成功");
    }

    @ApiOperation("题目是否审核通过")
    @RequiresAuthentication
    @PostMapping("/{pid}/auditing")
    public ResponseEntity problemAuditing(@PathVariable int pid,
                                          @RequestBody @Valid ProblemAuditingFormat format) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
        boolean status;
        if (format.getAccepted()) {
            status = problemService.acceptProblem(pid);
            if (status) {
                SendProblemAcceptedMessageTask task = new SendProblemAcceptedMessageTask();
                task.setTitle(problemEntity.getTitle());
                task.setUid(problemEntity.getOwner());
                task.setPid(problemEntity.getPid());
                task.setType(5);
                messageQueue.addTask(task);
            }
        } else {
            status = problemService.refuseProblem(pid);
            if (status) {
                SendProblemRefusedMessageTask task = new SendProblemRefusedMessageTask();
                task.setTitle(problemEntity.getTitle());
                task.setUid(problemEntity.getOwner());
                task.setPid(problemEntity.getPid());
                task.setType(6);
                messageQueue.addTask(task);
            }
        }
        if (! status) {
            throw  new WebErrorException("审核失败");
        }
        return new ResponseEntity("审核成功");
    }

    private void havePermission(ProblemEntity problemEntity) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();

        if (uid == problemEntity.getOwner())
            return;

        if (role == DefaultConfig.ADMIN_ROLE)
            return;

        if (problemModeratorService.isExist(problemEntity.getPid(), uid))
            return;

        throw new WebErrorException("非法操作");
    }

    private void checkProblemFormat(AddProblemFormat format) {

        // valid sample
        if (format.getSamples().size()==0)
            throw new WebErrorException("样本不得为空");

        for (Object obj: format.getSamples()) {
            boolean input = !((JSONObject) obj).containsKey("input");
            boolean output = !((JSONObject) obj).containsKey("output");
            if (input || output)
                throw new WebErrorException("样本格式不符");
        }

        // valid tags
        if (format.getTags().size()==0) {
            throw new WebErrorException("标签不得为空");
        }
    }

    private void haveProblem(ProblemEntity entity) {
        WebUtil.assertNotNull(entity, "题目不存在");
    }
}
