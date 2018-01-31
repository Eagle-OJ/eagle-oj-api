package com.eagleoj.web.controller;

import com.eagleoj.web.controller.exception.UnauthorizedException;
import com.eagleoj.web.controller.format.user.AddContestProblemFormat;
import com.eagleoj.web.controller.format.user.CreateContestFormat;
import com.eagleoj.web.controller.format.user.EnterContestFormat;
import com.eagleoj.web.controller.format.user.UpdateContestProblemFormat;
import com.eagleoj.web.data.status.ContestStatus;
import com.eagleoj.web.data.status.ContestTypeStatus;
import com.eagleoj.web.data.status.RoleStatus;
import com.eagleoj.web.entity.*;
import com.eagleoj.web.security.SessionHelper;
import com.eagleoj.web.service.ContestProblemService;
import com.eagleoj.web.service.ContestService;
import com.eagleoj.web.service.ProblemService;
import com.eagleoj.web.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageRowBounds;
import io.swagger.annotations.ApiOperation;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.entity.*;
import com.eagleoj.web.service.*;
import com.eagleoj.web.util.WebUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
@RequestMapping(value = "/contest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ContestController {

    @Autowired
    private ContestService contestService;

    @Autowired
    private UserService userService;

    @Autowired
    private ContestProblemService contestProblemService;

    @Autowired
    private ContestUserService contestUserService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private LeaderboardService leaderboardService;

    @ApiOperation("获取某个比赛信息")
    @GetMapping("/{cid}")
    public ResponseEntity getContest(@PathVariable("cid") int cid,
                                     @RequestParam(name = "is_detail", required = false, defaultValue = "false") boolean isDetail) {
        ContestEntity contestEntity = contestService.getContest(cid);
        if (isDetail && SecurityUtils.getSubject().isAuthenticated()) {
            accessToEditContest(contestEntity);
        } else {
            if (contestEntity.getPassword()!=null) {
                contestEntity.setPassword("You can't see it");
            }
        }
        return new ResponseEntity(contestEntity);
    }

    @ApiOperation("获取比赛中题目的信息")
    @RequiresAuthentication
    @GetMapping("/{cid}/problem/{pid}")
    public ResponseEntity getContestProblem(@PathVariable("cid") int cid,
                                            @PathVariable("pid") int pid) {
        ProblemEntity problemEntity = problemService.getProblem(pid);

        // 加载本次比赛中此题的提交情况
        ContestProblemEntity contestProblemEntity = contestProblemService.getContestProblem(cid, pid);
        problemEntity.setSubmitTimes(contestProblemEntity.getSubmitTimes());
        problemEntity.setACTimes(contestProblemEntity.getACTimes());
        problemEntity.setCETimes(contestProblemEntity.getCETimes());
        problemEntity.setWATimes(contestProblemEntity.getWATimes());
        problemEntity.setTLETimes(contestProblemEntity.getTLETimes());
        problemEntity.setRTETimes(contestProblemEntity.getRTETimes());

        // 加载用户信息
        UserEntity userEntity = userService.getUserByUid(problemEntity.getOwner());

        // 加载比赛信息
        ContestEntity contestEntity = contestService.getContest(cid);
        boolean contestStatus = true;
        if (contestEntity.getStatus() != ContestStatus.USING.getNumber()) {
            contestStatus = false;
        }
        // 判断用户在此比赛中是否能继续答题
        if (contestEntity.getType() == ContestTypeStatus.OI_CONTEST_LIMIT_TIME.getNumber() ||
                contestEntity.getType() == ContestTypeStatus.ACM_CONTEST_LIMIT_TIME.getNumber()) {
            ContestUserEntity contestUserEntity = contestUserService.get(cid, SessionHelper.get().getUid());
            if (System.currentTimeMillis() > (contestUserEntity.getJoinTime() + contestEntity.getTotalTime())) {
                contestStatus = false;
            }
        }
        Map<String, Object> map = new HashMap<>(3);
        map.put("problem", problemEntity);
        Map<String, Object> userMap = new HashMap<>(2);
        userMap.put("nickname", userEntity.getNickname());
        userMap.put("avatar", userEntity.getAvatar());
        map.put("author", userMap);
        Map<String, Object> contestMap = new HashMap<>(2);
        contestMap.put("status", contestStatus);
        contestMap.put("name", contestEntity.getName());
        contestMap.put("group", contestEntity.getGroup());
        map.put("contest", contestMap);
        return new ResponseEntity(map);
    }


    @ApiOperation("创建比赛")
    @RequiresAuthentication
    @PostMapping
    public ResponseEntity createContest(@RequestBody @Valid CreateContestFormat format) {
        if (format.getGroup() == null) {
            throw new WebErrorException("group不能为空");
        }
        checkContestFormat(format);
        int owner = SessionHelper.get().getUid();
        int cid = contestService.saveContest(format.getName(), owner, format.getGroup(), format.getSlogan(),
                format.getDescription(), format.getStartTime(), format.getEndTime(),
                format.getTotalTime(), format.getPassword(), format.getType());
        return new ResponseEntity("比赛创建成功", cid);
    }

    @ApiOperation("编辑比赛")
    @RequiresAuthentication
    @PutMapping("/{cid}")
    public ResponseEntity editContest(@PathVariable("cid") int cid,
                                      @RequestBody @Valid CreateContestFormat format) {
        if (format.getStatus() == null) {
            throw new WebErrorException("比赛status为空");
        }

        checkContestFormat(format);

        ContestEntity contestEntity = contestService.getContest(cid);
        accessToEditContest(contestEntity);

        contestService.updateContest(cid, format.getName(), format.getSlogan(), format.getDescription(),
                format.getStartTime(), format.getEndTime(), format.getTotalTime(),
                format.getPassword(), format.getType(), format.getStatus());

        return new ResponseEntity("比赛更新成功");
    }

    @ApiOperation("获取比赛的题目列表")
    @RequiresAuthentication
    @GetMapping("/{cid}/problems")
    public ResponseEntity getContestProblems(@PathVariable("cid") int cid,
                                             @RequestParam(name = "is_detail", required = false, defaultValue = "false") boolean isDetail) {
        ContestEntity contestEntity = contestService.getContest(cid);
        List<Map<String, Object>> list;
        if (isDetail) {
            // 后台显示题目列表
            accessToEditContest(contestEntity);
            list = contestProblemService.listContestProblems(cid);
        } else {
            // 前台显示题目列表，附带用户提交信息
            int uid = SessionHelper.get().getUid();
            // 检验是否加入了比赛
            contestUserService.get(cid, uid);
            list = contestProblemService.listContestProblems(cid, uid);
        }
        return new ResponseEntity(list);
    }

    @ApiOperation("添加比赛题目")
    @RequiresAuthentication
    @PostMapping("/{cid}/problem")
    public ResponseEntity addContestProblem(@PathVariable("cid") int cid,
                                            @RequestBody @Valid AddContestProblemFormat format) {
        // 校验数据
        int pid = format.getPid();
        int score = format.getScore();
        int displayId = format.getDisplayId();
        if (pid < 1 || score < 1 || displayId < 1) {
            throw new WebErrorException("数据格式错误");
        }

        // 校验比赛和权限
        ContestEntity contestEntity = contestService.getContest(cid);
        accessToEditContest(contestEntity);

        contestProblemService.saveContestProblem(cid, pid, displayId, score);

        return new ResponseEntity("题目添加成功");
    }

    @ApiOperation("更新比赛题目的分值和题号")
    @RequiresAuthentication
    @PutMapping("/{cid}/problem/{pid}")
    public ResponseEntity updateContestProblem(@PathVariable("cid") int cid,
                                               @PathVariable("pid") int pid,
                                               @RequestBody @Valid UpdateContestProblemFormat format) {
        ContestEntity contestEntity = contestService.getContest(cid);
        accessToEditContest(contestEntity);

        contestProblemService.updateContestProblem(cid, pid, format.getDisplayId(), format.getScore());
        return new ResponseEntity("更新成功");
    }

    @ApiOperation("删除比赛的题目")
    @RequiresAuthentication
    @DeleteMapping("/{cid}/problem/{pid}")
    public ResponseEntity deleteContestProblem(@PathVariable("cid") int cid,
                                               @PathVariable("pid") int pid) {
        ContestEntity contestEntity = contestService.getContest(cid);
        accessToEditContest(contestEntity);
        contestProblemService.deleteContestProblem(cid, pid);
        return new ResponseEntity("删除成功");
    }

    @ApiOperation("参加比赛")
    @RequiresAuthentication
    @PostMapping("/{cid}/enter")
    public ResponseEntity enterContest(@PathVariable("cid") int cid,
                                       @RequestBody @Valid EnterContestFormat format) {
        int uid = SessionHelper.get().getUid();
        contestUserService.joinContest(cid, uid, format.getPassword());
        return new ResponseEntity("加入比赛成功");
    }

    @ApiOperation("获取本人在某个比赛中的状况+题目列表")
    @RequiresAuthentication
    @GetMapping("/{cid}/data")
    public ResponseEntity getContestUserInfo(@PathVariable("cid") int cid) {
        int uid = SessionHelper.get().getUid();
        ContestUserEntity info = contestUserService.get(cid, uid);

        Map<String, Object> map = new HashMap<>(3);
        map.put("meta", leaderboardService.getUserMetaInContest(uid, cid));
        map.put("user", info);
        map.put("problems", contestProblemService.listContestProblems(cid, info.getUid()));
        return new ResponseEntity(map);
    }

    private void checkContestFormat(CreateContestFormat format) {
        // endTime为0代表永远不结束
        if (format.getStartTime() >= format.getEndTime() || format.getEndTime() <= System.currentTimeMillis()) {
            throw new WebErrorException("非法结束时间");
        }

        // 限时模式
        if (format.getType() == ContestTypeStatus.OI_CONTEST_LIMIT_TIME.getNumber()
                || format.getType() == ContestTypeStatus.ACM_CONTEST_LIMIT_TIME.getNumber()) {
            if (format.getTotalTime() == null || format.getTotalTime()<=0) {
                throw new WebErrorException("非法总时间");
            }
        }
    }

    private void accessToEditContest(ContestEntity contestEntity) {
        int uid = SessionHelper.get().getUid();
        int role = SessionHelper.get().getRole();
        if (contestEntity.getOwner() == uid) {
            return;
        }

        if (role >= RoleStatus.ADMIN.getNumber()) {
            return;
        }

        throw new UnauthorizedException();
    }
}
