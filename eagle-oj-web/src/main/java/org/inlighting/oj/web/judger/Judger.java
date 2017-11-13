package org.inlighting.oj.web.judger;

import org.inlighting.oj.judge.JudgeController;
import org.inlighting.oj.judge.bean.StdRequestBean;
import org.inlighting.oj.judge.bean.StdResponseBean;
import org.inlighting.oj.judge.config.LanguageConfiguration;
import org.inlighting.oj.judge.config.LanguageEnum;
import org.inlighting.oj.web.service.AttachmentService;
import org.inlighting.oj.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author Smith
 **/
@Component
public class Judger {

    private FileUtil fileUtil;

    private AttachmentService attachmentService;

    @Value("${eagle-oj.judge.url}")
    private String JUDGE_URL;

    @Autowired
    public void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Autowired
    public void setAttachmentService(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    public Map<LanguageEnum, Map<String, Integer>> getConfiguration() {
        return LanguageConfiguration.getLanguageMap();
    }

    @Async
    public Future<StdResponseBean> getResult(StdRequestBean requestBean) {
        return new AsyncResult<>(JudgeController.judge(JUDGE_URL, requestBean));
    }

    @Async
    public Future<Integer> uploadCode(int owner, LanguageEnum languageEnum, String code) {
        String filePath = fileUtil.uploadCode(languageEnum, code);
        int aid = attachmentService.add(owner, filePath, System.currentTimeMillis());
        return new AsyncResult<>(aid);
    }
}
