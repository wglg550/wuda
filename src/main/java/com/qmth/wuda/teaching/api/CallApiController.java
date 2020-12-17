package com.qmth.wuda.teaching.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qmth.wuda.teaching.annotation.ApiJsonObject;
import com.qmth.wuda.teaching.annotation.ApiJsonProperty;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.entity.*;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.*;
import com.qmth.wuda.teaching.util.ResultUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Api(tags = "调用层apiController")
@RestController
@RequestMapping("/${prefix.url.wuda}/call")
public class CallApiController {
    private final static Logger log = LoggerFactory.getLogger(CallApiController.class);

    @Resource
    CallApiService callApiService;

    @Resource
    TBSchoolService tbSchoolService;

    @Resource
    TEPaperService tePaperService;

    @Resource
    TEPaperStructService tePaperStructService;

    @Resource
    TECourseService teCourseService;

    @Resource
    TEStudentService teStudentService;

    @Resource
    TEExamStudentService teExamStudentService;

    @Resource
    TBTeacherService tbTeacherService;

    @Resource
    TBTeacherExamStudentService tbTeacherExamStudentService;

    @Resource
    TEExamRecordService teExamRecordService;

    @Resource
    TEAnswerService teAnswerService;

    @ApiOperation(value = "获取考生成绩接口")
    @RequestMapping(value = "/student/score", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result studentScore(
            @ApiJsonObject(name = "callStudentScore", value = {
                    @ApiJsonProperty(key = "examId", description = "考试id"),
                    @ApiJsonProperty(key = "examCode", description = "考试code")
            })
            @ApiParam(value = "获取考生成绩", required = true) @RequestBody Map<String, Object> mapParameter) {
        Long examId = null;
        String examCode = null;
        if (Objects.nonNull(mapParameter.get("examId")) && !Objects.equals(mapParameter.get("examId"), "")) {
            examId = Long.parseLong(String.valueOf(mapParameter.get("examId")));
        } else if (Objects.nonNull(mapParameter.get("examCode")) && !Objects.equals(mapParameter.get("examCode"), "")) {
            examCode = (String) mapParameter.get("examCode");
        }
        if (Objects.isNull(examId) && Objects.isNull(examCode)) {
            throw new BusinessException("考试id或考试code必须传一个");
        }
        List<Map> studentsMark = callApiService.callStudentScore(examId, examCode);
        if (Objects.nonNull(studentsMark) && studentsMark.size() > 0) {
            QueryWrapper<TBSchool> tbSchoolQueryWrapper = new QueryWrapper<>();
            tbSchoolQueryWrapper.lambda().eq(TBSchool::getCode, "whdx");
            TBSchool tbSchool = tbSchoolService.getOne(tbSchoolQueryWrapper);

//            QueryWrapper<TEPaper> tePaperQueryWrapper = new QueryWrapper<>();
//            tePaperQueryWrapper.lambda().eq(TEPaper::getExamId, examId);
//            TEPaper tePaper = tePaperService.getOne(tePaperQueryWrapper);
//            if (Objects.isNull(tePaper)) {
//                throw new BusinessException("试卷为空");
//            }
//
//            QueryWrapper<TEPaperStruct> tePaperStructQueryWrapper = new QueryWrapper<>();
//            tePaperStructQueryWrapper.lambda().eq(TEPaperStruct::getPaperId, tePaper.getId());
//            List<TEPaperStruct> tePaperStructList = tePaperStructService.list(tePaperStructQueryWrapper);
//            if (Objects.isNull(tePaperStructList) || tePaperStructList.size() == 0) {
//                throw new BusinessException("试卷结构为空");
//            }
//            Map<String, TEPaperStruct> tePaperStructMap = new LinkedHashMap<>();
//            if (Objects.nonNull(tePaperStructList) && tePaperStructList.size() > 0) {
//                tePaperStructList.forEach(s -> {
//                    tePaperStructMap.put(s.getMainNumber() + "-" + s.getSubNumber(), s);
//                });
//            }

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
            for (int i = 0; i < studentsMark.size(); i++) {
                Map map = studentsMark.get(i);
                String college = (String) map.get("college");
                String objectiveScore = (String) map.get("objectiveScore");
                String paperType = (String) map.get("paperType");
                String examCodeMap = (String) map.get("examCode");
                String studentCode = (String) map.get("studentCode");
                String className = (String) map.get("className");
                String examNumber = (String) map.get("examNumber");
                String totalScore = (String) map.get("totalScore");
                String teacher = (String) map.get("teacher");
                Long examIdMap = Long.parseLong(String.valueOf(map.get("examId")));
                String name = (String) map.get("name");
                String subjectiveScore = (String) map.get("subjectiveScore");
                String subjectCode = (String) map.get("subjectCode");
                String subjectName = (String) map.get("subjectName");
                Integer status = Integer.parseInt(String.valueOf(map.get("status")));

                TEPaper tePaper = null;
                if (!tePaperMap.containsKey(subjectCode)) {
                    QueryWrapper<TEPaper> tePaperQueryWrapper = new QueryWrapper<>();
                    tePaperQueryWrapper.lambda().eq(TEPaper::getExamId, examId)
                            .eq(TEPaper::getCourseCode, subjectCode);
                    tePaper = tePaperService.getOne(tePaperQueryWrapper);
                    if (Objects.isNull(tePaper)) {
                        continue;
                    }
                    tePaperMap.put(subjectCode, tePaper);
                } else {
                    tePaper = tePaperMap.get(subjectCode);
                }

                Map<String, TEPaperStruct> tePaperStructMap = null;
                if (!tePaperStructTranMap.containsKey(tePaper.getId())) {
                    QueryWrapper<TEPaperStruct> tePaperStructQueryWrapper = new QueryWrapper<>();
                    tePaperStructQueryWrapper.lambda().eq(TEPaperStruct::getPaperId, tePaper.getId());
                    List<TEPaperStruct> tePaperStructList = tePaperStructService.list(tePaperStructQueryWrapper);
                    if (Objects.isNull(tePaperStructList) || tePaperStructList.size() == 0) {
                        throw new BusinessException("试卷结构为空");
                    }
                    tePaperStructMap = new LinkedHashMap<>();
                    if (Objects.nonNull(tePaperStructList) && tePaperStructList.size() > 0) {
                        Map<String, TEPaperStruct> finalTePaperStructMap = tePaperStructMap;
                        tePaperStructList.forEach(s -> {
                            finalTePaperStructMap.put(s.getMainNumber() + "-" + s.getSubNumber(), s);
                        });
                    }
                    tePaperStructTranMap.put(tePaper.getId(), tePaperStructMap);
                } else {
                    tePaperStructMap = tePaperStructTranMap.get(tePaper.getId());
                }

                if (!courseMap.containsKey(subjectCode)) {
                    courseMap.put(subjectCode, subjectName);
                }
                if (!collegeMap.containsKey(college)) {
                    TBSchoolCollege tbSchoolCollege = new TBSchoolCollege(tbSchool.getId(), college, college);
                    collegeMap.put(college, tbSchoolCollege);
                }
                if (!studentMap.containsKey(studentCode)) {
                    TEStudent teStudent = new TEStudent(tbSchool.getId(), name, studentCode);
                    studentMap.put(studentCode, teStudent);
                }
                if (!examStudentMap.containsKey(studentCode + "_" + examNumber)) {
                    TEExamStudent teExamStudent = new TEExamStudent(examIdMap, studentMap.get(studentCode).getId(), collegeMap.get(college).getId(), null, subjectName, subjectCode, name, studentCode, examNumber, status, className);
                    examStudentMap.put(studentCode + "_" + examNumber, teExamStudent);
                }
                if (!teacherMap.containsKey(teacher)) {
                    TBTeacher tbTeacher = new TBTeacher(tbSchool.getId(), collegeMap.get(college).getId(), null, teacher);
                    teacherMap.put(teacher, tbTeacher);
                }
                TBTeacherExamStudent tbTeacherExamStudent = new TBTeacherExamStudent(teacherMap.get(teacher).getId(), examStudentMap.get(studentCode + "_" + examNumber).getId());
                teacherExamStudentMap.add(teacherMap.get(teacher).getId(), tbTeacherExamStudent);

                if (!examRecordMap.containsKey(examStudentMap.get(studentCode + "_" + examNumber).getId())) {
                    TEExamRecord teExamRecord = new TEExamRecord(examIdMap, tePaper.getId(), examStudentMap.get(studentCode + "_" + examNumber).getId(), new BigDecimal(objectiveScore), new BigDecimal(subjectiveScore), new BigDecimal(totalScore), null, null);
                    examRecordMap.put(examStudentMap.get(studentCode + "_" + examNumber).getId(), teExamRecord);
                }

                List<TEAnswer> teAnswerList = new ArrayList();
                JSONArray subjectiveScoreJsonArray = (JSONArray) map.get("subjectiveScoreDetail");
                JSONArray objectiveScoreJsonArray = (JSONArray) map.get("objectiveScoreDetail");
                for (int y = 0; y < objectiveScoreJsonArray.size(); y++) {
                    JSONObject objectiveJsonObject = objectiveScoreJsonArray.getJSONObject(y);
                    String score = objectiveJsonObject.getString("score");
                    String answer = objectiveJsonObject.getString("answer");
                    String mainNumber = objectiveJsonObject.getString("mainNumber");
                    String subNumber = objectiveJsonObject.getString("subNumber");
                    if (Objects.nonNull(tePaperStructMap.get(mainNumber + "-" + subNumber))) {
                        TEAnswer teAnswer = new TEAnswer(Integer.parseInt(mainNumber), Integer.parseInt(subNumber), tePaperStructMap.get(mainNumber + "-" + subNumber).getType(), examRecordMap.get(examStudentMap.get(studentCode + "_" + examNumber).getId()).getId(), answer, new BigDecimal(score));
                        teAnswerList.add(teAnswer);
                    }
                }

                for (int y = 0; y < subjectiveScoreJsonArray.size(); y++) {
                    JSONObject subjectiveJsonObject = subjectiveScoreJsonArray.getJSONObject(y);
                    String score = subjectiveJsonObject.getString("score");
                    String mainNumber = subjectiveJsonObject.getString("mainNumber");
                    String subNumber = subjectiveJsonObject.getString("subNumber");
                    if (Objects.nonNull(tePaperStructMap.get(mainNumber + "-" + subNumber))) {
                        TEAnswer teAnswer = new TEAnswer(Integer.parseInt(mainNumber), Integer.parseInt(subNumber), tePaperStructMap.get(mainNumber + "-" + subNumber).getType(), examRecordMap.get(examStudentMap.get(studentCode + "_" + examNumber).getId()).getId(), new BigDecimal(score));
                        teAnswerList.add(teAnswer);
                    }
                }
                if (!teAnswerMap.containsKey(examRecordMap.get(examStudentMap.get(studentCode + "_" + examNumber).getId()).getId())) {
                    teAnswerMap.put(examRecordMap.get(examStudentMap.get(studentCode + "_" + examNumber).getId()).getId(), teAnswerList);
                }
            }
            System.out.println(111);
            courseMap.forEach((k, v) -> {
                int count = teCourseService.countByCourseCode(k);
                if (count == 0) {
                    throw new BusinessException("科目编码" + k + "不存在");
                }
            });

            teStudentService.deleteAll();
            teExamStudentService.deleteAll();
            tbTeacherService.deleteAll();
            tbTeacherExamStudentService.deleteAll();
            teExamRecordService.deleteAll();
            teAnswerService.deleteAll();
        }
        return ResultUtil.ok(studentsMark);
    }
}
