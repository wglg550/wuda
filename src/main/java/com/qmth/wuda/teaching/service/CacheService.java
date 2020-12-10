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
     * @param schoolId
     * @param collegeId
     * @param studentNo
     * @return
     */
    PersonalReportBean addPersonalReport(Long schoolId, Long collegeId, String studentNo);

    /**
     * 删除个人报告
     *
     * @param schoolId
     * @param collegeId
     * @param studentNo
     */
    void removePersonalReport(Long schoolId, Long collegeId, String studentNo);
}
