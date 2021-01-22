package com.qmth.wuda.teaching.templete.impl;

import com.google.gson.Gson;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.bean.YyjSourceDataBean;
import com.qmth.wuda.teaching.entity.TBSchool;
import com.qmth.wuda.teaching.entity.TEAnswer;
import com.qmth.wuda.teaching.templete.CourseAnalysisTemplete;
import com.qmth.wuda.teaching.templete.service.CourseAnalysisService;
import com.qmth.wuda.teaching.util.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 大学数学科目分析模版
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/1/21
 */
@Service
public class MathCourseAnalysisTemplete extends CourseAnalysisTemplete {

    @Resource
    CourseAnalysisService courseAnalysisService;

    MathCourseAnalysisTemplete() {
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
//                    if (yyjObjectiveScoreBean.getMainNumber() == 2 && yyjObjectiveScoreBean.getSubNumber() == 1) {
//                        yyjObjectiveScoreBean.setSubNumber(5);
//                    } else if (yyjObjectiveScoreBean.getMainNumber() == 2 && yyjObjectiveScoreBean.getSubNumber() == 2) {
//                        yyjObjectiveScoreBean.setSubNumber(6);
//                    } else if (yyjObjectiveScoreBean.getMainNumber() == 2 && yyjObjectiveScoreBean.getSubNumber() == 3) {
//                        yyjObjectiveScoreBean.setSubNumber(7);
//                    } else if (yyjObjectiveScoreBean.getMainNumber() == 2 && yyjObjectiveScoreBean.getSubNumber() == 4) {
//                        yyjObjectiveScoreBean.setSubNumber(8);
//                    } else if (yyjObjectiveScoreBean.getMainNumber() == 3 && yyjObjectiveScoreBean.getSubNumber() == 1) {
//                        yyjObjectiveScoreBean.setSubNumber(9);
//                    } else if (yyjObjectiveScoreBean.getMainNumber() == 4 && yyjObjectiveScoreBean.getSubNumber() == 1) {
//                        yyjObjectiveScoreBean.setSubNumber(10);
//                    } else if (yyjObjectiveScoreBean.getMainNumber() == 5 && yyjObjectiveScoreBean.getSubNumber() == 1) {
//                        yyjObjectiveScoreBean.setSubNumber(11);
//                    } else if (yyjObjectiveScoreBean.getMainNumber() == 6 && yyjObjectiveScoreBean.getSubNumber() == 1) {
//                        yyjObjectiveScoreBean.setSubNumber(12);
//                    } else if (yyjObjectiveScoreBean.getMainNumber() == 7 && yyjObjectiveScoreBean.getSubNumber() == 1) {
//                        yyjObjectiveScoreBean.setSubNumber(13);
//                    } else if (yyjObjectiveScoreBean.getMainNumber() == 8 && yyjObjectiveScoreBean.getSubNumber() == 1) {
//                        yyjObjectiveScoreBean.setSubNumber(14);
//                    } else if (yyjObjectiveScoreBean.getMainNumber() == 9 && yyjObjectiveScoreBean.getSubNumber() == 1) {
//                        yyjObjectiveScoreBean.setSubNumber(16);
//                    }
                    Integer updateSubNumber = updateSubNumber(yyjObjectiveScoreBean);
                    yyjObjectiveScoreBean.setSubNumber(Objects.nonNull(updateSubNumber) ? updateSubNumber : yyjObjectiveScoreBean.getSubNumber());
                    if (Objects.nonNull(this.getTePaperStructMap().get(yyjObjectiveScoreBean.getMainNumber() + "-" + yyjObjectiveScoreBean.getSubNumber()))) {
                        TEAnswer teAnswer = new TEAnswer(yyjObjectiveScoreBean.getMainNumber(), yyjObjectiveScoreBean.getSubNumber(), this.getTePaperStructMap().get(yyjObjectiveScoreBean.getMainNumber() + "-" + yyjObjectiveScoreBean.getSubNumber()).getType(), this.getExamRecordMap().get(this.getExamStudentMap().get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId(), yyjObjectiveScoreBean.getAnswer(), yyjObjectiveScoreBean.getScore());
                        teAnswerList.add(teAnswer);
                    }
                }

                for (int y = 0; y < yyjSourceDataBean.getSubjectiveScoreDetail().size(); y++) {
                    YyjSourceDataBean.YyjSubjectiveScoreBean yyjSubjectiveScoreBean = yyjSourceDataBean.getSubjectiveScoreDetail().get(y);
                    Integer updateSubNumber = updateSubNumber(yyjSubjectiveScoreBean);
                    yyjSubjectiveScoreBean.setSubNumber(Objects.nonNull(updateSubNumber) ? updateSubNumber : yyjSubjectiveScoreBean.getSubNumber());
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

    /**
     * 修改子题号
     *
     * @param obj
     * @return
     */
    public Integer updateSubNumber(Object obj) {
        Integer mainNumber = null, subnumber = null, updateSubNumber = null;
        if (obj instanceof YyjSourceDataBean.YyjObjectiveScoreBean) {
            mainNumber = ((YyjSourceDataBean.YyjObjectiveScoreBean) obj).getMainNumber();
            subnumber = ((YyjSourceDataBean.YyjObjectiveScoreBean) obj).getSubNumber();
        } else {
            mainNumber = ((YyjSourceDataBean.YyjSubjectiveScoreBean) obj).getMainNumber();
            subnumber = ((YyjSourceDataBean.YyjSubjectiveScoreBean) obj).getSubNumber();
        }
        if (mainNumber == 2 && subnumber == 1) {
            updateSubNumber = 5;
        } else if (mainNumber == 2 && subnumber == 2) {
            updateSubNumber = 6;
        } else if (mainNumber == 2 && subnumber == 3) {
            updateSubNumber = 7;
        } else if (mainNumber == 2 && subnumber == 4) {
            updateSubNumber = 8;
        } else if (mainNumber == 3 && subnumber == 1) {
            updateSubNumber = 9;
        } else if (mainNumber == 4 && subnumber == 1) {
            updateSubNumber = 10;
        } else if (mainNumber == 5 && subnumber == 1) {
            updateSubNumber = 11;
        } else if (mainNumber == 6 && subnumber == 1) {
            updateSubNumber = 12;
        } else if (mainNumber == 7 && subnumber == 1) {
            updateSubNumber = 13;
        } else if (mainNumber == 8 && subnumber == 1) {
            updateSubNumber = 14;
        } else if (mainNumber == 9 && subnumber == 1) {
            updateSubNumber = 15;
        }
        return updateSubNumber;
    }
}
