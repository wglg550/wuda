package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.TBLevel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 等级信息表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TBLevelMapper extends BaseMapper<TBLevel> {

    /**
     * 根据模块id删除等级
     *
     * @param moduleId
     */
    void deleteAll(@Param("moduleId") Long moduleId);
}
