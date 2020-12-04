package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TECourse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 科目信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TECourseService extends IService<TECourse> {

    void deleteAll();
}
