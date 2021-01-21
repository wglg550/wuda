package com.qmth.wuda.teaching.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import com.qmth.wuda.teaching.annotation.ApiJsonObject;
import com.qmth.wuda.teaching.annotation.ApiJsonProperty;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.bean.YyjSourceDataBean;
import com.qmth.wuda.teaching.constant.SystemConstant;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Resource
    TBSchoolCollegeService tbSchoolCollegeService;

    @ApiOperation(value = "获取考生成绩接口")
    @RequestMapping(value = "/student/score", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result studentScore(
            @ApiJsonObject(name = "callStudentScore", value = {
                    @ApiJsonProperty(key = "examId", description = "考试id"),
                    @ApiJsonProperty(key = "examCode", description = "考试code")
            })
            @ApiParam(value = "获取考生成绩", required = true) @RequestBody Map<String, Object> mapParameter) throws IOException {
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
                if (!tePaperMap.containsKey(yyjSourceDataBean.getStudentCode())) {
                    QueryWrapper<TEPaper> tePaperQueryWrapper = new QueryWrapper<>();
                    tePaperQueryWrapper.lambda().eq(TEPaper::getExamId, examId)
                            .eq(TEPaper::getCourseCode, yyjSourceDataBean.getStudentCode());
                    tePaper = tePaperService.getOne(tePaperQueryWrapper);
                    if (Objects.isNull(tePaper)) {
                        continue;
                    }
                    tePaperMap.put(yyjSourceDataBean.getStudentCode(), tePaper);
                } else {
                    tePaper = tePaperMap.get(yyjSourceDataBean.getStudentCode());
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

                if (!courseMap.containsKey(yyjSourceDataBean.getStudentCode())) {
                    courseMap.put(yyjSourceDataBean.getStudentCode(), yyjSourceDataBean.getSubjectName());
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
                    Map<Object, Object> objectiveJsonObject = yyjSourceDataBean.getObjectiveScoreDetail().get(y);
                    String score = (String) objectiveJsonObject.get("score");
                    String answer = (String) objectiveJsonObject.get("answer");
                    String mainNumber = (String) objectiveJsonObject.get("mainNumber");
                    String subNumber = (String) objectiveJsonObject.get("subNumber");
                    if (Objects.nonNull(tePaperStructMap.get(mainNumber + "-" + subNumber))) {
                        TEAnswer teAnswer = new TEAnswer(Integer.parseInt(mainNumber), Integer.parseInt(subNumber), tePaperStructMap.get(mainNumber + "-" + subNumber).getType(), examRecordMap.get(examStudentMap.get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId(), answer, new BigDecimal(score));
                        teAnswerList.add(teAnswer);
                    }
                }

                for (int y = 0; y < yyjSourceDataBean.getSubjectiveScoreDetail().size(); y++) {
                    Map<Object, Object> subjectiveJsonObject = yyjSourceDataBean.getSubjectiveScoreDetail().get(y);
                    String score = (String) subjectiveJsonObject.get("score");
                    String mainNumber = (String) subjectiveJsonObject.get("mainNumber");
                    String subNumber = (String) subjectiveJsonObject.get("subNumber");
                    if (Objects.nonNull(tePaperStructMap.get(mainNumber + "-" + subNumber))) {
                        TEAnswer teAnswer = new TEAnswer(Integer.parseInt(mainNumber), Integer.parseInt(subNumber), tePaperStructMap.get(mainNumber + "-" + subNumber).getType(), examRecordMap.get(examStudentMap.get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId(), new BigDecimal(score));
                        teAnswerList.add(teAnswer);
                    }
                }
                if (!teAnswerMap.containsKey(examRecordMap.get(examStudentMap.get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId())) {
                    teAnswerMap.put(examRecordMap.get(examStudentMap.get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId()).getId(), teAnswerList);
                }
            }
            courseMap.forEach((k, v) -> {
                int count = teCourseService.countByCourseCode(k);
                if (count == 0) {
                    throw new BusinessException("科目编码" + k + "不存在");
                }
            });

            tbSchoolCollegeService.deleteAll(tbSchool.getId());
            int min = 0;
            int max = SystemConstant.MAX_IMPORT_SIZE, size = studentMap.keySet().size();
            if (max >= size) {
                max = size;
            }
            List list = Arrays.asList(studentMap.keySet().toArray());
            while (max <= size) {
                List subList = list.subList(min, max);
                teStudentService.deleteAll(tbSchool.getId(), new HashSet<>(subList));
                if (max == size) {
                    break;
                }
                min = max;
                max += SystemConstant.MAX_IMPORT_SIZE;
                if (max >= size) {
                    max = size;
                }
            }
            tbTeacherService.deleteAll(tbSchool.getId());

            List<TBSchoolCollege> tbSchoolCollegeList = new ArrayList();
            collegeMap.forEach((k, v) -> {
                tbSchoolCollegeList.add(v);
            });
            tbSchoolCollegeService.saveOrUpdateBatch(tbSchoolCollegeList);

            List<TEStudent> teStudentList = new ArrayList();
            studentMap.forEach((k, v) -> {
                teStudentList.add(v);
            });
            teStudentService.saveOrUpdateBatch(teStudentList);

            List<TEExamStudent> teExamStudentList = new ArrayList();
            Long finalExamId = examId;
            examStudentMap.forEach((k, v) -> {
                QueryWrapper<TEExamStudent> teExamStudentQueryWrapper = new QueryWrapper<>();
                teExamStudentQueryWrapper.lambda().eq(TEExamStudent::getExamId, finalExamId)
                        .eq(TEExamStudent::getStudentCode, v.getStudentCode())
                        .eq(TEExamStudent::getExamNumber, v.getExamNumber())
                        .eq(TEExamStudent::getCourseCode, v.getCourseCode());
                TEExamStudent teExamStudent = teExamStudentService.getOne(teExamStudentQueryWrapper);
                if (Objects.nonNull(teExamStudent)) {
                    QueryWrapper<TEExamRecord> teExamRecordQueryWrapper = new QueryWrapper<>();
                    teExamRecordQueryWrapper.lambda().eq(TEExamRecord::getExamId, finalExamId)
                            .eq(TEExamRecord::getExamStudentId, teExamStudent.getId())
                            .eq(TEExamRecord::getPaperId, tePaperMap.get(teExamStudent.getCourseCode()).getId());
                    TEExamRecord teExamRecord = teExamRecordService.getOne(teExamRecordQueryWrapper);
                    if (Objects.nonNull(teExamRecord)) {
                        teAnswerService.deleteAll(teExamRecord.getId());
                        teExamRecordService.remove(teExamRecordQueryWrapper);
                    }
                    teExamStudentService.remove(teExamStudentQueryWrapper);
                }
                teExamStudentList.add(v);
            });
            teExamStudentService.saveOrUpdateBatch(teExamStudentList);

            List<TBTeacher> tbTeacherList = new ArrayList();
            teacherMap.forEach((k, v) -> {
                QueryWrapper<TBTeacher> tbTeacherQueryWrapper = new QueryWrapper<>();
                tbTeacherQueryWrapper.lambda().eq(TBTeacher::getSchoolId, tbSchool.getId())
                        .eq(TBTeacher::getName, v.getName());
                TBTeacher tbTeacher = tbTeacherService.getOne(tbTeacherQueryWrapper);

                if (Objects.nonNull(tbTeacher)) {
                    QueryWrapper<TBTeacherExamStudent> tbTeacherExamStudentQueryWrapper = new QueryWrapper<>();
                    tbTeacherExamStudentQueryWrapper.lambda().eq(TBTeacherExamStudent::getTeacherId, tbTeacher.getId());
                    tbTeacherExamStudentService.remove(tbTeacherExamStudentQueryWrapper);
                }
                tbTeacherList.add(v);
            });
            tbTeacherService.saveOrUpdateBatch(tbTeacherList);

            List<TBTeacherExamStudent> tbTeacherExamStudentList = new ArrayList();
            teacherExamStudentMap.forEach((k, v) -> {
                tbTeacherExamStudentList.addAll(v);
            });
            tbTeacherExamStudentService.saveOrUpdateBatch(tbTeacherExamStudentList);

            List<TEExamRecord> teExamRecordList = new ArrayList();
            examRecordMap.forEach((k, v) -> {
                teExamRecordList.add(v);
            });
            teExamRecordService.saveOrUpdateBatch(teExamRecordList);

            List<TEAnswer> teAnswerList = new ArrayList();
            teAnswerMap.forEach((k, v) -> {
                teAnswerList.addAll(v);
            });
            teAnswerService.saveOrUpdateBatch(teAnswerList);
        }
        return ResultUtil.ok(studentsMark);
    }
}
