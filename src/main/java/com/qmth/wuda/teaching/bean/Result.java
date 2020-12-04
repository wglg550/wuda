package com.qmth.wuda.teaching.bean;

import java.io.Serializable;

/**
 * @Description: 自定义处理消息
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2019/10/11
 */
public class Result implements Serializable {
    private int code;
    private String message;
    private Object data;

    public Result() {

    }

    public Result(String message) {
        this.message = message;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
