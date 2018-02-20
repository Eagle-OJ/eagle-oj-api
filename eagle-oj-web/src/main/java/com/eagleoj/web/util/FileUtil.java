package com.eagleoj.web.util;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.eagleoj.judge.LanguageEnum;
import com.eagleoj.web.setting.SettingEnum;
import com.eagleoj.web.setting.SettingService;
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

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private OSSClient ossClient;

    private SettingService settingService;

    public FileUtil(SettingService settingService) {
        this.settingService = settingService;
    }

    public boolean test(String accessKey, String secretKey, String endPoint, String bucket) {
        try {
            ClientConfiguration config = new ClientConfiguration();
            config.setConnectionTimeout(1000);
            config.setMaxErrorRetry(1);
            OSSClient tempClient = new OSSClient(endPoint, accessKey, secretKey);
            String s = "hello world";
            final String key = "eagle-oj-test";
            tempClient.putObject(bucket, key, new ByteArrayInputStream(s.getBytes()));
            tempClient.deleteObject(bucket, key);
            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    public String uploadCode(LanguageEnum lang, String code) {
        try {
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

            getOSSClient().putObject(settingService.getSetting(SettingEnum.OSS_BUCKET), filePath, is, metadata);
            return "/"+filePath;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    public String uploadAvatar(InputStream is, String postfix) {
        try {
            String file = UUID.randomUUID().toString()+"."+postfix;
            String filePath = generateFilePath(file);
            getOSSClient().putObject(settingService.getSetting(SettingEnum.OSS_BUCKET), filePath, is);
            return "/"+filePath;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
    }

    private OSSClient getOSSClient() {
        if (ossClient == null) {
            ossClient = new OSSClient(
                    settingService.getSetting(SettingEnum.OSS_END_POINT),
                    settingService.getSetting(SettingEnum.OSS_ACCESS_KEY),
                    settingService.getSetting(SettingEnum.OSS_SECRET_KEY));
            return ossClient;
        } else {
            return ossClient;
        }
    }

    public void refresh() {
        this.ossClient = null;
    }

    private String generateFilePath(String fileName) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DATE)+"/"+fileName;
    }
}
