package com.qmth.wuda.teaching.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.bean.excel.ExcelCallback;
import com.qmth.wuda.teaching.bean.excel.ExcelError;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.dto.excel.DimensionImportDto;
import com.qmth.wuda.teaching.dto.excel.ExamStudentImportDto;
import com.qmth.wuda.teaching.dto.excel.PaperAndQuestionImportDto;
import com.qmth.wuda.teaching.entity.*;
import com.qmth.wuda.teaching.enums.ExceptionResultEnum;
import com.qmth.wuda.teaching.enums.ModuleEnum;
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
import java.math.BigDecimal;
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
                QueryWrapper<TBSchool> tbSchoolQueryWrapper = new QueryWrapper<>();
                tbSchoolQueryWrapper.lambda().eq(TBSchool::getCode, "whdx");
                TBSchool tbSchool = tbSchoolService.getOne(tbSchoolQueryWrapper);

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
                        teQuestionMap.put(s.getMainNumber() + "-" + s.getSubNumber(), s);
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
                LinkedMultiValueMap<Long, TBTeacherExamStudent> teacherExamStudentMap = new LinkedMultiValueMap<>();
                Map<Long, TEExamRecord> examRecordMap = new LinkedHashMap<>();
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
                                TEExamStudent teExamStudent = new TEExamStudent(teExam.getId(), studentMap.get(examStudentImportDto.getIdcardNumber()).getId(), collegeMap.get(examStudentImportDto.getCollegeName()).getId(), majorMap.get(examStudentImportDto.getMajorName()).getId(), teCourse.getCourseName(), teCourse.getCourseCode(), examStudentImportDto.getExamStudentName(), examStudentImportDto.getIdentity(), examStudentImportDto.getIdcardNumber(), examStudentImportDto.getGrade(), Objects.equals(examStudentImportDto.getMiss(), "是") ? 1 : 0, examStudentImportDto.getClassNo());
                                examStudentMap.put(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity(), teExamStudent);
                            }
                            if (!teacherMap.containsKey(examStudentImportDto.getTeacherName())) {
                                TBTeacher tbTeacher = new TBTeacher(tbSchool.getId(), collegeMap.get(examStudentImportDto.getCollegeName()).getId(), majorMap.get(examStudentImportDto.getMajorName()).getId(), examStudentImportDto.getTeacherName());
                                teacherMap.put(examStudentImportDto.getTeacherName(), tbTeacher);
                            }
                            TBTeacherExamStudent tbTeacherExamStudent = new TBTeacherExamStudent(teacherMap.get(examStudentImportDto.getTeacherName()).getId(), examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId());
                            teacherExamStudentMap.add(teacherMap.get(examStudentImportDto.getTeacherName()).getId(), tbTeacherExamStudent);

                            if (!examRecordMap.containsKey(examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId())) {
                                TEExamRecord teExamRecord = new TEExamRecord(teExam.getId(), tePaper.getId(), examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId(), new BigDecimal(examStudentImportDto.getObjectiveScore()), new BigDecimal(examStudentImportDto.getSubjectiveScore()), new BigDecimal(examStudentImportDto.getSumScore()));
                                examRecordMap.put(examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId(), teExamRecord);
                            }

                            Map<Long, TEAnswer> answerMap = new LinkedHashMap<>();
                            Map<String, String> extendColumnMap = examStudentImportDto.getExtendColumn();
                            extendColumnMap.forEach((k, v) -> {
                                TEAnswer teAnswer = null;
                                String filterTitle = SystemConstant.filterQuestion(k);
                                TEQuestion teQuestion = teQuestionMap.get(filterTitle);
                                Long examRecordId = examRecordMap.get(examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId()).getId();
                                if (!answerMap.containsKey(teQuestion.getId())) {
                                    teAnswer = new TEAnswer(teQuestion.getMainNumber(), teQuestion.getSubNumber(), teQuestion.getType(), examRecordId);
                                } else {
                                    teAnswer = answerMap.get(teQuestion.getId());
                                }
                                if (k.contains("作答") || k.contains("选项")) {
                                    teAnswer.setAnswer(v);
                                } else if (k.contains("得分") || Objects.equals(SystemConstant.filterQuestion(k), filterTitle)) {
                                    teAnswer.setScore(new BigDecimal(v));
                                }
                                teAnswer.setVersion(teAnswer.getVersion() + 1);
                                answerMap.put(teQuestion.getId(), teAnswer);
                            });

                            answerMap.forEach((k, v) -> {
                                teAnswerList.add(v);
                            });
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
                tbSchoolCollegeService.saveBatch(tbSchoolCollegeList);

                List<TBMajor> tbMajorList = new ArrayList<>();
                majorMap.forEach((k, v) -> {
                    tbMajorList.add(v);
                });
                tbMajorService.saveBatch(tbMajorList);

                List<TEStudent> teStudentList = new ArrayList();
                studentMap.forEach((k, v) -> {
                    teStudentList.add(v);
                });
                teStudentService.saveBatch(teStudentList);

                List<TEExamStudent> teExamStudentList = new ArrayList();
                examStudentMap.forEach((k, v) -> {
                    teExamStudentList.add(v);
                });
                teExamStudentService.saveBatch(teExamStudentList);

                List<TBTeacher> tbTeacherList = new ArrayList();
                teacherMap.forEach((k, v) -> {
                    tbTeacherList.add(v);
                });
                tbTeacherService.saveBatch(tbTeacherList);

                List<TBTeacherExamStudent> tbTeacherExamStudentList = new ArrayList();
                teacherExamStudentMap.forEach((k, v) -> {
                    tbTeacherExamStudentList.addAll(v);
                });
                tbTeacherExamStudentService.saveBatch(tbTeacherExamStudentList);

                List<TEExamRecord> teExamRecordList = new ArrayList();
                examRecordMap.forEach((k, v) -> {
                    teExamRecordList.add(v);
                });
                teExamRecordService.saveBatch(teExamRecordList);

                teAnswerService.saveBatch(teAnswerList);
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
                QueryWrapper<TBSchool> tbSchoolQueryWrapper = new QueryWrapper<>();
                tbSchoolQueryWrapper.lambda().eq(TBSchool::getCode, "whdx");
                TBSchool tbSchool = tbSchoolService.getOne(tbSchoolQueryWrapper);
                Map<String, TEPaper> paperMap = new LinkedHashMap<>();
                LinkedMultiValueMap<Long, TEQuestion> questionMap = new LinkedMultiValueMap<>();
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
                                TEPaper tePaper = new TEPaper(teExam.getId(), paperAndQuestionImportDto.getCourseName(), paperAndQuestionImportDto.getCourseCode(), paperAndQuestionImportDto.getPaperCode(), paperAndQuestionImportDto.getPaperCode(), new BigDecimal(100), new BigDecimal(60));
                                paperMap.put(paperAndQuestionImportDto.getPaperCode(), tePaper);
                            }
                            TEQuestion teQuestion = new TEQuestion(paperMap.get(paperAndQuestionImportDto.getPaperCode()).getId(), Integer.parseInt(paperAndQuestionImportDto.getMainNumber()), Integer.parseInt(paperAndQuestionImportDto.getSubNumber()), paperAndQuestionImportDto.getType(), new BigDecimal(paperAndQuestionImportDto.getScore()), paperAndQuestionImportDto.getRule(), paperAndQuestionImportDto.getDescription(), paperAndQuestionImportDto.getKnowledge(), paperAndQuestionImportDto.getCapability());
                            questionMap.add(paperMap.get(paperAndQuestionImportDto.getPaperCode()).getId(), teQuestion);
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
                tePaperService.saveBatch(paperList);

                List<TEQuestion> questionList = new ArrayList();
                questionMap.forEach((k, v) -> {
                    questionList.addAll(v);
                });
                teQuestionService.saveBatch(questionList);
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
                QueryWrapper<TBSchool> tbSchoolQueryWrapper = new QueryWrapper<>();
                tbSchoolQueryWrapper.lambda().eq(TBSchool::getCode, "whdx");
                TBSchool tbSchool = tbSchoolService.getOne(tbSchoolQueryWrapper);
                Map<String, TECourse> courseMap = new LinkedHashMap<>();
                Map<String, TBModule> moduleMap = new LinkedHashMap<>();
                LinkedMultiValueMap<Long, TBDimension> dimensionMap = new LinkedMultiValueMap<>();
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
                                TBModule tbModule = null;
                                if (Objects.equals(dimensionImportDto.getModuleName(), "知识")) {
                                    tbModule = new TBModule(tbSchool.getId(), dimensionImportDto.getModuleName(), ModuleEnum.convertToName(dimensionImportDto.getModuleName()), "课程标准规定的学科内容", "1.熟练：个人得分率≥85%\n" +
                                            "2.基本熟练：60%≤个人得分率＜85%\n" +
                                            "3.不熟练：个人得分率＜60%", "技能：学习和运用知识的心理加工过程；",
                                            "与以往传统考试单纯以部分对学生进行排序的评价方式不同，四维诊断评价能够综合分析你的学业发展长短板、优劣势，经由系统分析后出具详细的学业诊断评价报告。", "{\"dimensionSecondMastery\":[{\"level\":\"H\",\"degree\":\"85,100\"},{\"level\":\"M\",\"degree\":\"60,85\"},{\"level\":\"L\",\"degree\":\"0,60\"}]}");
                                } else {
                                    tbModule = new TBModule(tbSchool.getId(), dimensionImportDto.getModuleName(), ModuleEnum.convertToName(dimensionImportDto.getModuleName()), "经学习与训练内化而成的心理结构", null, null, null, null);
                                }
                                moduleMap.put(dimensionImportDto.getModuleName(), tbModule);
                            }
                            TBDimension tbDimension = new TBDimension(moduleMap.get(dimensionImportDto.getModuleName()).getId(), dimensionImportDto.getCourseName(), dimensionImportDto.getCourseCode(), dimensionImportDto.getKnowledgeFirst(), dimensionImportDto.getIdentifierFirst(), dimensionImportDto.getKnowledgeSecond(), dimensionImportDto.getIdentifierSecond(), dimensionImportDto.getDescription());
                            dimensionMap.add(moduleMap.get(dimensionImportDto.getModuleName()).getId(), tbDimension);
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
                teCourseService.saveBatch(courseList);

                List<TBModule> moduleList = new ArrayList();
                moduleMap.forEach((k, v) -> {
                    moduleList.add(v);
                });
                tbModuleService.saveBatch(moduleList);

                List<TBDimension> tbDimensionList = new ArrayList<>();
                dimensionMap.forEach((k, v) -> {
                    tbDimensionList.addAll(v);
                });
                tbDimensionService.saveBatch(tbDimensionList);
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
