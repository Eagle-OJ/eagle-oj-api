package com.eagleoj.web.controller;

import com.eagleoj.web.cache.CacheController;
import com.eagleoj.web.setting.SettingKeyMapper;
import com.eagleoj.web.setting.SettingService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import com.eagleoj.web.controller.exception.UnauthorizedException;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.index.AddSettingFormat;
import com.eagleoj.web.controller.format.index.UpdateSettingFormat;
import com.eagleoj.web.entity.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Smith
 **/
@RestController
@Validated
@RequestMapping(value = "/setting", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SettingController {

    @Autowired
    private SettingService settingService;

    @GetMapping
    public ResponseEntity getSetting(@RequestParam(name = "is_detail", required = false, defaultValue = "false") boolean isDetail) {
        Map<String, Object> settingMap = new HashMap<>();
        // 网站未安装
        if (! settingService.isInstalled()) {
            settingMap.put(SettingKeyMapper.IS_INSTALLED, false);
            return new ResponseEntity(settingMap);
        } else {
            settingMap.put(SettingKeyMapper.IS_INSTALLED, true);
        }


        settingMap.put(SettingKeyMapper.WEB_TITLE, settingService.getSetting(SettingKeyMapper.WEB_TITLE));
        settingMap.put(SettingKeyMapper.OSS_URL, settingService.getSetting(SettingKeyMapper.OSS_URL));
        settingMap.put(SettingKeyMapper.LANG, settingService.getLangMap());
        if (isDetail) {
            Subject subject = SecurityUtils.getSubject();
            if (! (subject.isAuthenticated() && subject.hasRole("9"))) {
                throw new UnauthorizedException();
            }

            settingMap.put(SettingKeyMapper.OSS_ACCESS_KEY, settingService.getSetting(SettingKeyMapper.OSS_ACCESS_KEY));
            settingMap.put(SettingKeyMapper.OSS_SECRET_KEY, settingService.getSetting(SettingKeyMapper.OSS_SECRET_KEY));
            settingMap.put(SettingKeyMapper.OSS_END_POINT, settingService.getSetting(SettingKeyMapper.OSS_END_POINT));
            settingMap.put(SettingKeyMapper.OSS_BUCKET, settingService.getSetting(SettingKeyMapper.OSS_BUCKET));
        }
        return new ResponseEntity(settingMap);
    }

    @RequiresRoles("9")
    @PutMapping
    public ResponseEntity updateSetting(@RequestBody @Valid UpdateSettingFormat format) {
        String key = format.getKey();
        String value = format.getValue();
        switch (key) {
            case "title":
                settingService.updateSetting(SettingKeyMapper.WEB_TITLE, value);
                break;
            case "accessKey":
                settingService.updateSetting(SettingKeyMapper.OSS_ACCESS_KEY, value);
                break;
            case "secretKey":
                settingService.updateSetting(SettingKeyMapper.OSS_SECRET_KEY, value);
                break;
            case "bucket":
                settingService.updateSetting(SettingKeyMapper.OSS_BUCKET, value);
                break;
            case "endPoint":
                settingService.updateSetting(SettingKeyMapper.OSS_END_POINT, value);
                break;
            case "url":
                settingService.updateSetting(SettingKeyMapper.OSS_URL, value);
                break;
            default:
                throw new WebErrorException("不存在此key");
        }
        settingService.refresh();
        return new ResponseEntity("网站配置更新成功");
    }

    @PostMapping
    public ResponseEntity install(@RequestBody @Valid AddSettingFormat format) {
        if (settingService.isInstalled()) {
            throw new WebErrorException("系统已经安装过了");
        }

        settingService.installWeb(format.getEmail(), format.getNickname(), format.getPassword(),
                format.getTitle(), format.getAccessKey(), format.getSecretKey(), format.getEndPoint(),
                format.getBucket(), format.getUrl());
        settingService.refresh();
        return new ResponseEntity("安装成功");
    }

    @ApiOperation("刷新系统缓存")
    @RequiresRoles("9")
    @PutMapping("/cache")
    public ResponseEntity refreshCache(@RequestParam String type) {
        switch (type) {
            case "user":
                CacheController.getAuthCache().clear();
                break;
            case "submission":
                CacheController.getSubmissionCache().clear();
                break;
            case "leaderboard":
                CacheController.getLeaderboard().clear();
                break;
            default:
                throw new WebErrorException("非法操作类型");
        }
        return new ResponseEntity("缓存刷新成功");
    }
}
