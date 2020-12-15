package com.qmth.wuda.teaching.dao;

import com.qmth.wuda.teaching.entity.TEExam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 根据考试id或考试编码删除考试
     *
     * @param examId
     * @param examCode
     */
    void deleteAll(@Param("examId") Long examId, @Param("examCode") String examCode);
}
