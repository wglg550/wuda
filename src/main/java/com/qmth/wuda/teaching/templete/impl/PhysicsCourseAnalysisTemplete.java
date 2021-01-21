package com.qmth.wuda.teaching.templete.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.bean.YyjSourceDataBean;
import com.qmth.wuda.teaching.entity.*;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.TBSchoolService;
import com.qmth.wuda.teaching.service.TEPaperService;
import com.qmth.wuda.teaching.service.TEPaperStructService;
import com.qmth.wuda.teaching.templete.CourseAnalysisTemplete;
import com.qmth.wuda.teaching.templete.service.CourseAnalysisService;
import com.qmth.wuda.teaching.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Resource
    CourseAnalysisService courseAnalysisService;

    @Resource
    TBSchoolService tbSchoolService;

    @Resource
    TEPaperService tePaperService;

    @Resource
    TEPaperStructService tePaperStructService;

    @Override
    @Transactional
    public Result dataAnalysis(Long examId, String examCode) throws IOException {
        List<Map> studentsMark = courseAnalysisService.yyjSourceDataAnalysis(examId, examCode);
        if (Objects.nonNull(studentsMark) && studentsMark.size() > 0) {
            QueryWrapper<TBSchool> tbSchoolQueryWrapper = new QueryWrapper<>();
            tbSchoolQueryWrapper.lambda().eq(TBSchool::getCode, "whdx");
            TBSchool tbSchool = tbSchoolService.getOne(tbSchoolQueryWrapper);

            Map<String, String> courseMap = new LinkedHashMap<>();
            Map<String, TBSchoolCollege> collegeMap = new LinkedHashMap<>();
            Map<String, TEStudent> studentMap = new LinkedHashMap<>();
            Map<String, TEExamStudent> examStudentMap = new LinkedHashMap<>();
            Map<String, TBTeacher> teacherMap = new LinkedHashMap<>();
            LinkedMultiValueMap<Long, TBTeacherExamStudent> teacherExamStudentMap = new LinkedMultiValueMap<>();
            Map<Long, TEExamRecord> examRecordMap = new LinkedHashMap<>();
            Map<Long, List<TEAnswer>> teAnswerMap = new LinkedHashMap<>();
            Map<String, TEPaper> tePaperMap = new LinkedHashMap<>();
            Map<Long, Map<String, TEPaperStruct>> tePaperStructTranMap = new LinkedHashMap<>();
            Gson gson = new Gson();
            for (int i = 0; i < studentsMark.size(); i++) {
                YyjSourceDataBean yyjSourceDataBean = gson.fromJson(gson.toJson(studentsMark.get(i)), YyjSourceDataBean.class);
                if (Objects.equals(yyjSourceDataBean.getSubjectName(), "大学物理B（下）")) {
                    yyjSourceDataBean.setSubjectCode("1001");
                }
                TEPaper tePaper = null;
                if (!tePaperMap.containsKey(yyjSourceDataBean.getSubjectCode())) {
                    QueryWrapper<TEPaper> tePaperQueryWrapper = new QueryWrapper<>();
                    tePaperQueryWrapper.lambda().eq(TEPaper::getExamId, examId)
                            .eq(TEPaper::getCourseCode, yyjSourceDataBean.getSubjectCode());
                    tePaper = tePaperService.getOne(tePaperQueryWrapper);
                    if (Objects.isNull(tePaper)) {
                        continue;
                    }
                    tePaperMap.put(yyjSourceDataBean.getSubjectCode(), tePaper);
                } else {
                    tePaper = tePaperMap.get(yyjSourceDataBean.getSubjectCode());
                }

                Map<String, TEPaperStruct> tePaperStructMap = null;
                if (!tePaperStructTranMap.containsKey(tePaper.getId())) {
                    QueryWrapper<TEPaperStruct> tePaperStructQueryWrapper = new QueryWrapper<>();
                    tePaperStructQueryWrapper.lambda().eq(TEPaperStruct::getPaperId, tePaper.getId());
                    List<TEPaperStruct> tePaperStructList = tePaperStructService.list(tePaperStructQueryWrapper);
                    if (Objects.isNull(tePaperStructList) || tePaperStructList.size() == 0) {
                        throw new BusinessException("试卷结构为空");
                    }
                    if (Objects.nonNull(tePaperStructList) && tePaperStructList.size() > 0) {
                        tePaperStructMap = tePaperStructList.stream().collect(Collectors.toMap(s -> s.getMainNumber() + "-" + s.getSubNumber(), Function.identity(), (dto1, dto2) -> dto1));
                        tePaperStructTranMap.put(tePaper.getId(), tePaperStructMap);
                    }
                } else {
                    tePaperStructMap = tePaperStructTranMap.get(tePaper.getId());
                }

                if (!courseMap.containsKey(yyjSourceDataBean.getSubjectCode())) {
                    courseMap.put(yyjSourceDataBean.getSubjectCode(), yyjSourceDataBean.getSubjectName());
                }
                if (!collegeMap.containsKey(yyjSourceDataBean.getCollege())) {
                    TBSchoolCollege tbSchoolCollege = new TBSchoolCollege(tbSchool.getId(), yyjSourceDataBean.getCollege(), yyjSourceDataBean.getCollege());
                    collegeMap.put(yyjSourceDataBean.getCollege(), tbSchoolCollege);
                }
                if (!studentMap.containsKey(yyjSourceDataBean.getStudentCode())) {
                    TEStudent teStudent = new TEStudent(tbSchool.getId(), yyjSourceDataBean.getName(), yyjSourceDataBean.getStudentCode());
                    studentMap.put(yyjSourceDataBean.getStudentCode(), teStudent);
                }
                if (!examStudentMap.containsKey(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber())) {
                    TEExamStudent teExamStudent = new TEExamStudent(examId, studentMap.get(yyjSourceDataBean.getStudentCode()).getId(), collegeMap.get(yyjSourceDataBean.getCollege()).getId(), null, yyjSourceDataBean.getSubjectName(), yyjSourceDataBean.getSubjectCode(), yyjSourceDataBean.getName(), yyjSourceDataBean.getStudentCode(), yyjSourceDataBean.getExamNumber(), yyjSourceDataBean.getStatus(), yyjSourceDataBean.getClassName(), yyjSourceDataBean.getPaperType());
                    examStudentMap.put(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber(), teExamStudent);
                }
                if (!teacherMap.containsKey(yyjSourceDataBean.getTeacher())) {
                    TBTeacher tbTeacher = new TBTeacher(tbSchool.getId(), collegeMap.get(yyjSourceDataBean.getCollege()).getId(), null, yyjSourceDataBean.getTeacher());
                    teacherMap.put(yyjSourceDataBean.getTeacher(), tbTeacher);
                }
                TBTeacherExamStudent tbTeacherExamStudent = new TBTeacherExamStudent(teacherMap.get(yyjSourceDataBean.getTeacher()).getId(), examStudentMap.get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId());
                teacherExamStudentMap.add(teacherMap.get(yyjSourceDataBean.getTeacher()).getId(), tbTeacherExamStudent);

                if (!examRecordMap.containsKey(examStudentMap.get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId())) {
                    TEExamRecord teExamRecord = new TEExamRecord(examId, tePaper.getId(), examStudentMap.get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId(), new BigDecimal(yyjSourceDataBean.getObjectiveScore()), new BigDecimal(yyjSourceDataBean.getSubjectiveScore()), new BigDecimal(yyjSourceDataBean.getTotalScore()), null, null);
                    examRecordMap.put(examStudentMap.get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId(), teExamRecord);
                }

                List<TEAnswer> teAnswerList = new ArrayList();
                for (int y = 0; y < yyjSourceDataBean.getObjectiveScoreDetail().size(); y++) {
                    YyjSourceDataBean.YyjObjectiveScoreBean yyjObjectiveScoreBean = yyjSourceDataBean.getObjectiveScoreDetail().get(y);
                    if (Objects.nonNull(tePaperStructMap.get(yyjObjectiveScoreBean.getMainNumber() + "-" + yyjObjectiveScoreBean.getSubNumber()))) {
                        TEAnswer teAnswer = new TEAnswer(yyjObjectiveScoreBean.getMainNumber(), yyjObjectiveScoreBean.getSubNumber(), tePaperStructMap.get(yyjObjectiveScoreBean.getMainNumber() + "-" + yyjObjectiveScoreBean.getSubNumber()).getType(), examRecordMap.get(examStudentMap.get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId(), yyjObjectiveScoreBean.getAnswer(), yyjObjectiveScoreBean.getScore());
                        teAnswerList.add(teAnswer);
                    }
                }

                for (int y = 0; y < yyjSourceDataBean.getSubjectiveScoreDetail().size(); y++) {
                    YyjSourceDataBean.YyjSubjectiveScoreBean yyjSubjectiveScoreBean = yyjSourceDataBean.getSubjectiveScoreDetail().get(y);
                    if (Objects.nonNull(tePaperStructMap.get(yyjSubjectiveScoreBean.getMainNumber() + "-" + yyjSubjectiveScoreBean.getSubNumber()))) {
                        TEAnswer teAnswer = new TEAnswer(yyjSubjectiveScoreBean.getMainNumber(), yyjSubjectiveScoreBean.getSubNumber(), tePaperStructMap.get(yyjSubjectiveScoreBean.getMainNumber() + "-" + yyjSubjectiveScoreBean.getSubNumber()).getType(), examRecordMap.get(examStudentMap.get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId(), yyjSubjectiveScoreBean.getScore());
                        teAnswerList.add(teAnswer);
                    }
                }
                if (!teAnswerMap.containsKey(examRecordMap.get(examStudentMap.get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId())) {
                    teAnswerMap.put(examRecordMap.get(examStudentMap.get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId(), teAnswerList);
                }
            }
            courseAnalysisService.saveYyjSourceDataForDb(tbSchool, examId, courseMap, collegeMap, studentMap, examStudentMap, teacherMap, teacherExamStudentMap, examRecordMap, teAnswerMap, tePaperMap, tePaperStructTranMap);
        }
        return ResultUtil.ok(studentsMark);
    }
}
