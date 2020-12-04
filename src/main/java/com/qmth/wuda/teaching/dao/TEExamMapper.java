package com.qmth.wuda.teaching.dao;

import com.qmth.wuda.teaching.entity.TEExam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 考试批次表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TEExamMapper extends BaseMapper<TEExam> {

    void deleteAll();
}
