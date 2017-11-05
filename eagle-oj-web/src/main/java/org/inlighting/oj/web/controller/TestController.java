package org.inlighting.oj.web.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author Smith
 **/
@RestController
public class TestController {

    @ApiOperation(value = "haha this is hello", notes = "wo ri ni da ye")
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/{username}/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ke xuan1", format = "test"),
            @ApiImplicitParam(name = "ke xuan2", value = "test")}
    )
    public String say(@PathVariable("username") String username,
                      @PathVariable("id") int id) {
        return username+":"+id;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String ok() {
        return "abc";
    }
}
