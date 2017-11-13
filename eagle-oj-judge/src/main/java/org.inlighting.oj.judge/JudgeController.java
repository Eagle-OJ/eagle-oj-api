package org.inlighting.oj.judge;

import org.inlighting.oj.judge.bean.JudgeServerEnum;
import org.inlighting.oj.judge.bean.StdRequestBean;
import org.inlighting.oj.judge.bean.StdResponseBean;
import org.inlighting.oj.judge.request.RequestController;

public class JudgeController {
    public static StdResponseBean judge(String judgerUrl, StdRequestBean requestBean) {
        return RequestController.judge(judgerUrl, requestBean);
    }
}
