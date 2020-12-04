package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TBTeacher;
import com.qmth.wuda.teaching.dao.TBTeacherMapper;
import com.qmth.wuda.teaching.service.TBTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 教师信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TBTeacherServiceImpl extends ServiceImpl<TBTeacherMapper, TBTeacher> implements TBTeacherService {

    @Resource
    TBTeacherMapper tbTeacherMapper;

    @Override
    public void deleteAll() {
        tbTeacherMapper.deleteAll();
    }
}
