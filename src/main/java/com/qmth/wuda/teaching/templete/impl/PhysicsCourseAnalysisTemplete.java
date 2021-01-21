package com.qmth.wuda.teaching.templete.impl;

import com.google.gson.Gson;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.bean.YyjSourceDataBean;
import com.qmth.wuda.teaching.entity.TBSchool;
import com.qmth.wuda.teaching.entity.TEAnswer;
import com.qmth.wuda.teaching.enums.CourseEnum;
import com.qmth.wuda.teaching.templete.CourseAnalysisTemplete;
import com.qmth.wuda.teaching.templete.service.CourseAnalysisService;
import com.qmth.wuda.teaching.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 大学物理科目分析模版
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/1/21
 */
@Service
public class PhysicsCourseAnalysisTemplete extends CourseAnalysisTemplete {
    private final static Logger log = LoggerFactory.getLogger(PhysicsCourseAnalysisTemplete.class);

    @Resource
    CourseAnalysisService courseAnalysisService;

    PhysicsCourseAnalysisTemplete() {
        super();
    }

    @Override
    public Result dataAnalysis(Long examId, String examCode) throws IOException {
        List<Map> studentsMark = courseAnalysisService.yyjSourceDataAnalysis(examId, examCode);
        if (Objects.nonNull(studentsMark) && studentsMark.size() > 0) {
            TBSchool tbSchool = courseAnalysisService.getSchoolData();
            Gson gson = new Gson();
            for (int i = 0; i < studentsMark.size(); i++) {
                YyjSourceDataBean yyjSourceDataBean = gson.fromJson(gson.toJson(studentsMark.get(i)), YyjSourceDataBean.class);
                if (!courseAnalysisService.yyjResolveData(examId, tbSchool, yyjSourceDataBean, this)) {
                    continue;
                }

                List<TEAnswer> teAnswerList = new ArrayList();
                for (int y = 0; y < yyjSourceDataBean.getObjectiveScoreDetail().size(); y++) {
                    YyjSourceDataBean.YyjObjectiveScoreBean yyjObjectiveScoreBean = yyjSourceDataBean.getObjectiveScoreDetail().get(y);
                    if (Objects.nonNull(this.getTePaperStructMap().get(yyjObjectiveScoreBean.getMainNumber() + "-" + yyjObjectiveScoreBean.getSubNumber()))) {
                        TEAnswer teAnswer = new TEAnswer(yyjObjectiveScoreBean.getMainNumber(), yyjObjectiveScoreBean.getSubNumber(), this.getTePaperStructMap().get(yyjObjectiveScoreBean.getMainNumber() + "-" + yyjObjectiveScoreBean.getSubNumber()).getType(), this.getExamRecordMap().get(this.getExamStudentMap().get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId(), yyjObjectiveScoreBean.getAnswer(), yyjObjectiveScoreBean.getScore());
                        teAnswerList.add(teAnswer);
                    }
                }

                for (int y = 0; y < yyjSourceDataBean.getSubjectiveScoreDetail().size(); y++) {
                    YyjSourceDataBean.YyjSubjectiveScoreBean yyjSubjectiveScoreBean = yyjSourceDataBean.getSubjectiveScoreDetail().get(y);
                    if (Objects.nonNull(this.getTePaperStructMap().get(yyjSubjectiveScoreBean.getMainNumber() + "-" + yyjSubjectiveScoreBean.getSubNumber()))) {
                        TEAnswer teAnswer = new TEAnswer(yyjSubjectiveScoreBean.getMainNumber(), yyjSubjectiveScoreBean.getSubNumber(), this.getTePaperStructMap().get(yyjSubjectiveScoreBean.getMainNumber() + "-" + yyjSubjectiveScoreBean.getSubNumber()).getType(), this.getExamRecordMap().get(this.getExamStudentMap().get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId(), yyjSubjectiveScoreBean.getScore());
                        teAnswerList.add(teAnswer);
                    }
                }
                if (!this.getTeAnswerMap().containsKey(this.getExamRecordMap().get(this.getExamStudentMap().get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId())) {
                    this.getTeAnswerMap().put(this.getExamRecordMap().get(this.getExamStudentMap().get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId(), teAnswerList);
                }
            }
            courseAnalysisService.saveYyjSourceDataForDb(examId, tbSchool, this);
        }
        return ResultUtil.ok(studentsMark);
    }
}
