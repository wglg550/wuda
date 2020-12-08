package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.TBDimension;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    void deleteAll();

    /**
     * 根据模块id和科目编码查找维度信息
     *
     * @param mouduleId
     * @param courseCode
     * @return
     */
    List<TBDimension> findByModuleIdAndCourseCode(@Param("mouduleId") Long mouduleId, @Param("courseCode") String courseCode);
}
