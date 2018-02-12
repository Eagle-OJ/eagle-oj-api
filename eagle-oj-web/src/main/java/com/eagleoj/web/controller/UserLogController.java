package com.eagleoj.web.controller;

import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.service.ProblemUserService;
import com.eagleoj.web.util.WebUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiOperation;
import com.eagleoj.web.entity.UserLogEntity;
import com.eagleoj.web.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping("/user_log")
public class UserLogController {

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private ProblemUserService problemUserService;

    @ApiOperation("获取用户敲代码近期记录")
    @GetMapping("coding_frequency")
    public ResponseEntity getUserLog(@RequestParam("uid") int uid,
                                     @RequestParam("time") String time) {
        List<UserLogEntity> tempList;
        int timeLength;
        if (time.equals("week")) {
            tempList = userLogService.listUserLogInWeek(uid);
            timeLength = 7;
        } else if (time.equals("month")) {
            tempList = userLogService.listUserLogInMonth(uid);
            timeLength = 30;
        } else {
            throw new WebErrorException("非法时间");
        }

        // format log
        Map<String, UserLogEntity> mapping = new HashMap<>(tempList.size());
        for (UserLogEntity entity: tempList) {
            Date date = entity.getDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            String key = calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
            mapping.put(key, entity);
        }
        List<UserLogEntity> finalList = new ArrayList<>(timeLength);
        for (int i=timeLength; i>=0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -i);
            String key = calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
            if (mapping.containsKey(key)) {
                finalList.add(mapping.get(key));
            } else {
                UserLogEntity entity = new UserLogEntity();
                entity.setUid(uid);
                entity.setDate(calendar.getTime());
                finalList.add(entity);
            }
        }
        return new ResponseEntity(finalList);
    }

    @ApiOperation("获取最近用户做题记录")
    @GetMapping("problem_history")
    public ResponseEntity getProblemUser(@RequestParam("uid") int uid,
                                         @RequestParam("page") int page,
                                         @RequestParam("page_size") int pageSize) {
        Page pager = PageHelper.startPage(page, pageSize);
        List<Map<String, Object>> list = problemUserService.listUserProblemHistory(uid);
        return new ResponseEntity(WebUtil.generatePageData(pager, list));
    }
}
