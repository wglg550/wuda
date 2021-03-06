package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.entity.TEAnswer;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEAnswerService extends IService<TEAnswer> {

    void deleteAll();

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
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param courseCode
     * @param dimension
     * @return
     */
    BigDecimal calculateCollegeAvgScoreByDimension(Long schoolId, Long examId, Long collegeId, String courseCode, String dimension);
}
