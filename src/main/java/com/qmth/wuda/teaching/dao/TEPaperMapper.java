package com.qmth.wuda.teaching.dao;

import com.qmth.wuda.teaching.entity.TEPaper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 试卷信息表 Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TEPaperMapper extends BaseMapper<TEPaper> {

    void deleteAll();
}
