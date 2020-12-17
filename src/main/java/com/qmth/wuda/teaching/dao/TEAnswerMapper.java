package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.TEAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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

    /**
     * 根据考试记录id删除答案
     *
     * @param examRecordId
     */
    void deleteAll(@Param("examRecordId") Long examRecordId);

    /**
     * 根据考试记录id查找答题记录
     *
     * @param examRecordId
     * @return
     */
    List<TEAnswer> findByExamRecordId(@Param("examRecordId") Long examRecordId);

    /**
     * 根据维度求学院该维度的平均值
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param courseCode
     * @param dimension
     * @return
     */
    BigDecimal calculateCollegeAvgScoreByDimension(@Param("schoolId") Long schoolId, @Param("examId") Long examId, @Param("collegeId") Long collegeId, @Param("courseCode") String courseCode, @Param("dimension") String dimension);
}
