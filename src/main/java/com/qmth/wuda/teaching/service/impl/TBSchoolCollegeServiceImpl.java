package com.qmth.wuda.teaching.service.impl;

import com.qmth.wuda.teaching.entity.TBSchoolCollege;
import com.qmth.wuda.teaching.dao.TBSchoolCollegeMapper;
import com.qmth.wuda.teaching.service.TBSchoolCollegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 学院信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TBSchoolCollegeServiceImpl extends ServiceImpl<TBSchoolCollegeMapper, TBSchoolCollege> implements TBSchoolCollegeService {

    @Resource
    TBSchoolCollegeMapper tbSchoolCollegeMapper;

    @Override
    public void deleteAll() {
        tbSchoolCollegeMapper.deleteAll();
    }
}
