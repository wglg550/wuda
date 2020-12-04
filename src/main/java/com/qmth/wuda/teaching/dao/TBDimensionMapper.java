package com.qmth.wuda.teaching.dao;

import com.qmth.wuda.teaching.entity.TBDimension;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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
}
