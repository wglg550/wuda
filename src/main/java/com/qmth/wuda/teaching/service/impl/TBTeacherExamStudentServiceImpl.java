package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TBTeacherExamStudent;
import com.qmth.wuda.teaching.dao.TBTeacherExamStudentMapper;
import com.qmth.wuda.teaching.service.TBTeacherExamStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Override
    public void deleteAll() {
        tbTeacherExamStudentMapper.deleteAll();
    }
}
