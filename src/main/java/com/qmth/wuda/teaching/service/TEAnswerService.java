package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TEAnswer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEAnswerService extends IService<TEAnswer> {

    /**
     * 根据考试记录id删除答案
     *
     * @param examRecordId
     */
    void deleteAll(Long examRecordId);

    /**
     * 根据考试记录id查找答题记录
     *
     * @param examRecordId
     * @return
     */
    List<TEAnswer> findByExamRecordId(Long examRecordId);

    /**
     * 根据维度求学院该维度的平均值
     *
     * @param examId
     * @param collegeId
     * @param courseCode
     * @param dimensions
     * @param moduleCode
     * @return
     */
    BigDecimal calculateCollegeAvgScoreByDimension(Long examId, Long collegeId, String courseCode, Set<String> dimensions, String moduleCode);

    /**
     * 根据维度求学生该维度的平均值
     *
     * @param examId
     * @param collegeId
     * @param studentCode
     * @param courseCode
     * @param dimensions
     * @param moduleCode
     * @return
     */
    BigDecimal calculateStudentAvgScoreByDimension(Long examId, Long collegeId, String studentCode, String courseCode, Set<String> dimensions, String moduleCode);
}
