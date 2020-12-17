package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TBTeacherExamStudentMapper;
import com.qmth.wuda.teaching.entity.TBTeacherExamStudent;
import com.qmth.wuda.teaching.service.TBTeacherExamStudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 教师考生关系表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TBTeacherExamStudentServiceImpl extends ServiceImpl<TBTeacherExamStudentMapper, TBTeacherExamStudent> implements TBTeacherExamStudentService {

    @Resource
    TBTeacherExamStudentMapper tbTeacherExamStudentMapper;

    /**
     * 根据老师id删除老师学生数据
     *
     * @param teacherId
     */
    @Override
    public void deleteAll(Long teacherId) {
        tbTeacherExamStudentMapper.deleteAll(teacherId);
    }
}
