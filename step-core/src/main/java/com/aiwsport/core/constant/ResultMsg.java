package com.aiwsport.core.constant;

/**
 * 请求公共上下文对象
 * @author yangjian9
 *
 */
public class ResultMsg<T> {

    private boolean isSuccess = true;
    private Integer code = 200;
    private String message;
    private T data;

    public ResultMsg(boolean isSuccess, Integer code, String message, T data){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultMsg(boolean isSuccess, Integer code, String message){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public ResultMsg(String message, T data){
        this.data = data;
        this.message = message;
        data.getClass();
    }

    public ResultMsg(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
