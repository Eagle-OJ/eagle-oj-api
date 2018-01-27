package com.eagleoj.web.controller;

import com.github.pagehelper.PageRowBounds;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/tags", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TagsController {

    @Autowired
    private TagsService tagsService;

    @GetMapping
    public ResponseEntity listTags() {
        return new ResponseEntity(tagsService.listAll());
    }

}
