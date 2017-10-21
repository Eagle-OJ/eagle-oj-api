package org.inlighting.oj.judge;

import org.inlighting.oj.judge.bean.StdRequestBean;
import org.inlighting.oj.judge.bean.StdResponseBean;
import org.inlighting.oj.judge.request.RequestController;

public class JudgeController {
    public static StdResponseBean judge(StdRequestBean request) {
        return RequestController.judge(request);
    }
}
