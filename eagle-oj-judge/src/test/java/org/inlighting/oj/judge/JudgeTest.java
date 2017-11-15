package org.inlighting.oj.judge;

import org.inlighting.oj.judge.config.JudgerRequestBean;
import org.inlighting.oj.judge.config.JudgeResponseBean;
import org.junit.jupiter.api.BeforeEach;

class JudgeTest {

    private final int TIME_OUT = 5;

    private JudgerRequestBean requestBean;
    private JudgeResponseBean responseBean;

    @BeforeEach
    void init() {
        requestBean = new JudgerRequestBean();
        responseBean = new JudgeResponseBean();
    }

/*
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
*/




}
