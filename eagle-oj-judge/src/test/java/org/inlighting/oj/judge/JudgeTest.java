package org.inlighting.oj.judge;

import org.inlighting.oj.judge.bean.LanguageEnum;
import org.inlighting.oj.judge.bean.StdRequestBean;
import org.inlighting.oj.judge.bean.StdResponseBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

class JudgeTest {

    private final int TIME_OUT = 5;

    private StdRequestBean requestBean;
    private StdResponseBean responseBean;

    @BeforeEach
    void init() {
        requestBean = new StdRequestBean();
        responseBean = new StdResponseBean();
    }

    @Test
    void inputNoneSuccessJudge() {
        requestBean.setTestCaseNumber(1);
        requestBean.setLanguage(LanguageEnum.PYTHON36);
        requestBean.setSourceCode("print(\"hello\")");
        requestBean.setStdin(new String[]{null});
        requestBean.setExpectResult(new String[]{"hello\n"});
        Assertions.assertTimeout(Duration.ofSeconds(TIME_OUT), () -> {
            responseBean = JudgeController.judge(requestBean);
            Assertions.assertEquals(responseBean.isAccept(), true);
        });
    }

    @Test
    void inputOneCaseSuccessJudge() {
        requestBean.setTestCaseNumber(1);
        requestBean.setLanguage(LanguageEnum.PYTHON36);
        requestBean.setSourceCode("a = input()\n" +
                "if a == 'ok':\n" +
                "  print('ok')\n" +
                "else:\n" +
                "  print('no')");
        requestBean.setStdin(new String[]{"123"});
        requestBean.setExpectResult(new String[]{"no\n"});
        Assertions.assertTimeout(Duration.ofSeconds(TIME_OUT), () -> {
            responseBean = JudgeController.judge(requestBean);
            Assertions.assertAll(() -> {
                Assertions.assertEquals(responseBean.isAccept(), true);
            });
        });
    }

    @Test
    void inputTwoCaseSuccessJudge() {
        requestBean.setTestCaseNumber(2);
        requestBean.setLanguage(LanguageEnum.PYTHON36);
        requestBean.setSourceCode("a = input()\n" +
                "if a == 'ok':\n" +
                "  print('ok')\n" +
                "else:\n" +
                "  print('no')");
        requestBean.setStdin(new String[]{"ok", "no"});
        requestBean.setExpectResult(new String[]{"ok\n", "no\n"});
        Assertions.assertTimeout(Duration.ofSeconds(TIME_OUT), () -> {
            responseBean = JudgeController.judge(requestBean);
            Assertions.assertAll(() -> {
                Assertions.assertEquals(responseBean.isAccept(), true);
            });
        });
    }




}
