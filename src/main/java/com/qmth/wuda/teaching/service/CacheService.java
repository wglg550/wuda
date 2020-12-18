package com.qmth.wuda.teaching.service;

import com.qmth.wuda.teaching.bean.report.PersonalReportBean;

/**
 * @Description: ehcache 服务类
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/6/27
 */
public interface CacheService {

    /**
     * 生成个人报告
     *
     * @param examId
     * @param examStudentId
     * @param courseCode
     * @return
     */
    PersonalReportBean addPersonalReport(Long examId, Long examStudentId, String courseCode);

    /**
     * 删除个人报告
     *
     * @param examId
     * @param examStudentId
     * @param courseCode
     */
    void removePersonalReport(Long examId, Long examStudentId, String courseCode);
}
