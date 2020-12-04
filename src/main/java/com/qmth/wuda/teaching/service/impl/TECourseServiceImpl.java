package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TECourse;
import com.qmth.wuda.teaching.dao.TECourseMapper;
import com.qmth.wuda.teaching.service.TECourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 科目信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TECourseServiceImpl extends ServiceImpl<TECourseMapper, TECourse> implements TECourseService {

    @Resource
    TECourseMapper teCourseMapper;

    @Override
    public void deleteAll() {
        teCourseMapper.deleteAll();
    }
}
