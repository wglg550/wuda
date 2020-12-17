package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.TBTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 教师信息表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TBTeacherMapper extends BaseMapper<TBTeacher> {

    /**
     * 根据学校id删除老师
     *
     * @param schoolId
     */
    void deleteAll(@Param("schoolId") Long schoolId);
}
