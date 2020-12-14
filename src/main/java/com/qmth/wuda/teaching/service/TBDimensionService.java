package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TBDimension;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 维度信息表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TBDimensionService extends IService<TBDimension> {

    /**
     * 根据模块id删除维度
     *
     * @param moduleIds
     */
    void deleteAll(Set<Long> moduleIds);

    /**
     * 根据模块id和科目编码查找维度信息
     *
     * @param mouduleId
     * @param courseCode
     * @return
     */
    List<TBDimension> findByModuleIdAndCourseCode(Long mouduleId, String courseCode);
}
