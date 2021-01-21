package com.qmth.wuda.teaching.templete.service.impl;

import com.qmth.wuda.teaching.service.CallApiService;
import com.qmth.wuda.teaching.templete.service.CourseAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 公用分析impl
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/1/21
 */
@Service
public class CourseAnalysisServiceImpl implements CourseAnalysisService {
    private final static Logger log = LoggerFactory.getLogger(CourseAnalysisServiceImpl.class);

    @Resource
    CallApiService callApiService;

    /**
     * 抽取云阅卷源数据
     *
     * @param id
     * @param cloudExamCode
     * @return
     */
    @Override
    public List<Map> yyjSourceDataAnalysis(Long id, String cloudExamCode) throws IOException {
        return callApiService.callStudentScore(id, cloudExamCode);
    }
}
