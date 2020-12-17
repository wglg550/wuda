package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TBTeacherExamStudent;

/**
 * <p>
 * 教师考生关系表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBTeacherExamStudentService extends IService<TBTeacherExamStudent> {

    /**
     * 根据老师id删除老师学生数据
     *
     * @param teacherId
     */
    void deleteAll(Long teacherId);
}
