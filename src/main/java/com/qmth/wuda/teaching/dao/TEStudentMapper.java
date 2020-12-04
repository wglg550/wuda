package com.qmth.wuda.teaching.dao;

import com.qmth.wuda.teaching.entity.TEStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

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

    void deleteAll();
}
