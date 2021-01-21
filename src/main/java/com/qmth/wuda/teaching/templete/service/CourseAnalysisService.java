package com.qmth.wuda.teaching.templete.service;

import com.qmth.wuda.teaching.bean.YyjSourceDataBean;
import com.qmth.wuda.teaching.entity.TBSchool;
import com.qmth.wuda.teaching.templete.CourseAnalysisTemplete;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 公用分析service
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/1/21
 */
public interface CourseAnalysisService {

    /**
     * 抽取云阅卷源数据
     *
     * @param examId
     * @param examCode
     * @return
     */
    List<Map> yyjSourceDataAnalysis(Long examId, String examCode) throws IOException;

    /**
     * 获取学校信息
     */
    TBSchool getSchoolData();

    /**
     * 云阅卷数据解析
     *
     * @param examId
     * @param tbSchool
     * @param yyjSourceDataBean
     * @param courseAnalysisTemplete
     * @return
     */
    boolean yyjResolveData(Long examId, TBSchool tbSchool, YyjSourceDataBean yyjSourceDataBean, CourseAnalysisTemplete courseAnalysisTemplete);

    /**
     * 保存数据库
     *
     * @param examId
     * @param tbSchool
     * @param courseAnalysisTemplete
     */
    void saveYyjSourceDataForDb(Long examId, TBSchool tbSchool, CourseAnalysisTemplete courseAnalysisTemplete);
}
