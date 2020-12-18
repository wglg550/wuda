package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.TBModule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

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

    /**
     * 根据科目编码删除
     *
     * @param courseCode
     * @param moduleNames
     */
    void deleteAll(@Param("courseCode") String courseCode, @Param("moduleNames") Set<String> moduleNames);

    /**
     * 根据科目编码获取模块
     *
     * @param courseCode
     * @return
     */
    List<TBModule> findByCourseCode(@Param("courseCode") String courseCode);
}
