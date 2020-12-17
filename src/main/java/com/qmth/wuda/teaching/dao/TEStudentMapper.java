package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.TEStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * <p>
 * 学生信息表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TEStudentMapper extends BaseMapper<TEStudent> {

    /**
     * 根据学校id和学号删除学生
     *
     * @param schoolId
     * @param studentCodes
     */
    void deleteAll(@Param("schoolId") Long schoolId, @Param("studentCodes") Set<String> studentCodes);
}
