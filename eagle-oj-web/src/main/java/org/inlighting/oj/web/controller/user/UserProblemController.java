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
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.ProblemService;
import org.inlighting.oj.web.service.TestCaseService;
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
@RequestMapping(value = "/user/problem", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserProblemController {

    private ProblemService problemService;

    private TestCaseService testCaseService;

    @Autowired
    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Autowired
    public void setTestCaseService(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @ApiOperation("添加题目")
    @PostMapping
    public ResponseEntity addProblem(@RequestBody @Valid AddProblemFormat format) {
        int owner = SessionHelper.get().getUid();

        checkProblemFormat(format);

        int pid = problemService.addProblem(owner, format.getCodeLanguage(),
                format.getTitle(),
                format.getDescription(),
                format.getDifficult(),
                format.getInputFormat(),
                format.getOutputFormat(),
                format.getConstraint(),
                format.getSample(),
                format.getModerator(),
                format.getTag(),
                format.getShare(),
                System.currentTimeMillis());

        if (pid == 0) {
            throw new RuntimeException("添加题目失败");
        }

        return new ResponseEntity("题目添加成功", pid);
    }

    @ApiOperation("更新题目")
    @PutMapping("/{pid}")
    public ResponseEntity updateProblem(@PathVariable("pid") int pid,
                                        @RequestBody @Valid AddProblemFormat format) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        if (problemEntity == null) {
            throw new RuntimeException("题目不存在");
        }

        int owner = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();
        JSONArray moderator = problemEntity.getModerator();
        if (!moderator.contains(owner) && owner!=problemEntity.getOwner() && role!= DefaultConfig.ADMIN_ROLE)
            throw new UnauthorizedException();

        // 检验数据
        checkProblemFormat(format);

        // 更新数据
        if (!problemService.updateProblemByPid(pid, format.getCodeLanguage(), format.getTitle(),
                format.getDescription(), format.getDifficult(), format.getInputFormat(), format.getOutputFormat(),
                format.getConstraint(), format.getSample(), format.getModerator(), format.getTag(), format.getShare())) {
            throw new RuntimeException("题目更新失败");
        }

        return new ResponseEntity("题目更新成功");
    }

    private void checkProblemFormat(AddProblemFormat format) {
        // valid code_language
        if (format.getCodeLanguage().size()==0)
            throw new RuntimeException("编程语言不得为空");

        // valid sample
        if (format.getSample().size()==0)
            throw new RuntimeException("样本不得为空");

        for (Object obj: format.getSample()) {
            //obj = (JSONObject) obj;
            boolean input = !((JSONObject) obj).containsKey("input");
            boolean output = !((JSONObject) obj).containsKey("output");
            if (input || output)
                throw new RuntimeException("样本格式不符");
        }

        // valid tag
        if (format.getTag().size()==0)
            throw new RuntimeException("标签不得为空");
    }


    @ApiOperation("获取题目和他所有的测试用例")
    @GetMapping("/{pid}")
    public ResponseEntity getProblem(@PathVariable("pid") int pid) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);

        if (problemEntity == null)
            throw new RuntimeException("此题目不存在");

        List<TestCaseEntity> testCaseEntityList = testCaseService.getAllTestCasesByPid(pid);
        Map<String, Object> map = new HashMap<>();
        map.put("problem", problemEntity);
        map.put("test_case", testCaseEntityList);
        return new ResponseEntity(map);
    }

    @ApiOperation("添加一道题目的一个测试用例")
    @PostMapping("/{pid}/test_case")
    public ResponseEntity addProblemTestCase(
            @PathVariable("pid") int pid,
            @RequestBody @Valid AddProblemTestCaseFormat format) {
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);
        if (problemEntity == null) {
            throw new RuntimeException("此题目不存在");
        }
        JSONArray moderator = problemEntity.getModerator();
        int owner = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();
        if (owner != problemEntity.getOwner() && !moderator.contains(owner) && role!=DefaultConfig.ADMIN_ROLE)
            throw new UnauthorizedException();

        // 添加test_case
        int tid = testCaseService.addTestCase(pid, format.getStdin(), format.getStdout(), format.getStrength());

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
        int owner = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();
        JSONArray moderator = problemEntity.getModerator();
        if (owner!=problemEntity.getOwner() && !moderator.contains(owner) && role!=DefaultConfig.ADMIN_ROLE) {
            throw new UnauthorizedException();
        }

        // 删除test_case
        if (! testCaseService.deleteTestCaseByTid(tid)) {
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
        JSONArray moderator = problemEntity.getModerator();
        int owner = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();
        if (owner!=problemEntity.getOwner() && !moderator.contains(owner) && role!=DefaultConfig.ADMIN_ROLE) {
            throw new UnauthorizedException();
        }

        if (! testCaseService.updateTestCaseByTid(tid, format.getStdin(), format.getStdout(),
                format.getStrength())) {
            throw new RuntimeException("更新失败");
        }
        return new ResponseEntity("更新成功");
    }
}
