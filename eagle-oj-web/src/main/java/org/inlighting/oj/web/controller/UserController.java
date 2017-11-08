package org.inlighting.oj.web.controller;

import com.alibaba.fastjson.JSONArray;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.controller.format.user.ProblemAddProblemFormat;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Smith
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    private ProblemService problemService;

    @Autowired
    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    @PostMapping(value = "/problem")
    public ResponseEntity addProblem(@RequestBody @Valid ProblemAddProblemFormat format) {
        int owner = SessionHelper.get().getUid();
        JSONArray moderator = new JSONArray();
        // valid code_language
        if (format.getCodeLanguage().size()==0)
            throw new RuntimeException("编程语言不得为空");

        // valid sample
        if (format.getSample().size()==0)
            throw new RuntimeException("样本不得为空");

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

    @PutMapping("/problem")
    public ResponseEntity updateProblem() {
        // todo
        return null;
    }

    /**
     * 附带test_case个数
     */
    @GetMapping("/problem")
    public ResponseEntity getProblem() {
        // todo
        return null;
    }

    @PostMapping("/problem/test_case")
    public ResponseEntity addProblemTestCase() {
        // todo
        return null;
    }

    @GetMapping("/problem/test_case")
    public ResponseEntity getProblemTestCase() {
        // todo
        return null;
    }

    @PutMapping("/problem/test")
    public ResponseEntity updateProblemTestCase() {
        // todo
        return null;
    }
}
