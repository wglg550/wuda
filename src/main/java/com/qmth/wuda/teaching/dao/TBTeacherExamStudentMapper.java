package com.qmth.wuda.teaching.dao;

import com.qmth.wuda.teaching.entity.TBTeacherExamStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 教师考生关系表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TBTeacherExamStudentMapper extends BaseMapper<TBTeacherExamStudent> {

    void deleteAll();
}
