package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TBSchoolCollegeMapper;
import com.qmth.wuda.teaching.entity.TBSchoolCollege;
import com.qmth.wuda.teaching.service.TBSchoolCollegeService;
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

    /**
     * 根据学校id删除学院
     *
     * @param schoolId
     */
    @Override
    public void deleteAll(Long schoolId) {
        tbSchoolCollegeMapper.deleteAll(schoolId);
    }
}
