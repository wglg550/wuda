package com.qmth.wuda.teaching.templete;

import com.qmth.wuda.teaching.bean.Result;

import java.io.IOException;

/**
 * @Description: 科目分析模版
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/1/21
 */
public interface CourseAnalysisTemplete {

    /**
     * 数据分析模版
     *
     * @param examId
     * @param examCode
     * @return
     */
    Result dataAnalysis(Long examId, String examCode) throws IOException;
}
