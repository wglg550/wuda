package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TBTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 教师信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBTeacherService extends IService<TBTeacher> {

    void deleteAll();
}
