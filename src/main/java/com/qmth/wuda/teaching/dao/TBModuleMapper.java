package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.TBModule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 模块信息表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TBModuleMapper extends BaseMapper<TBModule> {

    void deleteAll();

    /**
     * 根据学校id获取模块
     *
     * @param schoolId
     * @return
     */
    List<TBModule> findBySchoolId(@Param("schoolId") Long schoolId);
}
