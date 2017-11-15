package org.inlighting.oj.judge;

import org.inlighting.oj.judge.config.JudgerRequestBean;
import org.inlighting.oj.judge.config.JudgeResponseBean;
import org.inlighting.oj.judge.request.RequestController;

public class JudgeController {
    public static JudgeResponseBean judge(String judgerUrl, JudgerRequestBean requestBean) {
        return RequestController.judge(judgerUrl, requestBean);
    }
}
