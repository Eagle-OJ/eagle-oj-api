package org.inlighting.oj.web.judger;

import org.inlighting.oj.judge.JudgeController;
import org.inlighting.oj.judge.config.CodeLanguageEnum;
import org.inlighting.oj.judge.config.JudgerRequestBean;
import org.inlighting.oj.judge.config.JudgeResponseBean;
import org.inlighting.oj.judge.config.LanguageConfiguration;
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
public class Judger {

    public Map<CodeLanguageEnum, Map<String, Integer>> getConfiguration() {
        return LanguageConfiguration.getLanguageMap();
    }

    public Map<String, Integer> getJudgerStatus() {
        // todo 返回判卷机状态，队列状态等等
        return null;
    }

}
