package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TEStudent;
import com.qmth.wuda.teaching.dao.TEStudentMapper;
import com.qmth.wuda.teaching.service.TEStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 学生信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TEStudentServiceImpl extends ServiceImpl<TEStudentMapper, TEStudent> implements TEStudentService {

    @Resource
    TEStudentMapper teStudentMapper;

    @Override
    public void deleteAll() {
        teStudentMapper.deleteAll();
    }
}
