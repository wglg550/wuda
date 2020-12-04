package com.qmth.wuda.teaching.exception;

import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.enums.ExceptionResultEnum;
import com.qmth.wuda.teaching.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * @Description: 全局异常处理
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2019/10/11
 */
@ControllerAdvice
public class GlobalDefultExceptionHandler {
    private final static Logger log = LoggerFactory.getLogger(GlobalDefultExceptionHandler.class);

    /**
     * excetion异常处理
     *
     * @param e
     * @param response
     * @param <T>
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public <T> Result defultExcepitonHandler(Exception e, HttpServletResponse response) {
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            response.setStatus(businessException.getCode());
            return ResultUtil.error(businessException.getStatusCode(), businessException.getMessage());
        } else if (e instanceof IllegalArgumentException) {
            response.setStatus(ExceptionResultEnum.EXCEPTION_ERROR.getCode());
            if (e.getMessage().contains("No enum constant Platform")) {
                return ResultUtil.error(ExceptionResultEnum.EXCEPTION_ERROR.getStatusCode(), "暂不支持此平台");
            }
        }
        response.setStatus(ExceptionResultEnum.EXCEPTION_ERROR.getCode());
        //Exception错误
        log.error("Exception 请求出错", e);
        return ResultUtil.error(ExceptionResultEnum.EXCEPTION_ERROR.getStatusCode(), e.getMessage());
    }

    /**
     * sql异常处理
     *
     * @param e
     * @param response
     * @param <T>
     * @return
     */
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public <T> Result sqlExceptionHandler(SQLException e, HttpServletResponse response) {
        response.setStatus(ExceptionResultEnum.SQL_ERROR.getCode());
        log.error("SQLException 请求出错", e);
        return ResultUtil.error(ExceptionResultEnum.SQL_ERROR.getStatusCode(), e.getMessage());
    }

    /**
     * 反射异常处理
     *
     * @param e
     * @param response
     * @param <T>
     * @return
     */
    @ExceptionHandler(InvocationTargetException.class)
    @ResponseBody
    public <T> Result invocationTargetExceptionHandler(InvocationTargetException e, HttpServletResponse response) {
        response.setStatus(ExceptionResultEnum.INVOCATIONTARGET_ERROR.getCode());
        log.error("invocationTargetExceptionHandler 请求出错", e);
        return ResultUtil.error(ExceptionResultEnum.INVOCATIONTARGET_ERROR.getStatusCode(), ExceptionResultEnum.INVOCATIONTARGET_ERROR.name() + e.getTargetException());
    }
}
