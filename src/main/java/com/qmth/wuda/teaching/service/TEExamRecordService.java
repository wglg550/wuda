package com.qmth.wuda.teaching.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qmth.wuda.teaching.bean.report.SynthesisBean;
import com.qmth.wuda.teaching.entity.TEExamRecord;

/**
 * <p>
 * 考试记录表 服务类
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
public interface TEExamRecordService extends IService<TEExamRecord> {

    /**
     * 根据考试id和考生id和试卷id删除考试记录
     *
     * @param examId
     * @param examStudentId
     * @param paperId
     */
    void deleteAll(Long examId, Long examStudentId, Long paperId);

    /**
     * 获取学院分数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param courseCode
     * @return
     */
    SynthesisBean findByCollegeScore(Long schoolId, Long examId, Long collegeId, String courseCode);

    /**
     * 获取班级分数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param courseCode
     * @return
     */
    SynthesisBean findByClassScore(Long schoolId, Long examId, Long collegeId, String classNo, String courseCode);

    /**
     * 获取分数比自己低的人数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @param examRecordId
     * @param courseCode
     * @return
     */
    Integer getLowScoreByMe(Long schoolId, Long examId, Long collegeId, Long examRecordId, String courseCode);
}
