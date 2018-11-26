package com.aiwsport.core;

import org.apache.commons.httpclient.HttpStatus;

public class StepServerExceptionFactor {

    public static final StepServerExceptionFactor DEFAULT = new StepServerExceptionFactor(
            HttpStatus.SC_INTERNAL_SERVER_ERROR, 10001,
            "system error", "系统错误");
    public static final StepServerExceptionFactor INTERNAL_ERROR = new StepServerExceptionFactor(
            HttpStatus.SC_INTERNAL_SERVER_ERROR, 10002,
            "internal server error", "内部错误");
    public static final StepServerExceptionFactor BIND_ERROR = new StepServerExceptionFactor(
            HttpStatus.SC_FORBIDDEN, 10003,
            "bind param error", "系统参数绑定异常");
    public static final StepServerExceptionFactor PATTERN_ERROR = new StepServerExceptionFactor(
            HttpStatus.SC_FORBIDDEN, 10004,
            "pattern error", "数据格式异常");

    public static final StepServerExceptionFactor PUSH_CONN_INTERRUPT = new StepServerExceptionFactor(
            HttpStatus.SC_BAD_REQUEST, 20001,
            "connection is interrupted", "客户端连接中断");
    public static final StepServerExceptionFactor PROTOCOL_ERROR = new StepServerExceptionFactor(
            HttpStatus.SC_BAD_REQUEST, 20002,
            "unsupport protocol", "不支持的协议");
    public static final StepServerExceptionFactor MISSING_PARAM = new StepServerExceptionFactor(
            HttpStatus.SC_BAD_REQUEST, 20003,
            "missing param", "缺少参数");
    public static final StepServerExceptionFactor CONFIG_PARAM_TYPE_MISMATCH = new StepServerExceptionFactor(
            HttpStatus.SC_BAD_REQUEST, 20010,
            "param type mismatch", "参数类型错误");

    public static final StepServerExceptionFactor SIGN_IS_ERROR = new StepServerExceptionFactor(
            HttpStatus.SC_BAD_REQUEST, 20010,
            "sign is error", "签名错误");

    public static final StepServerExceptionFactor CONFIG_ERROR = new StepServerExceptionFactor(
            HttpStatus.SC_FORBIDDEN, 50000,
            "param config error", "参数校验配置出错");
    public static final StepServerExceptionFactor PARAM_VERIFY_FAIL = new StepServerExceptionFactor(
            HttpStatus.SC_FORBIDDEN, 50001,
            "param verify is fail", "参数校验失败");

    private int httpStatus;
    private int errorCode;
    private String errorMsg;
    private String errorMsgCN;

    public StepServerExceptionFactor(int httpStatus, int errorCode, String errorMsg, String errorMsgCN) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorMsgCN = errorMsgCN;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsgCN() {
        return errorMsgCN;
    }

    public void setErrorMsgCN(String errorMsgCN) {
        this.errorMsgCN = errorMsgCN;
    }
}
