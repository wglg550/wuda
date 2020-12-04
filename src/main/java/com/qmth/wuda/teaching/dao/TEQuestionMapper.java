package com.qmth.wuda.teaching.dao;

import com.qmth.wuda.teaching.entity.TEQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 试卷结构题型说明 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TEQuestionMapper extends BaseMapper<TEQuestion> {

    void deleteAll();
}
