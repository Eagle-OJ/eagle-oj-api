package org.inlighting.oj.web.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.DefaultConfig;
import org.inlighting.oj.web.controller.exception.UnauthorizedException;
import org.inlighting.oj.web.controller.format.user.AddProblemFormat;
import org.inlighting.oj.web.controller.format.user.AddProblemTestCaseFormat;
import org.inlighting.oj.web.entity.ProblemEntity;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.entity.TestCaseEntity;
import org.inlighting.oj.web.entity.UserEntity;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.ProblemService;
import org.inlighting.oj.web.service.TagsService;
import org.inlighting.oj.web.service.TestCasesService;
import org.inlighting.oj.web.service.UserService;
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
@RequestMapping(value = "/user/problem", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserProblemController {

    private ProblemService problemService;

    private TestCasesService testCasesService;

    private TagsService tagsService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Autowired
    public void setTestCaseService(TestCasesService testCaseService) {
        this.testCasesService = testCaseService;
    }

    @Autowired
    public void setTagsService(TagsService tagsService) {
        this.tagsService = tagsService;
    }

    @ApiOperation("添加题目")
    @PostMapping
    public ResponseEntity addProblem(@RequestBody @Valid AddProblemFormat format) {
        int owner = SessionHelper.get().getUid();

        checkProblemFormat(format);

        JSONArray tags = new JSONArray(format.getTags().size());
        for(int i=0; i<format.getTags().size(); i++) {
            String name = format.getTags().getString(i);
            if (tagsService.addUsedTimes(name)) {
                tags.add(name);
            }
        }
        if (tags.size() == 0) {
            throw new RuntimeException("标签非法");
        }

        int pid = problemService.addProblem(owner, format.getTitle(), format.getDescription(), format.getInputFormat(),
                format.getOutputFormat(), format.getDifficult(), format.getSamples(), tags,
                System.currentTimeMillis());

        if (pid == 0) {
            throw new RuntimeException("添加题目失败");
        }

        return new ResponseEntity("题目添加成功", pid);
    }

    @GetMapping("/{pid}")
    public ResponseEntity getProblemDescription(@PathVariable("pid") int pid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);

        if (problemEntity == null) {
            throw new RuntimeException("此题目不存在");
        }

        if (! checkProblemEditPermission(problemEntity)) {
            throw new RuntimeException("非法操作");
        }
        return new ResponseEntity(problemEntity);
    }

    @ApiOperation("更新题目")
    @PutMapping("/{pid}")
    public ResponseEntity updateProblemDescription(@PathVariable("pid") int pid,
                                        @RequestBody @Valid AddProblemFormat format) {
        // 检验数据
        checkProblemFormat(format);

        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        if (problemEntity == null) {
            throw new RuntimeException("题目不存在");
        }

        if (! checkProblemEditPermission(problemEntity)) {
            throw new RuntimeException("非法操作");
        }

        // tags 过滤
        JSONArray originTags= problemEntity.getTags();
        JSONArray newTags = format.getTags();
        JSONArray finalTags = new JSONArray(newTags.size());

        for(int i=0; i<newTags.size(); i++) {
            String name = newTags.getString(i);
            if (originTags.contains(name)) {
                finalTags.add(name);
            } else {
                if (tagsService.addUsedTimes(name)) {
                    finalTags.add(name);
                }
            }
        }

        if (finalTags.size() == 0) {
            throw new RuntimeException("非法标签");
        }

        // 更新数据
        if (!problemService.updateProblemDescription(pid, format.getTitle(), format.getDescription(), format.getInputFormat(),
                format.getOutputFormat(), format.getSamples(), format.getDifficult(), finalTags)) {
            throw new RuntimeException("题目更新失败");
        }

        return new ResponseEntity("题目更新成功");
    }


    @ApiOperation("获取一道题目的测试用例")
    @GetMapping("/{pid}/test_cases")
    public ResponseEntity getProblemTestCase(@PathVariable int pid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        if (problemEntity == null) {
            throw new RuntimeException("此题目不存在");
        }

        if (! checkProblemEditPermission(problemEntity)) {
            throw new RuntimeException("非法操作");
        }

        List<TestCaseEntity> testCaseEntities = testCasesService.getAllTestCasesByPid(problemEntity.getPid());
        return new ResponseEntity(testCaseEntities);
    }

    @ApiOperation("添加一道题目的一个测试用例")
    @PostMapping("/{pid}/test_cases")
    public ResponseEntity addProblemTestCase(
            @PathVariable("pid") int pid,
            @RequestBody @Valid AddProblemTestCaseFormat format) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        if (problemEntity == null) {
            throw new RuntimeException("此题目不存在");
        }

        if (! checkProblemEditPermission(problemEntity)) {
            throw new RuntimeException("非法操作");
        }

        // 添加test_case
        int tid = testCasesService.addTestCase(pid, format.getStdin(), format.getStdout(), format.getStrength());

        if (tid == 0) {
            throw new RuntimeException("添加失败");
        }

        return new ResponseEntity("添加成功", tid);
    }

    @ApiOperation("删除指定的test_case")
    @DeleteMapping("/{pid}/test_case/{tid}")
    public ResponseEntity deleteProblemTestCase(@PathVariable("pid") int pid,
                                                @PathVariable("tid") int tid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        if (problemEntity==null) {
            throw new RuntimeException("题目不存在");
        }

        if (!checkProblemEditPermission(problemEntity)) {
            throw new RuntimeException("非法操作");
        }

        // 删除test_case
        if (! testCasesService.deleteTestCaseByTid(tid)) {
            throw new RuntimeException("删除失败");
        }

        return new ResponseEntity("删除成功");
    }

    @ApiOperation("更新指定的test_case")
    @PutMapping("/{pid}/test_case/{tid}")
    public ResponseEntity updateProblemTestCase(@PathVariable("pid") int pid,
                                                @PathVariable("tid") int tid,
                                                @RequestBody @Valid AddProblemTestCaseFormat format) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        if (problemEntity==null) {
            throw new RuntimeException("题目不存在");
        }

        if(! checkProblemEditPermission(problemEntity)) {
            throw new RuntimeException("非法操作");
        }

        if (! testCasesService.updateTestCaseByTidPid(tid, pid, format.getStdin(), format.getStdout(),
                format.getStrength())) {
            throw new RuntimeException("更新失败");
        }
        return new ResponseEntity("更新成功");
    }

    @ApiOperation("获取该题目的problem的所有moderator")
    @GetMapping("/{pid}/moderators")
    public ResponseEntity getProblemModerators(@PathVariable("pid") int pid) {
        // todo
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);

        if (problemEntity == null) {
            throw new RuntimeException("题目不存在");
        }

        if (! checkProblemEditPermission(problemEntity)) {
            throw new RuntimeException("非法操作");
        }

        JSONArray moderators = problemEntity.getModerators();
        List<Integer> uidList = new ArrayList<>(3);
        for (int i=0; i<moderators.size(); i++) {
            uidList.add(moderators.getInteger(i));
        }
        List<UserEntity> userList = userService.getUsersInUidList(uidList);
        List<Map<String, Object>> moderatorList = new ArrayList<>(3);
        for (UserEntity userEntity: userList) {
            Map<String, Object> userObject = new HashMap<>(2);
            userObject.put("nickname", userEntity.getNickname());
            userObject.put("avatar", userEntity.getAvatar());
            moderatorList.add(userObject);
        }
        return new ResponseEntity(moderatorList);
    }


    private void checkProblemFormat(AddProblemFormat format) {

        // valid sample
        if (format.getSamples().size()==0)
            throw new RuntimeException("样本不得为空");

        for (Object obj: format.getSamples()) {
            boolean input = !((JSONObject) obj).containsKey("input");
            boolean output = !((JSONObject) obj).containsKey("output");
            if (input || output)
                throw new RuntimeException("样本格式不符");
        }

        // valid tags
        if (format.getTags().size()==0) {
            throw new RuntimeException("标签不得为空");
        }
    }

    private boolean checkProblemEditPermission(ProblemEntity problemEntity) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();
        return uid == problemEntity.getOwner() || problemEntity.getModerators().contains(uid) || role ==DefaultConfig.ADMIN_ROLE;
    }
}
