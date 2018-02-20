package com.eagleoj.web.controller;

import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.web.cache.CacheController;
import com.eagleoj.web.controller.format.admin.AddMailFormat;
import com.eagleoj.web.controller.format.admin.UpdateGlobalSettingFormat;
import com.eagleoj.web.controller.format.admin.UpdateStorageSettingFormat;
import com.eagleoj.web.file.FileService;
import com.eagleoj.web.mail.MailService;
import com.eagleoj.web.setting.SettingEnum;
import com.eagleoj.web.setting.SettingService;
import com.eagleoj.web.util.FileUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import com.eagleoj.web.controller.exception.WebErrorException;
import com.eagleoj.web.controller.format.index.AddSettingFormat;
import com.eagleoj.web.controller.format.index.UpdateSettingFormat;
import com.eagleoj.web.entity.ResponseEntity;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;

    @GetMapping
    public ResponseEntity getSetting() {
        Map<String, Object> settingMap = new HashMap<>();
        // is website installed
        if (! settingService.isInstalled()) {
            settingMap.put(SettingEnum.IS_INSTALLED.getName(), false);
            return new ResponseEntity(settingMap);
        } else {
            settingMap.put(SettingEnum.IS_INSTALLED.getName(), true);
        }

        // lang map
        Map<LanguageEnum, String> langMap = new HashMap<>();
        for (LanguageEnum languageEnum: LanguageEnum.values()) {
            langMap.put(languageEnum, languageEnum.getName());
        }
        settingMap.put("lang", langMap);

        // get setting
        String webTitle = settingService.getSetting(SettingEnum.WEB_TITLE);
        settingMap.put(SettingEnum.WEB_TITLE.getName(), webTitle);
        if (settingService.isOpenStorage()) {
            settingMap.put(SettingEnum.IS_OPEN_STORAGE.getName(), true);
            settingMap.put(SettingEnum.OSS_URL.getName(), settingService.getSetting(SettingEnum.OSS_URL));
        } else {
            settingMap.put(SettingEnum.IS_OPEN_STORAGE.getName(), false);
        }
        return new ResponseEntity(settingMap);
    }

    @ApiOperation("获取全局设置")
    @RequiresRoles("9")
    @GetMapping("/global")
    public ResponseEntity getGlobalSetting() {
        Map<String, String> map = new HashMap<>();
        map.put(SettingEnum.WEB_TITLE.getName(), settingService.getSetting(SettingEnum.WEB_TITLE));
        return new ResponseEntity(map);
    }

    @ApiOperation("更新全局设置")
    @RequiresRoles("9")
    @PostMapping("/global")
    public ResponseEntity updateGlobalSetting(@RequestBody @Valid UpdateGlobalSettingFormat format) {
        settingService.updateSetting(SettingEnum.WEB_TITLE, format.getTitle());
        return new ResponseEntity("更新设置成功");
    }

    @ApiOperation("获取存储设置")
    @RequiresRoles("9")
    @GetMapping("/storage")
    public ResponseEntity getStorageSetting() {
        Map<String, String> map = new HashMap<>();
        if (settingService.isOpenStorage()) {
            map.put(SettingEnum.IS_OPEN_STORAGE.getName(), "true");
            List<SettingEnum> keys = new ArrayList<>(5);
            keys.add(SettingEnum.OSS_ACCESS_KEY);
            keys.add(SettingEnum.OSS_SECRET_KEY);
            keys.add(SettingEnum.OSS_END_POINT);
            keys.add(SettingEnum.OSS_BUCKET);
            keys.add(SettingEnum.OSS_URL);
            List<String> values = settingService.listSettings(keys);
            for (int i=0; i<values.size(); i++) {
                map.put(keys.get(i).getName(), values.get(i));
            }
        } else {
            map.put(SettingEnum.IS_OPEN_STORAGE.getName(), "false");
        }
        return new ResponseEntity(map);
    }

    @ApiOperation("更新存储设置")
    @RequiresRoles("9")
    @PostMapping("/storage")
    public ResponseEntity updateStorageSetting(@RequestBody @Valid UpdateStorageSettingFormat format) {
        if (format.getOpenStorage()) {
            if (format.getAccessKey() == null || format.getSecretKey() == null || format.getEndPoint() == null ||
                    format.getBucket() == null || format.getUrl() == null) {
                throw new WebErrorException("参数不得为空");
            }

            // 进行客户端测试
            if (! fileService.test(format.getAccessKey(), format.getSecretKey(), format.getEndPoint(), format.getBucket())) {
                throw new WebErrorException("你填写的参数无效");
            }

            List<SettingEnum> keys = new ArrayList<>(6);
            List<String> values = new ArrayList<>(6);
            keys.add(SettingEnum.IS_OPEN_STORAGE);
            values.add("true");
            keys.add(SettingEnum.OSS_ACCESS_KEY);
            values.add(format.getAccessKey());
            keys.add(SettingEnum.OSS_SECRET_KEY);
            values.add(format.getSecretKey());
            keys.add(SettingEnum.OSS_END_POINT);
            values.add(format.getEndPoint());
            keys.add(SettingEnum.OSS_BUCKET);
            values.add(format.getBucket());
            keys.add(SettingEnum.OSS_URL);
            values.add(format.getUrl());
            settingService.updateSettings(keys, values);
            fileService.refresh();
        } else {
            settingService.updateSetting(SettingEnum.IS_OPEN_STORAGE, "false");
        }
        return new ResponseEntity("更新设置成功");
    }

    @ApiOperation("获取邮件设置")
    @RequiresRoles("9")
    @GetMapping("/mail")
    public ResponseEntity getMailSetting() {
        Map<String, String> map = new HashMap<>();
        if (settingService.isOpenMail()) {
            map.put(SettingEnum.IS_OPEN_MAIL.getName(), "true");
            List<SettingEnum> keys = new ArrayList<>(4);;
            keys.add(SettingEnum.MAIL_HOST);
            keys.add(SettingEnum.MAIL_PORT);
            keys.add(SettingEnum.MAIL_USERNAME);
            keys.add(SettingEnum.MAIL_PASSWORD);
            List<String> values = settingService.listSettings(keys);
            for (int i=0; i<keys.size(); i++) {
                map.put(keys.get(i).getName(), values.get(i));
            }
        } else {
            map.put(SettingEnum.IS_OPEN_MAIL.getName(), "false");
        }
        return new ResponseEntity(map);
    }

    @ApiOperation("更新")
    @RequiresRoles("9")
    @PostMapping("/mail")
    public ResponseEntity addMailSetting(@RequestBody @Valid AddMailFormat format) {
        Boolean isOpenMail = format.getOpenMail();
        String host = format.getHost();
        Integer port = format.getPort();
        String username = format.getUsername();
        String password = format.getPassword();

        if (isOpenMail) {
            if (host == null || port == null || username == null || password == null) {
                throw new WebErrorException("邮件信息不完整");
            }

            List<SettingEnum> keys = new ArrayList<>(5);
            List<String> values = new ArrayList<>(5);
            keys.add(SettingEnum.IS_OPEN_MAIL);
            values.add("true");
            keys.add(SettingEnum.MAIL_HOST);
            values.add(host);
            keys.add(SettingEnum.MAIL_PORT);
            values.add(String.valueOf(port));
            keys.add(SettingEnum.MAIL_USERNAME);
            values.add(username);
            keys.add(SettingEnum.MAIL_PASSWORD);
            values.add(password);
            settingService.updateSettings(keys, values);
        } else {
            settingService.updateSetting(SettingEnum.IS_OPEN_MAIL, "false");
        }
        return new ResponseEntity("更新设置成功");
    }

    @ApiOperation("测试邮件发送")
    @RequiresRoles("9")
    @PostMapping("/mail/test")
    public ResponseEntity testMailSetting(@RequestBody @Valid AddMailFormat format) {
        String host = format.getHost();
        Integer port = format.getPort();
        String username = format.getUsername();
        String password = format.getPassword();
        String receiver = format.getTestMailAddress();
        if (host == null || port == null || username == null ||
                password == null || receiver == null) {
            throw new WebErrorException("邮件信息不完整");
        }

        if (! mailService.testMail(host, port, username, password, receiver)) {
            throw new WebErrorException("邮件发送失败");
        }
        return new ResponseEntity("邮件发送成功，请查看收件箱");
    }

    @PostMapping("/install")
    public ResponseEntity install(@RequestBody @Valid AddSettingFormat format) {

        settingService.installWeb(format.getEmail(), format.getNickname(), format.getPassword(),
                format.getTitle());
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
