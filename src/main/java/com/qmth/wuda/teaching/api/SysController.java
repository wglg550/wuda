package com.qmth.wuda.teaching.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.bean.excel.ExcelCallback;
import com.qmth.wuda.teaching.bean.excel.ExcelError;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.dto.DimensionImportDto;
import com.qmth.wuda.teaching.dto.ExamStudentImportDto;
import com.qmth.wuda.teaching.dto.PaperAndQuestionImportDto;
import com.qmth.wuda.teaching.entity.*;
import com.qmth.wuda.teaching.enums.ExceptionResultEnum;
import com.qmth.wuda.teaching.enums.UploadFileEnum;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.*;
import com.qmth.wuda.teaching.util.ExcelUtil;
import com.qmth.wuda.teaching.util.ResultUtil;
import com.qmth.wuda.teaching.util.ServletUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * <p>
 * 系统信息 前端控制器
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Api(tags = "系统信息Controller")
@RestController
@RequestMapping("/${prefix.url.wuda}/sys")
public class SysController {
    private final static Logger log = LoggerFactory.getLogger(SysController.class);

    @Resource
    TBAttachmentService tbAttachmentService;

    @Resource
    TBSchoolService tbSchoolService;

    @Resource
    TECourseService teCourseService;

    @Resource
    TBModuleService tbModuleService;

    @Resource
    TBDimensionService tbDimensionService;

    @Resource
    TEExamService teExamService;

    @Resource
    TEPaperService tePaperService;

    @Resource
    TEQuestionService teQuestionService;

    @Resource
    TBSchoolCollegeService tbSchoolCollegeService;

    @Resource
    TEStudentService teStudentService;

    @Resource
    TEExamStudentService teExamStudentService;

    @Resource
    TBMajorService tbMajorService;

    @Resource
    TBTeacherService tbTeacherService;

    @Resource
    TBTeacherExamStudentService tbTeacherExamStudentService;

    @Resource
    TEExamRecordService teExamRecordService;

    @Resource
    TEAnswerService teAnswerService;

    @ApiOperation(value = "测试接口")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result test() {
        log.info("test is come in");
        return ResultUtil.ok(Collections.singletonMap(SystemConstant.SUCCESS, true));
    }

    @ApiOperation(value = "考生导入接口")
    @RequestMapping(value = "/examStudent/import", method = RequestMethod.POST)
    @Transactional
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result examStudentImport(@ApiParam(value = "上传文件", required = true) @RequestParam MultipartFile file) {
        if (Objects.isNull(file) || Objects.equals(file, "")) {
            throw new BusinessException(ExceptionResultEnum.ATTACHMENT_IS_NULL);
        }
        TBAttachment tbAttachment = null;
        try {
            tbAttachment = tbAttachmentService
                    .saveAttachment(file, ServletUtil.getRequestMd5(), ServletUtil.getRequestPath(),
                            UploadFileEnum.file, null, null);
            if (Objects.isNull(tbAttachment)) {
                throw new BusinessException(ExceptionResultEnum.ATTACHMENT_ERROR);
            }
            List<LinkedMultiValueMap<Integer, Object>> finalList = ExcelUtil.excelReader(file.getInputStream(), Lists.newArrayList(ExamStudentImportDto.class), new ExcelCallback() {
                @Override
                public List<LinkedMultiValueMap<Integer, Object>> callback(List<LinkedMultiValueMap<Integer, Object>> finalList, List<LinkedMultiValueMap<Integer, String>> finalColumnNameList) throws IllegalAccessException, IOException {
                    List<ExcelError> excelErrorList = new ArrayList<>();
                    for (int i = 0; i < finalList.size(); i++) {
                        LinkedMultiValueMap<Integer, Object> map = finalList.get(i);
                        List<Object> examStudentImportDtoList = map.get(i);
                        for (int y = 0; y < examStudentImportDtoList.size(); y++) {
                            ExamStudentImportDto examStudentImportDto = (ExamStudentImportDto) examStudentImportDtoList.get(y);
                            List<ExcelError> excelErrorTemp = ExcelUtil.checkExcelField(examStudentImportDto, y, i);
                            if (excelErrorTemp.size() > 0) {
                                excelErrorList.addAll(excelErrorTemp);
                            }
                        }
                    }
                    if (excelErrorList.size() > 0) {
                        throw new BusinessException(JSONObject.toJSONString(excelErrorList));
                    }
                    return finalList;
                }
            });
            int line = 0;
            //保存到数据库
            if (Objects.nonNull(finalList) && finalList.size() > 0) {
                TBSchool tbSchool = tbSchoolService.getById(1);
                QueryWrapper<TEExam> teExamQueryWrapper = new QueryWrapper<>();
                teExamQueryWrapper.lambda().eq(TEExam::getSchoolId, tbSchool.getId())
                        .eq(TEExam::getCode, "202012");
                TEExam teExam = teExamService.getOne(teExamQueryWrapper);

                QueryWrapper<TEPaper> tePaperQueryWrapper = new QueryWrapper<>();
                tePaperQueryWrapper.lambda().eq(TEPaper::getExamId, teExam.getId());
                TEPaper tePaper = tePaperService.getOne(tePaperQueryWrapper);

                QueryWrapper<TEQuestion> teQuestionQueryWrapper = new QueryWrapper<>();
                teQuestionQueryWrapper.lambda().eq(TEQuestion::getPaperId, tePaper.getId());
                List<TEQuestion> teQuestionList = teQuestionService.list(teQuestionQueryWrapper);
                Map<String, TEQuestion> teQuestionMap = new LinkedHashMap<>();
                if (Objects.nonNull(teQuestionList) && teQuestionList.size() > 0) {
                    teQuestionList.forEach(s -> {
                        teQuestionMap.put(s.getType() + "题" + s.getMainNumber() + "-" + s.getSubNumber() + "作答", s);
                        teQuestionMap.put(s.getType() + "题" + s.getMainNumber() + "-" + s.getSubNumber() + "得分", s);
                    });
                }

                QueryWrapper<TECourse> teCourseQueryWrapper = new QueryWrapper<>();
                teCourseQueryWrapper.lambda().eq(TECourse::getSchoolId, tbSchool.getId())
                        .eq(TECourse::getCourseCode, "1100810013012");
                TECourse teCourse = teCourseService.getOne(teCourseQueryWrapper);

                Map<String, TBSchoolCollege> collegeMap = new LinkedHashMap<>();
                Map<String, TEStudent> studentMap = new LinkedHashMap<>();
                Map<String, TEExamStudent> examStudentMap = new LinkedHashMap<>();
                Map<String, TBMajor> majorMap = new LinkedHashMap<>();
                Map<String, TBTeacher> teacherMap = new LinkedHashMap<>();
                Map<String, Map<String, TBTeacherExamStudent>> teacherExamStudentMap = new LinkedHashMap<>();
                Map<String, Map<String, TEExamRecord>> examRecordMap = new LinkedHashMap<>();
                Map<String, Map<String, String>> questionMap = new LinkedHashMap<>();
                List<TEAnswer> teAnswerList = new ArrayList();
                for (int i = 0; i < finalList.size(); i++) {
                    LinkedMultiValueMap<Integer, Object> finalMap = finalList.get(i);
                    List<Object> dimensionExportDtoList = finalMap.get(i);
                    int min = 0;
                    int max = SystemConstant.MAX_IMPORT_SIZE, size = dimensionExportDtoList.size();
                    if (max >= size) {
                        max = size;
                    }
                    while (max <= size) {
                        List subList = dimensionExportDtoList.subList(min, max);
                        for (int y = 0; y < subList.size(); y++) {
                            line++;
                            ExamStudentImportDto examStudentImportDto = (ExamStudentImportDto) subList.get(y);
                            if (!collegeMap.containsKey(examStudentImportDto.getCollegeName())) {
                                TBSchoolCollege tbSchoolCollege = new TBSchoolCollege(tbSchool.getId(), examStudentImportDto.getCollegeName(), examStudentImportDto.getCollegeName());
                                collegeMap.put(examStudentImportDto.getCollegeName(), tbSchoolCollege);
                            }
                            if (!majorMap.containsKey(examStudentImportDto.getMajorName())) {
                                TBMajor tbMajor = new TBMajor(tbSchool.getId(), examStudentImportDto.getMajorName(), examStudentImportDto.getMajorName());
                                majorMap.put(examStudentImportDto.getMajorName(), tbMajor);
                            }
                            if (!studentMap.containsKey(examStudentImportDto.getIdcardNumber())) {
                                TEStudent teStudent = new TEStudent(tbSchool.getId(), examStudentImportDto.getExamStudentName(), examStudentImportDto.getIdcardNumber());
                                studentMap.put(examStudentImportDto.getIdcardNumber(), teStudent);
                            }
                            if (!examStudentMap.containsKey(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity())) {
                                TEExamStudent teExamStudent = new TEExamStudent(teExam.getId(), teCourse.getCourseName(), teCourse.getCourseCode(), examStudentImportDto.getExamStudentName(), examStudentImportDto.getIdentity(), examStudentImportDto.getIdcardNumber(), examStudentImportDto.getGrade(), Objects.equals(examStudentImportDto.getMiss(), "是") ? 1 : 0, examStudentImportDto.getMajorName(), examStudentImportDto.getCollegeName());
                                examStudentMap.put(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity(), teExamStudent);
                            }
                            if (!questionMap.containsKey(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity())) {
                                questionMap.put(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity(), examStudentImportDto.getExtendColumn());
                            }
                            if (!teacherMap.containsKey(examStudentImportDto.getTeacherName())) {
                                TBTeacher tbTeacher = new TBTeacher(tbSchool.getId(), examStudentImportDto.getTeacherName(), examStudentImportDto.getMajorName());
                                teacherMap.put(examStudentImportDto.getTeacherName(), tbTeacher);
                            }

                            Map<String, TBTeacherExamStudent> map = teacherExamStudentMap.get(examStudentImportDto.getTeacherName());
                            if (Objects.isNull(map) || map.size() == 0) {
                                map = new LinkedHashMap<>();
                            }
                            if (!map.containsKey(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity())) {
                                TBTeacherExamStudent tbTeacherExamStudent = new TBTeacherExamStudent(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity());
                                map.put(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity(), tbTeacherExamStudent);
                            }
                            teacherExamStudentMap.put(examStudentImportDto.getTeacherName(), map);

                            Map<String, TEExamRecord> teExamRecordMap = examRecordMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity());
                            if (Objects.isNull(teExamRecordMap) || teExamRecordMap.size() == 0) {
                                teExamRecordMap = new LinkedHashMap<>();
                            }
                            if (!teExamRecordMap.containsKey(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity())) {
                                TEExamRecord teExamRecord = new TEExamRecord(teExam.getId(), tePaper.getId(), Double.parseDouble(examStudentImportDto.getObjectiveScore()), Double.parseDouble(examStudentImportDto.getSubjectiveScore()), Double.parseDouble(examStudentImportDto.getSumScore()));
                                teExamRecordMap.put(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity(), teExamRecord);
                            }
                            examRecordMap.put(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity(), teExamRecordMap);
                        }
                        if (max == size) {
                            break;
                        }
                        min = max;
                        max += SystemConstant.MAX_IMPORT_SIZE;
                        if (max >= size) {
                            max = size;
                        }
                    }
                }
                tbSchoolCollegeService.deleteAll();
                teStudentService.deleteAll();
                teExamStudentService.deleteAll();
                tbMajorService.deleteAll();
                tbTeacherService.deleteAll();
                tbTeacherExamStudentService.deleteAll();
                teExamRecordService.deleteAll();
                teAnswerService.deleteAll();

                List<TBSchoolCollege> tbSchoolCollegeList = new ArrayList();
                collegeMap.forEach((k, v) -> {
                    tbSchoolCollegeList.add(v);
                });
                tbSchoolCollegeService.saveOrUpdateBatch(tbSchoolCollegeList);

                List<TBMajor> tbMajorList = new ArrayList<>();
                majorMap.forEach((k, v) -> {
                    tbMajorList.add(v);
                });
                tbMajorService.saveOrUpdateBatch(tbMajorList);

                List<TEStudent> teStudentList = new ArrayList();
                studentMap.forEach((k, v) -> {
                    teStudentList.add(v);
                });
                teStudentService.saveOrUpdateBatch(teStudentList);

                examStudentMap.forEach((k, v) -> {
                    TBMajor tbMajor = majorMap.get(v.getMajorName());
                    v.setMajorId(tbMajor.getId());

                    TBSchoolCollege tbSchoolCollege = collegeMap.get(v.getCollegeName());
                    v.setCollegeId(tbSchoolCollege.getId());

                    TEStudent teStudent = studentMap.get(v.getIdcardNumber());
                    v.setStudentId(teStudent.getId());

                    teExamStudentService.save(v);
                    Map<String, TEExamRecord> map = examRecordMap.get(k);
                    map.forEach((k1, v1) -> {
                        v1.setExamStudentId(v.getId());
                        teExamRecordService.save(v1);
                    });

                    Map<Long, TEAnswer> answerMap = new LinkedHashMap<>();
                    Map<String, String> m = questionMap.get(k);
                    m.forEach((k2, v2) -> {
                        TEAnswer teAnswer = null;
                        TEQuestion teQuestion = teQuestionMap.get(k2);
                        if (!answerMap.containsKey(teQuestion.getId())) {
                            teAnswer = new TEAnswer(teQuestion.getMainNumber(), teQuestion.getMainNumber(), teQuestion.getType(), map.get(k).getId());
                        } else {
                            teAnswer = answerMap.get(teQuestion.getId());
                        }
                        if (k2.contains("作答")) {
                            teAnswer.setAnswer(v2);
                        } else if (k2.contains("得分")) {
                            teAnswer.setScore(Double.parseDouble(v2));
                        }
                        teAnswer.setVersion(teAnswer.getVersion() + 1);
                        answerMap.put(teQuestion.getId(), teAnswer);
                    });

                    answerMap.forEach((k3, v3) -> {
                        teAnswerList.add(v3);
                    });
                });

                teAnswerService.saveOrUpdateBatch(teAnswerList);

                List<TBTeacherExamStudent> tbTeacherExamStudentList = new ArrayList();
                teacherMap.forEach((k, v) -> {
                    TBMajor tbMajor = majorMap.get(v.getMajorName());
                    v.setMajorId(tbMajor.getId());
                    tbTeacherService.save(v);
                    Map<String, TBTeacherExamStudent> map = teacherExamStudentMap.get(k);
                    map.forEach((k1, v1) -> {
                        v1.setTeacherId(v.getId());
                        TEExamStudent teExamStudent = examStudentMap.get(v1.getExamStudentIdcardNumberAndIdentity());
                        v1.setExamStudentId(teExamStudent.getId());
                        tbTeacherExamStudentList.add(v1);
                    });
                });
                tbTeacherExamStudentService.saveOrUpdateBatch(tbTeacherExamStudentList);
            }
        } catch (Exception e) {
            log.error("请求出错", e);
            if (Objects.nonNull(tbAttachment)) {
                tbAttachmentService.deleteAttachment(UploadFileEnum.file, tbAttachment);
            }
            if (e instanceof BusinessException) {
                throw new BusinessException(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return ResultUtil.ok(Collections.singletonMap(SystemConstant.SUCCESS, true));
    }

    @ApiOperation(value = "试卷题型导入接口")
    @RequestMapping(value = "/paperQuestion/import", method = RequestMethod.POST)
    @Transactional
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result paperQuestionImport(@ApiParam(value = "上传文件", required = true) @RequestParam MultipartFile file) {
        if (Objects.isNull(file) || Objects.equals(file, "")) {
            throw new BusinessException(ExceptionResultEnum.ATTACHMENT_IS_NULL);
        }
        TBAttachment tbAttachment = null;
        try {
            tbAttachment = tbAttachmentService
                    .saveAttachment(file, ServletUtil.getRequestMd5(), ServletUtil.getRequestPath(),
                            UploadFileEnum.file, null, null);
            if (Objects.isNull(tbAttachment)) {
                throw new BusinessException(ExceptionResultEnum.ATTACHMENT_ERROR);
            }
            List<LinkedMultiValueMap<Integer, Object>> finalList = ExcelUtil.excelReader(file.getInputStream(), Lists.newArrayList(PaperAndQuestionImportDto.class), new ExcelCallback() {
                @Override
                public List<LinkedMultiValueMap<Integer, Object>> callback(List<LinkedMultiValueMap<Integer, Object>> finalList, List<LinkedMultiValueMap<Integer, String>> finalColumnNameList) throws IllegalAccessException, IOException {
                    List<ExcelError> excelErrorList = new ArrayList<>();
                    for (int i = 0; i < finalList.size(); i++) {
                        LinkedMultiValueMap<Integer, Object> map = finalList.get(i);
                        List<Object> paperAndQuestionImportDtoList = map.get(i);
                        for (int y = 0; y < paperAndQuestionImportDtoList.size(); y++) {
                            PaperAndQuestionImportDto paperAndQuestionImportDto = (PaperAndQuestionImportDto) paperAndQuestionImportDtoList.get(y);
                            List<ExcelError> excelErrorTemp = ExcelUtil.checkExcelField(paperAndQuestionImportDto, y, i);
                            if (excelErrorTemp.size() > 0) {
                                excelErrorList.addAll(excelErrorTemp);
                            }
                        }
                    }
                    if (excelErrorList.size() > 0) {
                        throw new BusinessException(JSONObject.toJSONString(excelErrorList));
                    }
                    return finalList;
                }
            });
            int line = 0;
            //保存到数据库
            if (Objects.nonNull(finalList) && finalList.size() > 0) {
                TBSchool tbSchool = tbSchoolService.getById(1);
                Map<String, TEPaper> paperMap = new LinkedHashMap<>();
                Map<String, Map<String, TEQuestion>> questionMap = new LinkedHashMap<>();

                teExamService.deleteAll();
                TEExam teExam = new TEExam(tbSchool.getId(), "202012月考试", "202012", 1606890121000L, 1606890121000L);
                teExamService.save(teExam);
                for (int i = 0; i < finalList.size(); i++) {
                    LinkedMultiValueMap<Integer, Object> finalMap = finalList.get(i);
                    List<Object> dimensionExportDtoList = finalMap.get(i);
                    int min = 0;
                    int max = SystemConstant.MAX_IMPORT_SIZE, size = dimensionExportDtoList.size();
                    if (max >= size) {
                        max = size;
                    }
                    while (max <= size) {
                        List subList = dimensionExportDtoList.subList(min, max);
                        for (int y = 0; y < subList.size(); y++) {
                            line++;
                            PaperAndQuestionImportDto paperAndQuestionImportDto = (PaperAndQuestionImportDto) subList.get(y);
                            if (!paperMap.containsKey(paperAndQuestionImportDto.getPaperCode())) {
                                TEPaper tePaper = new TEPaper(teExam.getId(), paperAndQuestionImportDto.getCourseName(), paperAndQuestionImportDto.getCourseCode(), paperAndQuestionImportDto.getPaperCode(), paperAndQuestionImportDto.getPaperCode(), 100D);
                                paperMap.put(paperAndQuestionImportDto.getPaperCode(), tePaper);
                            }
                            Map<String, TEQuestion> teQuestionMap = questionMap.get(paperAndQuestionImportDto.getPaperCode());
                            if (Objects.isNull(teQuestionMap) || teQuestionMap.size() == 0) {
                                teQuestionMap = new LinkedHashMap<>();
                            }
                            if (!teQuestionMap.containsKey(paperAndQuestionImportDto.getMainNumber() + "_" + paperAndQuestionImportDto.getSubNumber())) {
                                TEQuestion teQuestion = new TEQuestion(Integer.parseInt(paperAndQuestionImportDto.getMainNumber()), Integer.parseInt(paperAndQuestionImportDto.getSubNumber()), paperAndQuestionImportDto.getType(), Double.parseDouble(paperAndQuestionImportDto.getScore()), paperAndQuestionImportDto.getRule(), paperAndQuestionImportDto.getDescription(), paperAndQuestionImportDto.getKnowledge(), paperAndQuestionImportDto.getCapability());
                                teQuestionMap.put(paperAndQuestionImportDto.getMainNumber() + "_" + paperAndQuestionImportDto.getSubNumber(), teQuestion);
                            }
                            questionMap.put(paperAndQuestionImportDto.getPaperCode(), teQuestionMap);
                        }
                        if (max == size) {
                            break;
                        }
                        min = max;
                        max += SystemConstant.MAX_IMPORT_SIZE;
                        if (max >= size) {
                            max = size;
                        }
                    }
                }
                tePaperService.deleteAll();
                teQuestionService.deleteAll();

                List<TEPaper> paperList = new ArrayList();
                paperMap.forEach((k, v) -> {
                    paperList.add(v);
                });
                tePaperService.saveOrUpdateBatch(paperList);

                List<TEQuestion> questionList = new ArrayList();
                questionMap.forEach((k, v) -> {
                    TEPaper tePaper = paperMap.get(k);
                    Map<String, TEQuestion> map = v;
                    map.forEach((k1, v1) -> {
                        v1.setPaperId(tePaper.getId());
                        questionList.add(v1);
                    });
                });
                teQuestionService.saveOrUpdateBatch(questionList);
            }
        } catch (Exception e) {
            log.error("请求出错", e);
            if (Objects.nonNull(tbAttachment)) {
                tbAttachmentService.deleteAttachment(UploadFileEnum.file, tbAttachment);
            }
            if (e instanceof BusinessException) {
                throw new BusinessException(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return ResultUtil.ok(Collections.singletonMap(SystemConstant.SUCCESS, true));
    }

    @ApiOperation(value = "维度导入接口")
    @RequestMapping(value = "/dimension/import", method = RequestMethod.POST)
    @Transactional
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result dimensionImport(@ApiParam(value = "上传文件", required = true) @RequestParam MultipartFile file) {
        if (Objects.isNull(file) || Objects.equals(file, "")) {
            throw new BusinessException(ExceptionResultEnum.ATTACHMENT_IS_NULL);
        }
        TBAttachment tbAttachment = null;
        try {
            tbAttachment = tbAttachmentService
                    .saveAttachment(file, ServletUtil.getRequestMd5(), ServletUtil.getRequestPath(),
                            UploadFileEnum.file, null, null);
            if (Objects.isNull(tbAttachment)) {
                throw new BusinessException(ExceptionResultEnum.ATTACHMENT_ERROR);
            }
            List<LinkedMultiValueMap<Integer, Object>> finalList = ExcelUtil.excelReader(file.getInputStream(), Lists.newArrayList(DimensionImportDto.class), new ExcelCallback() {
                @Override
                public List<LinkedMultiValueMap<Integer, Object>> callback(List<LinkedMultiValueMap<Integer, Object>> finalList, List<LinkedMultiValueMap<Integer, String>> finalColumnNameList) throws IllegalAccessException, IOException {
                    List<ExcelError> excelErrorList = new ArrayList<>();
                    for (int i = 0; i < finalList.size(); i++) {
                        LinkedMultiValueMap<Integer, Object> map = finalList.get(i);
                        List<Object> dimensionImportList = map.get(i);
                        for (int y = 0; y < dimensionImportList.size(); y++) {
                            DimensionImportDto dimensionImportDto = (DimensionImportDto) dimensionImportList.get(y);
                            List<ExcelError> excelErrorTemp = ExcelUtil.checkExcelField(dimensionImportDto, y, i);
                            if (excelErrorTemp.size() > 0) {
                                excelErrorList.addAll(excelErrorTemp);
                            }
                        }
                    }
                    if (excelErrorList.size() > 0) {
                        throw new BusinessException(JSONObject.toJSONString(excelErrorList));
                    }
                    return finalList;
                }
            });
            int line = 0;
            //保存到数据库
            if (Objects.nonNull(finalList) && finalList.size() > 0) {
                TBSchool tbSchool = tbSchoolService.getById(1);
                Map<String, TECourse> courseMap = new LinkedHashMap<>();
                Map<String, TBModule> moduleMap = new LinkedHashMap<>();
                Map<String, Map<String, TBDimension>> dimensionMap = new LinkedHashMap<>();
                for (int i = 0; i < finalList.size(); i++) {
                    LinkedMultiValueMap<Integer, Object> finalMap = finalList.get(i);
                    List<Object> dimensionExportDtoList = finalMap.get(i);
                    int min = 0;
                    int max = SystemConstant.MAX_IMPORT_SIZE, size = dimensionExportDtoList.size();
                    if (max >= size) {
                        max = size;
                    }
                    while (max <= size) {
                        List subList = dimensionExportDtoList.subList(min, max);
                        for (int y = 0; y < subList.size(); y++) {
                            line++;
                            DimensionImportDto dimensionImportDto = (DimensionImportDto) subList.get(y);
                            if (!courseMap.containsKey(dimensionImportDto.getCourseCode())) {
                                TECourse teCourse = new TECourse(tbSchool.getId(), dimensionImportDto.getCourseName(), dimensionImportDto.getCourseCode());
                                courseMap.put(dimensionImportDto.getCourseCode(), teCourse);
                            }
                            if (!moduleMap.containsKey(dimensionImportDto.getModuleName())) {
                                TBModule tbModule = new TBModule(tbSchool.getId(), dimensionImportDto.getModuleName(), dimensionImportDto.getModuleName(), "课程标准规定的学科内容", "1.熟练：个人得分率≥85%\n" +
                                        "2.基本熟练：60%≤个人得分率＜85%\n" +
                                        "3.不熟练：个人得分率＜60%");
                                moduleMap.put(dimensionImportDto.getModuleName(), tbModule);
                            }
                            Map<String, TBDimension> tbDimensionMap = dimensionMap.get(dimensionImportDto.getModuleName());
                            if (Objects.isNull(tbDimensionMap) || tbDimensionMap.size() == 0) {
                                tbDimensionMap = new LinkedHashMap<>();
                            }
                            if (!tbDimensionMap.containsKey(dimensionImportDto.getIdentifierSecond())) {
                                TBDimension tbDimension = new TBDimension(dimensionImportDto.getCourseName(), dimensionImportDto.getCourseCode(), dimensionImportDto.getKnowledgeFirst(), dimensionImportDto.getIdentifierFirst(), dimensionImportDto.getKnowledgeSecond(), dimensionImportDto.getIdentifierSecond(), dimensionImportDto.getDescription());
                                tbDimensionMap.put(dimensionImportDto.getIdentifierSecond(), tbDimension);
                            }
                            dimensionMap.put(dimensionImportDto.getModuleName(), tbDimensionMap);
                        }
                        if (max == size) {
                            break;
                        }
                        min = max;
                        max += SystemConstant.MAX_IMPORT_SIZE;
                        if (max >= size) {
                            max = size;
                        }
                    }
                }
                teCourseService.deleteAll();
                tbModuleService.deleteAll();
                tbDimensionService.deleteAll();

                List<TECourse> courseList = new ArrayList();
                courseMap.forEach((k, v) -> {
                    courseList.add(v);
                });
                teCourseService.saveOrUpdateBatch(courseList);

                moduleMap.forEach((k, v) -> {
                    tbModuleService.save(v);
                });

                List<TBDimension> tbDimensionList = new ArrayList<>();
                dimensionMap.forEach((k, v) -> {
                    Map<String, TBDimension> map = v;
                    TBModule tbModule = moduleMap.get(k);
                    map.forEach((k1, v1) -> {
                        v1.setModuleId(tbModule.getId());
                        tbDimensionList.add(v1);
                    });
                });
                tbDimensionService.saveOrUpdateBatch(tbDimensionList);
            }
        } catch (Exception e) {
            log.error("请求出错", e);
            if (Objects.nonNull(tbAttachment)) {
                tbAttachmentService.deleteAttachment(UploadFileEnum.file, tbAttachment);
            }
            if (e instanceof BusinessException) {
                throw new BusinessException(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return ResultUtil.ok(Collections.singletonMap(SystemConstant.SUCCESS, true));
    }
}
