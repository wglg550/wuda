package com.qmth.wuda.teaching.exception;

import com.qmth.wuda.teaching.enums.ExceptionResultEnum;

import java.io.Serializable;

/**
 * @Description: 自定义异常处理
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2019/10/11
 */
public class BusinessException extends RuntimeException implements Serializable {

    private int statusCode;
    private int code;  //错误码
    private String message;

    public BusinessException() {

    }

    public BusinessException(ExceptionResultEnum exceptionResultEnum) {
        super(exceptionResultEnum.getMessage());
        this.statusCode = exceptionResultEnum.getStatusCode();
        this.code = exceptionResultEnum.getCode();
        this.message = exceptionResultEnum.getMessage();
    }

    public BusinessException(int statusCode, int code, String message) {
        super(message);
        this.statusCode = statusCode;
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        super(message);
        this.statusCode = ExceptionResultEnum.ERROR.getStatusCode();
        this.code = ExceptionResultEnum.ERROR.getCode();
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
