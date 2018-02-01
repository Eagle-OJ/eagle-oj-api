package com.eagleoj.web.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.web.config.OSSConfig;
import com.eagleoj.web.service.SettingService;
import com.eagleoj.web.config.OSSConfig;
import com.eagleoj.web.service.SettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author Smith
 **/
@Component
public class FileUtil {

    private Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    private String BUCKET;

    private OSSClient ossClient;

    private SettingService settingService;

    public FileUtil(SettingService settingService) {
        this.settingService = settingService;
    }

    public String uploadCode(LanguageEnum lang, String code) {
        InputStream is = new ByteArrayInputStream(code.getBytes());
        StringBuilder fileName = new StringBuilder(UUID.randomUUID().toString());
        switch (lang) {
            case JAVA8:
                fileName.append(".java");
                break;
            case C:
                fileName.append(".c");
                break;
            case CPP:
                fileName.append(".cpp");
                break;
            default:
                fileName.append(".py");
                break;
        }
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("text/plain");
        String filePath = generateFilePath(fileName.toString());
        try {
            getOSSClient().putObject(BUCKET, filePath, is, metadata);
            return "/"+filePath;
        } catch (Exception e) {

            return null;
        }
    }

    public String uploadAvatar(InputStream is, String postfix) throws Exception {
        String file = UUID.randomUUID().toString()+"."+postfix;
        String filePath = generateFilePath(file);
        getOSSClient().putObject(BUCKET, filePath, is);
        return "/"+filePath;
    }

    private OSSClient getOSSClient() {
        if (ossClient == null) {
            OSSConfig ossConfig = settingService.getSystemConfig().getOssConfig();
            BUCKET = ossConfig.getBUCKET();
            ossClient = new OSSClient(ossConfig.getEND_POINT(),
                    ossConfig.getACCESS_KEY(), ossConfig.getSECRET_KEY());
            return ossClient;
        } else {
            return ossClient;
        }
    }

    private String generateFilePath(String fileName) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DATE)+"/"+fileName;
    }
}
