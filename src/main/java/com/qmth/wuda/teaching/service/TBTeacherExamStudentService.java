package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.entity.TBTeacherExamStudent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 教师考生关系表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBTeacherExamStudentService extends IService<TBTeacherExamStudent> {

    void deleteAll();
}
