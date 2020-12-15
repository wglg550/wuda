package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TEStudentMapper;
import com.qmth.wuda.teaching.entity.TEStudent;
import com.qmth.wuda.teaching.service.TEStudentService;
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

    /**
     * 根据学号(身份证号)获取学生信息
     *
     * @param studentNo
     * @return
     */
    @Override
    public TEStudent findByStudentNo(String studentNo) {
        QueryWrapper<TEStudent> teStudentQueryWrapper = new QueryWrapper<>();
        teStudentQueryWrapper.lambda().eq(TEStudent::getIdentity, studentNo);
        return this.getOne(teStudentQueryWrapper);
    }
}
