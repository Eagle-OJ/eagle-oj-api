package org.inlighting.oj.web.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.DefaultConfig;
import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.controller.format.user.AddProblemFormat;
import org.inlighting.oj.web.controller.format.user.AddProblemModeratorFormat;
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
import org.inlighting.oj.web.util.WebUtil;
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
            throw new WebErrorException("标签非法");
        }

        int pid = problemService.addProblem(owner, format.getTitle(), format.getDescription(), format.getInputFormat(),
                format.getOutputFormat(), format.getDifficult(), format.getSamples(), tags,
                System.currentTimeMillis());

        if (pid == 0) {
            throw new WebErrorException("添加题目失败");
        }

        return new ResponseEntity("题目添加成功", pid);
    }

    @ApiOperation("获取用户创建的题目")
    @GetMapping
    public ResponseEntity getProblems(@RequestParam(name = "page") int page,
                                      @RequestParam(name = "page_size") int pageSize) {
        int uid = SessionHelper.get().getUid();
        PageRowBounds pager = new PageRowBounds(page, pageSize);
        return new ResponseEntity(WebUtil.generatePageData(pager, problemService.getProblemsByUid(uid, pager)));
    }

    @GetMapping("/{pid}")
    public ResponseEntity getProblemDescription(@PathVariable("pid") int pid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);

        haveProblem(problemEntity);
        havePermission(problemEntity);

        return new ResponseEntity(problemEntity);
    }

    @ApiOperation("更新题目")
    @PutMapping("/{pid}")
    public ResponseEntity updateProblemDescription(@PathVariable("pid") int pid,
                                        @RequestBody @Valid AddProblemFormat format) {
        // 检验数据
        checkProblemFormat(format);

        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
        havePermission(problemEntity);

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
            throw new WebErrorException("非法标签");
        }

        // 更新数据
        if (!problemService.updateProblemDescription(pid, format.getTitle(), format.getDescription(), format.getInputFormat(),
                format.getOutputFormat(), format.getSamples(), format.getDifficult(), finalTags)) {
            throw new WebErrorException("题目更新失败");
        }

        return new ResponseEntity("题目更新成功");
    }


    @ApiOperation("获取一道题目的测试用例")
    @GetMapping("/{pid}/test_cases")
    public ResponseEntity getProblemTestCase(@PathVariable int pid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
        havePermission(problemEntity);

        List<TestCaseEntity> testCaseEntities = testCasesService.getAllTestCasesByPid(problemEntity.getPid());
        return new ResponseEntity(testCaseEntities);
    }

    @ApiOperation("添加一道题目的一个测试用例")
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

    @ApiOperation("删除指定的moderator")
    @DeleteMapping("/{pid}/moderator/{uid}")
    public ResponseEntity deleteProblemModerator(@PathVariable("pid") int pid,
                                                 @PathVariable("uid") int uid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);

        haveProblem(problemEntity);
        havePermission(problemEntity);

        JSONArray moderators = problemEntity.getModerators();
        if (! moderators.contains(uid)) {
            throw new WebErrorException("不存在此用户");
        } else {
            moderators.remove((Object) uid);
        }

        if (! problemService.updateProblemModerators(pid, moderators)) {
            throw new WebErrorException("删除失败");
        }

        return new ResponseEntity("删除成功");
    }

    @ApiOperation("添加指定的moderator")
    @PutMapping("/{pid}/moderators")
    public ResponseEntity addProblemModerator(@PathVariable("pid") int pid,
                                              @RequestBody @Valid AddProblemModeratorFormat format) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        haveProblem(problemEntity);
        haveOwnPermission(problemEntity);

        UserEntity userEntity = userService.getUserByEmail(format.getEmail());
        if (userEntity == null) {
            throw new WebErrorException("此用户不存在");
        }

        JSONArray moderators = problemEntity.getModerators();
        if (moderators.contains(userEntity.getUid())) {
            throw new WebErrorException("此用户已经添加");
        } else {
            moderators.add(userEntity.getUid());
        }
        if (! problemService.updateProblemModerators(pid, moderators)) {
            throw new WebErrorException("添加失败");
        }
        return new ResponseEntity("添加成功");
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

    private void haveProblem(ProblemEntity problemEntity) {
        if (problemEntity == null) {
            throw new WebErrorException("题目不存在");
        }
    }

    private void havePermission(ProblemEntity problemEntity) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();
        if (! (uid == problemEntity.getOwner() || problemEntity.getModerators().contains(uid) || role ==DefaultConfig.ADMIN_ROLE)) {
            throw new WebErrorException("非法操作");
        }
    }

    private void haveOwnPermission(ProblemEntity problemEntity) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();
        if (! (uid == problemEntity.getOwner() || role ==DefaultConfig.ADMIN_ROLE)) {
            throw new WebErrorException("只允许本人操作");
        }
    }

}
