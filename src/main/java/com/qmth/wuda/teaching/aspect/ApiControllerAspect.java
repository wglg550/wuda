package com.qmth.wuda.teaching.aspect;

import com.qmth.wuda.teaching.config.DictionaryConfig;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.util.JacksonUtil;
import com.qmth.wuda.teaching.util.ResultUtil;
import com.qmth.wuda.teaching.util.ServletUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Description: api aspect
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/5/12
 */
@Aspect
@Component
public class ApiControllerAspect {
    private final static Logger log = LoggerFactory.getLogger(ApiControllerAspect.class);

    @Resource
    DictionaryConfig dictionaryConfig;

    /**
     * api切入点
     */
    @Pointcut("execution(public * com.qmth.wuda.teaching.api.*.*(..))")
    public void apiAspect() {
    }

    /**
     * 后台环绕切入
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "apiAspect()")
    public Object aroundApiPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            long start = System.currentTimeMillis();
            boolean open = dictionaryConfig.sysDomain().isOpen();
            if (!open) {
                throw new BusinessException("api暂不开放调用!");
            }
            MethodSignature msig = (MethodSignature) joinPoint.getSignature();
            String className = msig.getDeclaringTypeName();
            String methodName = msig.getName();
            Object[] args = joinPoint.getArgs();
            String[] paramsName = msig.getParameterNames();
            HttpServletRequest request = ServletUtil.getRequest();
            log.info("============请求地址========:{}", request.getServletPath());
            log.info("============类=============:{}", className);
            log.info("============方法===========:{}", methodName);
            if (Objects.nonNull(args) && args.length > 0) {
                boolean jsonOut = true;
                for (Object o : args) {
                    if (o instanceof CommonsMultipartFile) {
                        jsonOut = false;
                        break;
                    }
                }
                if (jsonOut) {
                    log.info("============参数key:{},参数value===========:{}", JacksonUtil.parseJson(paramsName), JacksonUtil.parseJson(args));
                } else {
                    for (int i = 0; i < args.length; i++) {
                        log.info("============参数key:{},参数value===========:{}", paramsName[i], args[i]);
                    }
                }
            }
            Object proceed = joinPoint.proceed();
            long end = System.currentTimeMillis();
            log.info("============耗时============:{}秒", (end - start) / 1000);
            return proceed;
        } catch (Exception e) {
            log.error("请求出错", e);
            if (e instanceof BusinessException) {
                return ResultUtil.error((BusinessException) e, e.getMessage());
            } else {
                return ResultUtil.error(e.getMessage());
            }
        }
    }
}
