package com.qmth.wuda.teaching.util;

import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.enums.ExceptionResultEnum;
import com.qmth.wuda.teaching.exception.BusinessException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Description: http工具
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/4/10
 */
public class ServletUtil {

    /**
     * 输出错误
     *
     * @param code
     * @param message
     * @throws IOException
     */
    public static void responseError(int code, String message) throws IOException {
        Result result = ResultUtil.error(code, message);
        String json = JacksonUtil.parseJson(result);
        getResponse().getWriter().print(json);
    }

    /**
     * 获取请求的Authorization
     *
     * @return
     */
    public static String getRequestAuthorization() {
        HttpServletRequest request = getRequest();
        // 从header中获取authorization
        String authorization = request.getHeader(SystemConstant.HEADER_AUTHORIZATION);
        // 如果header中不存在authorization，则从参数中获取authorization
        if (Objects.isNull(authorization)) {
            authorization = request.getParameter(SystemConstant.HEADER_AUTHORIZATION);
            if (Objects.isNull(authorization)) {
                throw new BusinessException(ExceptionResultEnum.AUTHORIZATION_INVALID);
            }
        }
        return authorization;
    }

    /**
     * 获取请求的md5
     *
     * @return
     */
    public static String getRequestMd5() {
        return getRequest().getHeader(SystemConstant.MD5);
    }

    /**
     * 获取请求的path
     *
     * @return
     */
    public static String getRequestPath() {
        return getRequest().getHeader(SystemConstant.PATH);
    }

    /**
     * 获取HttpServletRequest
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    /**
     * 获取HttpServletResponse
     *
     * @return
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getResponse();
    }
}
