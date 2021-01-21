package com.qmth.wuda.teaching.templete.impl;

import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.templete.CourseAnalysisTemplete;
import com.qmth.wuda.teaching.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

/**
 * @Description: 大学物理科目分析模版
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/1/21
 */
@Service
public class PhysicsCourseAnalysisTemplete implements CourseAnalysisTemplete {
    private final static Logger log = LoggerFactory.getLogger(PhysicsCourseAnalysisTemplete.class);

    @Override
    public Result dataAnalysis(Long id, String cloudExamCode) throws IOException {

        return ResultUtil.ok(Collections.singletonMap(SystemConstant.SUCCESS, true));
    }
}
