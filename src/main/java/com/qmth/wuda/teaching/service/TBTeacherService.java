package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TBTeacher;

/**
 * <p>
 * 教师信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBTeacherService extends IService<TBTeacher> {

    /**
     * 根据学校id删除老师
     *
     * @param schoolId
     */
    void deleteAll(Long schoolId);
}
