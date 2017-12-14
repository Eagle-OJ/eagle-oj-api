package org.inlighting.oj.web.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import org.inlighting.oj.judge.LanguageEnum;
import org.springframework.beans.factory.annotation.Value;
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

    private static String BUCKET;

    private OSSClient ossClient;

    public FileUtil(@Value("${eagle-oj.oss.end-point}") String endPoint,
                    @Value("${eagle-oj.oss.access-key}") String key,
                    @Value("${eagle-oj.oss.access-secret}") String secret,
                    @Value("${eagle-oj.oss.bucket}") String bucket) {
        ossClient = new OSSClient(endPoint, key, secret);
        BUCKET = bucket;
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
            return null;
        }
    }

    private static String generateFilePath(String fileName) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DATE)+"/"+fileName;
    }
}
