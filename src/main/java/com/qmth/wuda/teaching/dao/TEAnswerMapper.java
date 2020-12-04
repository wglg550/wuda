package com.qmth.wuda.teaching.dao;

import com.qmth.wuda.teaching.entity.TEAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TEAnswerMapper extends BaseMapper<TEAnswer> {

    void deleteAll();
}
