package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.Sequence;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: 序列 mapper
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/6/15
 */
@Mapper
public interface SequenceMapper extends BaseMapper<Sequence> {

    /**
     * 获取序列号
     *
     * @return
     */
    Integer selectNextVal();
}
