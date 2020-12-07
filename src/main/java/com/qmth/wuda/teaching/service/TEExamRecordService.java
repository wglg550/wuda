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

    void deleteAll();

    /**
     * 获取学院分数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @return
     */
    SynthesisBean findByCollegeScore(Long schoolId, Long examId, Long collegeId);

    /**
     * 获取班级分数
     *
     * @param schoolId
     * @param examId
     * @param collegeId
     * @return
     */
    SynthesisBean findByClassScore(Long schoolId, Long examId, Long collegeId, String classNo);
}
