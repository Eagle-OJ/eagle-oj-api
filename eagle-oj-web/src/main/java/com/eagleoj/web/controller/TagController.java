package com.eagleoj.web.controller;

import com.eagleoj.web.controller.format.admin.AddTagFormat;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.service.TagsService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
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
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TagController {

    @Autowired
    private TagsService tagsService;

    @ApiOperation("添加标签")
    @RequiresAuthentication
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @PostMapping
    public ResponseEntity addTag(@RequestBody @Valid AddTagFormat format) {
        tagsService.save(format.getName());
        return new ResponseEntity("添加标签成功");
    }

    @ApiOperation("删除标签")
    @RequiresAuthentication
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @DeleteMapping("/{tid}")
    public ResponseEntity deleteTag(@PathVariable int tid) {
        tagsService.deleteTag(tid);
        return new ResponseEntity("标签删除成功");
    }

    @ApiOperation("更新标签名")
    @RequiresAuthentication
    @RequiresRoles(value = {"8", "9"}, logical = Logical.OR)
    @PutMapping("/{tid}")
    public ResponseEntity updateTag(@PathVariable int tid,
                                    @RequestBody @Valid AddTagFormat format) {
        tagsService.updateTagName(tid, format.getName());
        return new ResponseEntity("标签更新成功");
    }
}
