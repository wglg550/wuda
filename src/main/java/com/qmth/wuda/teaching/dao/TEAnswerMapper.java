package com.qmth.wuda.teaching.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qmth.wuda.teaching.entity.TEAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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
     * @param examId
     * @param paperId
     * @param collegeId
     * @param courseCode
     * @param dimensions
     * @param moduleCode
     * @return
     */
    BigDecimal calculateCollegeAvgScoreByDimension(@Param("examId") Long examId, @Param("paperId") Long paperId, @Param("collegeId") Long collegeId, @Param("courseCode") String courseCode, @Param("dimensions") Set<String> dimensions, @Param("moduleCode") String moduleCode);

    /**
     * 根据维度求学生该维度的平均值
     *
     * @param examId
     * @param paperId
     * @param collegeId
     * @param studentCode
     * @param courseCode
     * @param dimensions
     * @param moduleCode
     * @return
     */
    BigDecimal calculateStudentAvgScoreByDimension(@Param("examId") Long examId, @Param("paperId") Long paperId, @Param("collegeId") Long collegeId, @Param("studentCode") String studentCode, @Param("courseCode") String courseCode, @Param("dimensions") Set<String> dimensions, @Param("moduleCode") String moduleCode);
}
