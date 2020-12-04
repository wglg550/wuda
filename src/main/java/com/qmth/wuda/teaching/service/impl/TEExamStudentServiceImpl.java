package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TEExamStudent;
import com.qmth.wuda.teaching.dao.TEExamStudentMapper;
import com.qmth.wuda.teaching.service.TEExamStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 考生信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TEExamStudentServiceImpl extends ServiceImpl<TEExamStudentMapper, TEExamStudent> implements TEExamStudentService {

    @Resource
    TEExamStudentMapper teExamStudentMapper;

    @Override
    public void deleteAll() {
        teExamStudentMapper.deleteAll();
    }
}
