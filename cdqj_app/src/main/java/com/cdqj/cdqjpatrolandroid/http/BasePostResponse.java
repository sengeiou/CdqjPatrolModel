package com.cdqj.cdqjpatrolandroid.http;

/**
 * Created by lyf on 2018/9/7 14:31
 *
 * @author lyf
 * desc：保存数据后返回的基类（保存通用post方法：如保存状态、上传数据等）
 */
public class BasePostResponse<T> {

    private String code;

    private String msg;

    private boolean success;

    private T obj;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "BasePostResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", obj=" + obj +
                '}';
    }
}