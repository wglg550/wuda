package com.qmth.wuda.teaching.util;

import cn.hutool.http.HttpStatus;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.enums.ExceptionResultEnum;
import com.qmth.wuda.teaching.exception.BusinessException;

/**
 * @Description: 自定义消息工具
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2019/10/11
 */
public class ResultUtil {

    public static Result success(Object object) {
        return new Result(ExceptionResultEnum.SUCCESS.getStatusCode(), ExceptionResultEnum.SUCCESS.getMessage(), object);
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(int code, String message) {
        return new Result(code, message);
    }

    public static Result error() {
        throw new BusinessException(ExceptionResultEnum.EXCEPTION_ERROR);
    }

    public static Result error(BusinessException e, String message) {
        throw new BusinessException(e.getStatusCode(), e.getCode(), message);
    }

    public static Result error(String message) {
        throw new BusinessException(ExceptionResultEnum.EXCEPTION_ERROR.getStatusCode(), ExceptionResultEnum.EXCEPTION_ERROR.getCode(), message);
    }

    public static Result ok(String message) {
        return new Result(HttpStatus.HTTP_OK, message);
    }

    public static Result ok(Object data) {
        Result r = new Result();
        r.setCode(HttpStatus.HTTP_OK);
        r.setData(data);
        r.setMessage(ExceptionResultEnum.SUCCESS.getMessage());
        return r;
    }

    public static Result ok(Object data, String message) {
        Result r = new Result();
        r.setCode(HttpStatus.HTTP_OK);
        r.setData(data);
        r.setMessage(message);
        return r;
    }

    public static Result ok() {
        Result r = new Result();
        r.setCode(HttpStatus.HTTP_OK);
        return r;
    }
}
