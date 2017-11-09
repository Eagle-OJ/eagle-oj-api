package org.inlighting.oj.web.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.Hash;
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

    @PostMapping
    public ResponseEntity addProblem(@RequestBody @Valid AddProblemFormat format) {
        int owner = SessionHelper.get().getUid();
        JSONArray moderator = new JSONArray();
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

        if (!problemService.addProblem(owner,
                format.getCodeLanguage(),
                format.getTitle(),
                format.getDescription(),
                format.getDifficult(),
                format.getInputFormat(),
                format.getOutputFormat(),
                format.getConstraint(),
                format.getSample(),
                moderator,
                format.getTag(),
                format.getShare(),
                System.currentTimeMillis())) {
            throw new RuntimeException("添加题目失败");
        }

        return new ResponseEntity("题目添加成功");
    }

    @PutMapping
    public ResponseEntity updateProblem() {
        return null;
    }

    @ApiOperation("获取题目和他所有的测试用例")
    @GetMapping("/{pid}")
    public ResponseEntity getProblem(@PathVariable("pid") int pid) {
        // todo
        ProblemEntity problemEntity = problemService.getProblemByPid(pid);

        if (problemEntity == null)
            throw new RuntimeException("此题目不存在");

        List<TestCaseEntity> testCaseEntityList = testCaseService.getTestCases(pid);
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
        // todo
        // 检验pid的归属者是否为本人
        int owner = problemService.getProblemByPid(pid).getOwner();
        if (owner != SessionHelper.get().getUid())
            throw new UnauthorizedException();

        // 添加test_case
        if (!testCaseService.addTestCase(pid, owner, format.getStdin(),
                format.getStdout(), format.getStrength(), System.currentTimeMillis())) {
            throw new RuntimeException("添加失败");
        }

        return new ResponseEntity("添加成功");
    }

    @ApiOperation("删除指定的test_case")
    @DeleteMapping("/{pid}/tid/{tid}")
    public ResponseEntity deleteProblemTestCase(@PathVariable("pid") int pid,
                                                @PathVariable("tid") int tid) {
        // todo
        int owner = SessionHelper.get().getUid();
        // 删除test_case
        if (! testCaseService.deleteTestCaseByTidAndOwner(tid, owner)) {
            throw new RuntimeException("删除失败");
        }

        return new ResponseEntity("删除成功");
    }

    @ApiOperation("更新指定的test_case")
    @PutMapping("/{pid}/tid/{tid}")
    public ResponseEntity updateProblemTestCase(@PathVariable("pid") int pid,
                                                @PathVariable("tid") int tid,
                                                @RequestBody @Valid AddProblemTestCaseFormat format) {
        // todo
        // 检验是否为本人
        int owner = SessionHelper.get().getUid();

        if (! testCaseService.updateTestCaseByTidAndOwner(tid, owner, format.getStdin(), format.getStdout(),
                format.getStrength(), System.currentTimeMillis())) {
            throw new RuntimeException("更新失败");
        }
        return new ResponseEntity("更新成功");
    }
}
