package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.dto.ExamDto;
import com.qmth.wuda.teaching.entity.TEExam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据考试名称查询考试
     *
     * @param examName
     * @return
     */
    List<ExamDto> findByExamName(@Param("examName") String examName);
}
