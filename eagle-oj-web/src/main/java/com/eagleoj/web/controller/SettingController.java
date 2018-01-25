package com.eagleoj.web.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import com.eagleoj.web.config.OSSConfig;
import com.eagleoj.web.config.SystemConfig;
import com.eagleoj.web.controller.exception.UnauthorizedException;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.index.AddSettingFormat;
import com.eagleoj.web.controller.format.index.UpdateSettingFormat;
import com.eagleoj.web.entity.ResponseEntity;
import com.eagleoj.web.entity.SettingEntity;
import com.eagleoj.web.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
        SystemConfig config = settingService.getSystemConfig();
        if (isDetail) {
            Subject subject = SecurityUtils.getSubject();
            if (! (subject.isAuthenticated() && subject.hasRole("9"))) {
                throw new UnauthorizedException();
            }
            return new ResponseEntity(config);
        } else {
            SystemConfig newConfig = new SystemConfig();
            newConfig.setInstalled(config.isInstalled());
            newConfig.setTitle(config.getTitle());
            newConfig.setLang(config.getLang());
            OSSConfig ossConfig = config.getOssConfig();
            OSSConfig newOSSConfig = new OSSConfig();
            newOSSConfig.setURL(ossConfig.getURL());
            newConfig.setOssConfig(newOSSConfig);
            return new ResponseEntity(newConfig);
        }

    }

    @PostMapping
    public ResponseEntity addSetting(@RequestBody @Valid AddSettingFormat format) {
        List<SettingEntity> list = settingService.getAll();
        if (list.size() != 0) {
            throw new WebErrorException("系统已经配置过了");
        }

        if (! settingService.installWeb(format.getEmail(), format.getNickname(), format.getPassword(),
                format.getTitle(), format.getAccessKey(), format.getSecretKey(), format.getEndPoint(),
                format.getBucket(), format.getUrl())) {
            throw new WebErrorException("网站安装失败");
        }
        return new ResponseEntity("安装成功");
    }

    @RequiresAuthentication
    @RequiresRoles("9")
    @PutMapping
    public ResponseEntity updateSetting(@RequestBody @Valid UpdateSettingFormat format) {
        if (! settingService.updateSetting(format.getTitle(), format.getAccessKey(), format.getSecretKey(),
                format.getEndPoint(), format.getBucket(), format.getUrl())) {
            throw new WebErrorException("网站配置更新失败");
        }
        settingService.reloadSetting();
        return new ResponseEntity("网站配置更新成功，刷新网页后生效");
    }
}
