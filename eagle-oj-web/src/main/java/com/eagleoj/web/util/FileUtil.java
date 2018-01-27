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

    public FileUtil(SettingService settingService) {
        OSSConfig ossConfig = settingService.getSystemConfig().getOssConfig();
        ossClient = new OSSClient(ossConfig.getEND_POINT(),
                ossConfig.getACCESS_KEY(), ossConfig.getSECRET_KEY());
        BUCKET = ossConfig.getBUCKET();
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
            ossClient.putObject(BUCKET, filePath, is, metadata);
            return "/"+filePath;
        } catch (Exception e) {

            return null;
        }
    }

    public String uploadAvatar(InputStream is, String postfix) {
        String file = UUID.randomUUID().toString()+"."+postfix;
        String filePath = generateFilePath(file);
        try {
            ossClient.putObject(BUCKET, filePath, is);
            return "/"+filePath;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private String generateFilePath(String fileName) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DATE)+"/"+fileName;
    }
}
