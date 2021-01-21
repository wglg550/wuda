package com.qmth.wuda.teaching.templete.service;

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
     * @param id
     * @param cloudExamCode
     * @return
     */
    List<Map> yyjSourceDataAnalysis(Long id, String cloudExamCode) throws IOException;
}
