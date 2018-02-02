package com.eagleoj.web.controller;

import com.eagleoj.web.controller.format.admin.AddJudgerFormat;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.judger.JudgerDispatcher;
import com.eagleoj.web.service.JudgerService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/judger", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class JudgerController {

    @Autowired
    private JudgerService judgerService;

    @Autowired
    private JudgerDispatcher judgerDispatcher;

    @RequiresAuthentication
    @RequiresRoles("9")
    @GetMapping
    public ResponseEntity getJudgerList() {
        return new ResponseEntity(judgerService.listJudger());
    }

    @RequiresAuthentication
    @RequiresRoles("9")
    @PostMapping
    public ResponseEntity addJudger(@RequestBody @Valid AddJudgerFormat format) {
        judgerService.addJudger(format.getUrl(), format.getPort());
        judgerDispatcher.refresh();
        return new ResponseEntity("判卷机添加成功");
    }

    @RequiresAuthentication
    @RequiresRoles("9")
    @PutMapping("/{jid}")
    public ResponseEntity updateJudge(@PathVariable int jid,
                                      @RequestBody @Valid AddJudgerFormat format) {
        judgerService.updateJudger(jid, format.getUrl(), format.getPort());
        judgerDispatcher.refresh();
        return new ResponseEntity("判卷机更新成功");
    }

    @RequiresAuthentication
    @RequiresRoles("9")
    @DeleteMapping("/{jid}")
    public ResponseEntity deleteJudger(@PathVariable int jid) {
        judgerService.deleteJudger(jid);
        judgerDispatcher.refresh();
        return new ResponseEntity("删除判卷机成功");
    }
}
