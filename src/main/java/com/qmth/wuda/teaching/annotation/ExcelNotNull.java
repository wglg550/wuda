package com.qmth.wuda.teaching.annotation;

import java.lang.annotation.*;

/**
 * @Description: excel字段不为空注解
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/7/19
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelNotNull {

}
