package org.inlighting.oj.judge.request;

@Deprecated
abstract class RequestBody implements RequestBase {

    /**
     * The source code.
     */
    protected String SOURCE_CODE;

    /**
     * 程序模拟输入
     */
    protected String INPUTS[];

    /**
     * 程序输出
     */
    protected String OUTPUTS[];

    /**
     * The language id.<br/>
     * Notice: Different api have different language_id.
     */
    protected int LANGUAGE_ID;

    /**
     * 是否等待，如果等待则请求同步。
     */
    protected boolean WAIT = true;

    /**
     * 程序运行错误
     */
    protected String STD_ERROR;

    /**
     * KB
     */
    protected int MEMORY_LIMIT;

    /**
     * second
     */
    protected int TIME_LIMIT;
}
