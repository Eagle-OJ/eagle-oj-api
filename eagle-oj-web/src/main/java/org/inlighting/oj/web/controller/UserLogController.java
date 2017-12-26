package org.inlighting.oj.web.controller;

import io.swagger.annotations.ApiOperation;
import org.inlighting.oj.web.controller.exception.WebErrorException;
import org.inlighting.oj.web.entity.ResponseEntity;
import org.inlighting.oj.web.entity.UserLogEntity;
import org.inlighting.oj.web.service.UserLogService;
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

    private UserLogService userLogService;

    @Autowired
    public void setUserLogService(UserLogService userLogService) {
        this.userLogService = userLogService;
    }

    @ApiOperation("获取用户近期记录")
    @GetMapping
    public ResponseEntity getUserLog(@RequestParam("uid") int uid,
                                     @RequestParam("time") String time) {
        List<UserLogEntity> tempList;
        int timeLength = 0;
        if (time.equals("week")) {
            tempList = userLogService.getInWeek(uid);
            timeLength = 7;
        } else if (time.equals("month")) {
            tempList = userLogService.getInMonth(uid);
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
            calendar.set(Calendar.HOUR, 12);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            mapping.put(calendar.getTime().toString(), entity);
        }
        List<UserLogEntity> finalList = new ArrayList<>(timeLength);
        for (int i=timeLength; i>=0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.add(Calendar.DATE, -i);
            Date date = calendar.getTime();
            if (mapping.containsKey(date.toString())) {
                finalList.add(mapping.get(date.toString()));
            } else {
                UserLogEntity entity = new UserLogEntity();
                entity.setUid(uid);
                entity.setDate(date);
                finalList.add(entity);
            }
        }
        return new ResponseEntity(finalList);
    }
}
