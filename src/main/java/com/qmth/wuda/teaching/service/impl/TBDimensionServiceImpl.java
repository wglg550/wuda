package com.qmth.wuda.teaching.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qmth.wuda.teaching.dao.TBDimensionMapper;
import com.qmth.wuda.teaching.entity.TBDimension;
import com.qmth.wuda.teaching.service.TBDimensionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 维度信息表 服务实现类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Service
public class TBDimensionServiceImpl extends ServiceImpl<TBDimensionMapper, TBDimension> implements TBDimensionService {

    @Resource
    TBDimensionMapper tbDimensionMapper;

    /**
     * 根据模块id删除维度
     *
     * @param moduleIds
     */
    @Override
    public void deleteAll(Set<Long> moduleIds) {
        tbDimensionMapper.deleteAll(moduleIds);
    }

    /**
     * 根据模块id和科目编码查找维度信息
     *
     * @param mouduleId
     * @param courseCode
     * @return
     */
    @Override
    @Cacheable(value = "dimension_cache", key = "#mouduleId + '-' + #courseCode", unless = "#result == null")
    public List<TBDimension> findByModuleIdAndCourseCode(Long mouduleId, String courseCode) {
        return tbDimensionMapper.findByModuleIdAndCourseCode(mouduleId, courseCode);
    }
}
