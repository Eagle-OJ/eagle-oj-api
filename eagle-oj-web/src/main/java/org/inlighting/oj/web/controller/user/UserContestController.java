package org.inlighting.oj.web.controller.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.controller.format.user.AddContestProblemFormat;
import org.inlighting.oj.web.controller.format.user.CreateContestFormat;
import org.inlighting.oj.web.controller.format.user.EnterContestFormat;
import org.inlighting.oj.web.entity.ContestEntity;
import org.inlighting.oj.web.entity.ContestUserInfoEntity;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.security.SessionHelper;
import org.inlighting.oj.web.service.ContestService;
import org.inlighting.oj.web.service.ContestUserInfoService;
import org.inlighting.oj.web.service.ProblemService;
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
@RequestMapping(value = "/user/contest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserContestController {

    private ProblemService problemService;

    private ContestService contestService;

    private ContestUserInfoService contestUserInfoService;

    @Autowired
    public void setProblemService(ProblemService problemService) {
        this.problemService = problemService;
    }

    @Autowired
    public void setContestService(ContestService contestService) {
        this.contestService = contestService;
    }

    @Autowired
    public void setContestUserInfoService(ContestUserInfoService contestUserInfoService) {
        this.contestUserInfoService = contestUserInfoService;
    }

    @ApiOperation("创建比赛")
    @PostMapping
    public ResponseEntity createContest(@RequestBody @Valid CreateContestFormat format) {
        int owner = SessionHelper.get().getUid();
        int official = 0;

        long currentTime = System.currentTimeMillis();
        // valid time
        if (format.getStartTime() <= currentTime) {
            throw new RuntimeException("非法开始时间");
        }

        // endTime为0代表永远不结束
        if (format.getEndTime()!=0 && format.getStartTime() >= format.getEndTime()) {
            throw new RuntimeException("非法结束时间");
        }

        // 限时模式
        if (format.getType()==1 || format.getType()==3) {
            if (format.getTotalTime() == 0) {
                throw new RuntimeException("非法总时间");
            }
        } else {
            if (format.getTotalTime() != 0) {
                throw new RuntimeException("非法总时间");
            }
        }

        int cid = contestService.addContest(format.getName(), owner,format.getSlogan(),
                format.getDescription(), format.getStartTime(), format.getEndTime(),
                format.getTotalTime(), format.getPassword(), official, format.getType(), 1, currentTime);
        if (cid == 0) {
            throw new RuntimeException("比赛创建失败");
        }
        return new ResponseEntity("比赛创建成功", cid);
    }

    @ApiOperation("参加比赛")
    @PostMapping("/{cid}/enter")
    public ResponseEntity enterContest(@PathVariable("cid") int cid,
                                       @RequestBody @Valid EnterContestFormat format) {
        // 判断是否已经加入比赛
        int uid = SessionHelper.get().getUid();
        ContestUserInfoEntity contestUserInfoEntity = contestUserInfoService.getByCidAndUid(cid, uid);
        if (contestUserInfoEntity != null) {
            throw new RuntimeException("你已经加入比赛了");
        }

        // 校验密码
        ContestEntity contestEntity = contestService.getContestByCid(cid);
        if (contestEntity.getPassword() != null) {
            String password = contestEntity.getPassword();
            if (! password.equals(format.getPassword())) {
                throw new RuntimeException("密码错误");
            }
        }

        // 加入比赛
        if (! contestUserInfoService.add(cid, uid, System.currentTimeMillis())) {
            throw new RuntimeException("加入比赛失败");
        }
        return new ResponseEntity("加入比赛成功");
    }

    @ApiOperation("向比赛添加题目")
    @PostMapping("/{cid}/problem")
    public ResponseEntity addContestProblem(@PathVariable("cid") int cid,
                                            @RequestBody @Valid AddContestProblemFormat format) {
        // todo
        // 校验数据
        JSONArray problems = format.getProblems();
        if (problems.size() ==0) {
            throw new RuntimeException("数据非法");
        }

        int uid = SessionHelper.get().getUid();

        // 校验自己是否为比赛拥有者
        ContestEntity contestEntity = contestService.getContestByCid(cid);
        if (contestEntity.getOwner() != uid) {
            throw new RuntimeException("非法操作");
        }

        List<Integer> problemList = new ArrayList<>(10);
        for (int i=0; i<problems.size(); i++) {
            problemList.add(problems.getInteger(i));
        }
        if (! problemService.addContestProblem(problemList, cid)) {
            throw new RuntimeException("题目添加失败");
        }
        return new ResponseEntity("题目添加成功");
    }
}
