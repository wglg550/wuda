package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.TEAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Mapper
public interface TEAnswerMapper extends BaseMapper<TEAnswer> {

    void deleteAll();

    /**
     * 根据考试记录id查找答题记录
     *
     * @param examRecordId
     * @return
     */
    List<TEAnswer> findByExamRecordId(@Param("examRecordId") Long examRecordId);
}
