package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.TBDimension;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 维度信息表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TBDimensionMapper extends BaseMapper<TBDimension> {

    /**
     * 根据模块id删除维度
     *
     * @param moduleIds
     */
    void deleteAll(@Param("moduleIds") Set<Long> moduleIds);

    /**
     * 根据模块id和科目编码查找维度信息
     *
     * @param mouduleId
     * @param courseCode
     * @return
     */
    List<TBDimension> findByModuleIdAndCourseCode(@Param("mouduleId") Long mouduleId, @Param("courseCode") String courseCode);
}
