package com.qmth.wuda.teaching.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.bean.excel.ExcelCallback;
import com.qmth.wuda.teaching.bean.excel.ExcelError;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.dto.excel.*;
import com.qmth.wuda.teaching.entity.*;
import com.qmth.wuda.teaching.enums.ExceptionResultEnum;
import com.qmth.wuda.teaching.enums.ModuleEnum;
import com.qmth.wuda.teaching.enums.UploadFileEnum;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.*;
import com.qmth.wuda.teaching.util.ExcelUtil;
import com.qmth.wuda.teaching.util.JacksonUtil;
import com.qmth.wuda.teaching.util.ResultUtil;
import com.qmth.wuda.teaching.util.ServletUtil;
import io.swagger.annotations.*;
import net.sf.ehcache.CacheManager;
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

    @Resource
    TBLevelService tbLevelService;

    @ApiOperation(value = "更新缓存接口")
    @RequestMapping(value = "/updateCache", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result updateCache() {
        CacheManager cacheManager = CacheManager.getInstance();
        cacheManager.clearAll();
        return ResultUtil.ok(Collections.singletonMap(SystemConstant.SUCCESS, true));
    }

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

                ExamStudentImportDto examStudent = (ExamStudentImportDto) finalList.get(0).get(0).get(0);
                QueryWrapper<TEExam> teExamQueryWrapper = new QueryWrapper<>();
                teExamQueryWrapper.lambda().eq(TEExam::getSchoolId, tbSchool.getId())
                        .eq(TEExam::getCode, examStudent.getCourseCode());
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
                        .eq(TECourse::getCourseCode, examStudent.getCourseCode());
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
                    List<Object> examStudentImportDtoList = finalMap.get(i);
                    int min = 0;
                    int max = SystemConstant.MAX_IMPORT_SIZE, size = examStudentImportDtoList.size();
                    if (max >= size) {
                        max = size;
                    }
                    while (max <= size) {
                        List subList = examStudentImportDtoList.subList(min, max);
                        for (int y = 0; y < subList.size(); y++) {
                            line++;
                            ExamStudentImportDto examStudentImportDto = (ExamStudentImportDto) subList.get(y);
                            Map<String, Object> extendColumnMap = examStudentImportDto.getExtendColumn();
                            if (!collegeMap.containsKey(examStudentImportDto.getCollegeName())) {
                                TBSchoolCollege tbSchoolCollege = new TBSchoolCollege(tbSchool.getId(), examStudentImportDto.getCollegeName(), examStudentImportDto.getCollegeName());
                                collegeMap.put(examStudentImportDto.getCollegeName(), tbSchoolCollege);
                            }
                            if (!majorMap.containsKey(examStudentImportDto.getMajorName())) {
                                TBMajor tbMajor = new TBMajor(tbSchool.getId(), examStudentImportDto.getMajorName(), examStudentImportDto.getMajorName());
                                majorMap.put(examStudentImportDto.getMajorName(), tbMajor);
                            }
                            if (!studentMap.containsKey(examStudentImportDto.getIdentity())) {
                                TEStudent teStudent = new TEStudent(tbSchool.getId(), examStudentImportDto.getExamStudentName(), examStudentImportDto.getIdentity());
                                studentMap.put(examStudentImportDto.getIdentity(), teStudent);
                            }
                            if (!examStudentMap.containsKey(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity())) {
                                TEExamStudent teExamStudent = new TEExamStudent(teExam.getId(), studentMap.get(examStudentImportDto.getIdentity()).getId(), collegeMap.get(examStudentImportDto.getCollegeName()).getId(), majorMap.get(examStudentImportDto.getMajorName()).getId(), teCourse.getCourseName(), teCourse.getCourseCode(), examStudentImportDto.getExamStudentName(), examStudentImportDto.getIdentity(), examStudentImportDto.getIdcardNumber(), Objects.isNull(extendColumnMap) || extendColumnMap.size() == 0 ? 1 : 0, examStudentImportDto.getClassNo());
                                examStudentMap.put(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity(), teExamStudent);
                            }
                            if (!teacherMap.containsKey(examStudentImportDto.getTeacherName())) {
                                TBTeacher tbTeacher = new TBTeacher(tbSchool.getId(), collegeMap.get(examStudentImportDto.getCollegeName()).getId(), majorMap.get(examStudentImportDto.getMajorName()).getId(), examStudentImportDto.getTeacherName());
                                teacherMap.put(examStudentImportDto.getTeacherName(), tbTeacher);
                            }
                            TBTeacherExamStudent tbTeacherExamStudent = new TBTeacherExamStudent(teacherMap.get(examStudentImportDto.getTeacherName()).getId(), examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId());
                            teacherExamStudentMap.add(teacherMap.get(examStudentImportDto.getTeacherName()).getId(), tbTeacherExamStudent);

                            if (!examRecordMap.containsKey(examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId())) {
                                TEExamRecord teExamRecord = new TEExamRecord(teExam.getId(), tePaper.getId(), examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId(), new BigDecimal(examStudentImportDto.getObjectiveScore()), new BigDecimal(examStudentImportDto.getSubjectiveScore()), new BigDecimal(examStudentImportDto.getSumScore()), examStudentImportDto.getMarkDetail(), examStudentImportDto.getRemark());
                                examRecordMap.put(examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId(), teExamRecord);
                            }

                            Map<Long, TEAnswer> answerMap = new LinkedHashMap<>();
                            extendColumnMap.forEach((k, v) -> {
                                TEAnswer teAnswer = null;
                                String filterTitle = SystemConstant.filterQuestion(k);
                                log.info("filterTitle:{}", filterTitle);
                                TEQuestion teQuestion = teQuestionMap.get(filterTitle);
                                log.info("teQuestion:{}", JacksonUtil.parseJson(teQuestion));
                                Long examRecordId = examRecordMap.get(examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId()).getId();
                                if (!answerMap.containsKey(teQuestion.getId())) {
                                    teAnswer = new TEAnswer(teQuestion.getMainNumber(), teQuestion.getSubNumber(), teQuestion.getType(), examRecordId);
                                } else {
                                    teAnswer = answerMap.get(teQuestion.getId());
                                }
                                if ((k.contains("作答") || k.contains("选项")) && Objects.nonNull(v)) {
                                    teAnswer.setAnswer((String) v);
                                } else if ((k.contains("得分") || Objects.equals(SystemConstant.filterQuestion(k), filterTitle)) && Objects.nonNull(v)) {
                                    teAnswer.setScore(new BigDecimal(String.valueOf(v)));
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
            List<LinkedMultiValueMap<Integer, Object>> finalList = ExcelUtil.excelReader(file.getInputStream(), Lists.newArrayList(PaperImportDto.class, QuestionImportDto.class), new ExcelCallback() {
                @Override
                public List<LinkedMultiValueMap<Integer, Object>> callback(List<LinkedMultiValueMap<Integer, Object>> finalList, List<LinkedMultiValueMap<Integer, String>> finalColumnNameList) throws IllegalAccessException, IOException {
                    List<ExcelError> excelErrorList = new ArrayList<>();
                    for (int i = 0; i < finalList.size(); i++) {
                        LinkedMultiValueMap<Integer, Object> map = finalList.get(i);
                        List<Object> paperQuestionImportDtoList = map.get(i);
                        for (int y = 0; y < paperQuestionImportDtoList.size(); y++) {
                            List<ExcelError> excelErrorTemp = null;
                            if (paperQuestionImportDtoList.get(y) instanceof PaperImportDto) {
                                PaperImportDto paperImportDto = (PaperImportDto) paperQuestionImportDtoList.get(y);
                                excelErrorTemp = ExcelUtil.checkExcelField(paperImportDto, y, i);
                            } else if (paperQuestionImportDtoList.get(y) instanceof QuestionImportDto) {
                                QuestionImportDto questionImportDto = (QuestionImportDto) paperQuestionImportDtoList.get(y);
                                excelErrorTemp = ExcelUtil.checkExcelField(questionImportDto, y, i);
                            }
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
//                Map<String, TEExam> examMap = new LinkedHashMap<>();
                for (int i = 0; i < finalList.size(); i++) {
                    LinkedMultiValueMap<Integer, Object> finalMap = finalList.get(i);
                    List<Object> paperQuestionImportDtoList = finalMap.get(i);
                    int min = 0;
                    int max = SystemConstant.MAX_IMPORT_SIZE, size = paperQuestionImportDtoList.size();
                    if (max >= size) {
                        max = size;
                    }
                    while (max <= size) {
                        List subList = paperQuestionImportDtoList.subList(min, max);
                        for (int y = 0; y < subList.size(); y++) {
                            line++;
                            if (subList.get(y) instanceof PaperImportDto) {
                                PaperImportDto paperImportDto = (PaperImportDto) paperQuestionImportDtoList.get(y);
                                if (!paperMap.containsKey(paperImportDto.getPaperCode() + "_" + paperImportDto.getCourseCode())) {
                                    TEPaper tePaper = new TEPaper(paperImportDto.getCourseName(), paperImportDto.getCourseCode(), paperImportDto.getPaperCode(), paperImportDto.getPaperCode(), new BigDecimal(paperImportDto.getTotalScore()), new BigDecimal(paperImportDto.getPassScore()), Objects.nonNull(paperImportDto.getContribution()) && Objects.equals(paperImportDto.getContribution(), "是") ? 1 : 0, Objects.nonNull(paperImportDto.getContributionScore()) ? new BigDecimal(paperImportDto.getContributionScore()) : new BigDecimal(0));
                                    paperMap.put(paperImportDto.getPaperCode() + "_" + paperImportDto.getCourseCode(), tePaper);
                                }
                            } else if (subList.get(y) instanceof QuestionImportDto) {
                                QuestionImportDto questionImportDto = (QuestionImportDto) paperQuestionImportDtoList.get(y);
                                TEQuestion teQuestion = new TEQuestion(paperMap.get(questionImportDto.getPaperCode() + "_" + questionImportDto.getCourseCode()).getId(), Integer.parseInt(questionImportDto.getMainNumber()), Integer.parseInt(questionImportDto.getSubNumber()), questionImportDto.getType(), new BigDecimal(questionImportDto.getScore()), questionImportDto.getRule(), questionImportDto.getDescription(), questionImportDto.getKnowledge(), questionImportDto.getCapability());
                                questionMap.add(paperMap.get(questionImportDto.getPaperCode() + "_" + questionImportDto.getCourseCode()).getId(), teQuestion);
                            }
//                            QuestionImportDto paperAndQuestionImportDto = (QuestionImportDto) subList.get(y);
//                            if (!examMap.containsKey(paperAndQuestionImportDto.getExamCode())) {
//                                TEExam teExam = new TEExam(tbSchool.getId(), paperAndQuestionImportDto.getExamCode(), paperAndQuestionImportDto.getExamCode(), System.currentTimeMillis(), System.currentTimeMillis());
//                                examMap.put(paperAndQuestionImportDto.getExamCode(), teExam);
//                            }
//                            if (!paperMap.containsKey(paperAndQuestionImportDto.getPaperCode())) {
//                                TEPaper tePaper = new TEPaper(examMap.get(paperAndQuestionImportDto.getExamCode()).getId(), paperAndQuestionImportDto.getCourseName(), paperAndQuestionImportDto.getCourseCode(), paperAndQuestionImportDto.getPaperCode(), paperAndQuestionImportDto.getPaperCode(), new BigDecimal(100), new BigDecimal(60));
//                                paperMap.put(paperAndQuestionImportDto.getPaperCode(), tePaper);
//                            }
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
//                tePaperService.deleteAll();
                teQuestionService.deleteAll();
//                teExamService.deleteAll();

//                List<TEExam> examList = new ArrayList();
//                examMap.forEach((k, v) -> {
//                    examList.add(v);
//                });
//                teExamService.saveOrUpdateBatch(examList);

                List<TEPaper> paperList = new ArrayList();
                paperMap.forEach((k, v) -> {
                    paperList.add(v);
                });
                tePaperService.saveOrUpdateBatch(paperList);

                List<TEQuestion> questionList = new ArrayList();
                questionMap.forEach((k, v) -> {
                    questionList.addAll(v);
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
                QueryWrapper<TBSchool> tbSchoolQueryWrapper = new QueryWrapper<>();
                tbSchoolQueryWrapper.lambda().eq(TBSchool::getCode, "whdx");
                TBSchool tbSchool = tbSchoolService.getOne(tbSchoolQueryWrapper);
                Map<String, TECourse> courseMap = new LinkedHashMap<>();
                Map<String, TBModule> moduleMap = new LinkedHashMap<>();
                LinkedMultiValueMap<Long, TBDimension> dimensionMap = new LinkedMultiValueMap<>();
                for (int i = 0; i < finalList.size(); i++) {
                    LinkedMultiValueMap<Integer, Object> finalMap = finalList.get(i);
                    List<Object> dimensionImportDtoList = finalMap.get(i);
                    int min = 0;
                    int max = SystemConstant.MAX_IMPORT_SIZE, size = dimensionImportDtoList.size();
                    if (max >= size) {
                        max = size;
                    }
                    while (max <= size) {
                        List subList = dimensionImportDtoList.subList(min, max);
                        for (int y = 0; y < subList.size(); y++) {
                            line++;
                            DimensionImportDto dimensionImportDto = (DimensionImportDto) subList.get(y);
                            if (!courseMap.containsKey(dimensionImportDto.getCourseCode())) {
                                TECourse teCourse = new TECourse(tbSchool.getId(), dimensionImportDto.getCourseName(), dimensionImportDto.getCourseCode());
                                courseMap.put(dimensionImportDto.getCourseCode(), teCourse);
                            }
                            if (!moduleMap.containsKey(dimensionImportDto.getModuleName())) {
                                QueryWrapper<TBModule> tbModuleQueryWrapper = new QueryWrapper<>();
                                tbModuleQueryWrapper.lambda().eq(TBModule::getCode, ModuleEnum.convertToName(dimensionImportDto.getModuleName()).toLowerCase())
                                        .eq(TBModule::getSchoolId, tbSchool.getId());
                                moduleMap.put(dimensionImportDto.getModuleName(), tbModuleService.getOne(tbModuleQueryWrapper));
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
                teCourseService.deleteAll(tbSchool.getId(), new HashSet<>(courseMap.keySet()));
                tbDimensionService.deleteAll(new HashSet<>(dimensionMap.keySet()));

                List<TECourse> courseList = new ArrayList();
                courseMap.forEach((k, v) -> {
                    courseList.add(v);
                });
                teCourseService.saveOrUpdateBatch(courseList);

                List<TBDimension> tbDimensionList = new ArrayList<>();
                dimensionMap.forEach((k, v) -> {
                    tbDimensionList.addAll(v);
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

    @ApiOperation(value = "模块和等级导入接口")
    @RequestMapping(value = "/moduleLevel/import", method = RequestMethod.POST)
    @Transactional
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result moduleLevelImport(@ApiParam(value = "上传文件", required = true) @RequestParam MultipartFile file) {
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
            List<LinkedMultiValueMap<Integer, Object>> finalList = ExcelUtil.excelReader(file.getInputStream(), Lists.newArrayList(ModuleImportDto.class, LevelImportDto.class), new ExcelCallback() {
                @Override
                public List<LinkedMultiValueMap<Integer, Object>> callback(List<LinkedMultiValueMap<Integer, Object>> finalList, List<LinkedMultiValueMap<Integer, String>> finalColumnNameList) throws IllegalAccessException, IOException {
                    List<ExcelError> excelErrorList = new ArrayList<>();
                    for (int i = 0; i < finalList.size(); i++) {
                        LinkedMultiValueMap<Integer, Object> map = finalList.get(i);
                        List<Object> moduleLevelImportList = map.get(i);
                        for (int y = 0; y < moduleLevelImportList.size(); y++) {
                            List<ExcelError> excelErrorTemp = null;
                            if (moduleLevelImportList.get(y) instanceof ModuleImportDto) {
                                ModuleImportDto moduleImportDto = (ModuleImportDto) moduleLevelImportList.get(y);
                                excelErrorTemp = ExcelUtil.checkExcelField(moduleImportDto, y, i);
                            } else if (moduleLevelImportList.get(y) instanceof LevelImportDto) {
                                LevelImportDto levelImportDto = (LevelImportDto) moduleLevelImportList.get(y);
                                excelErrorTemp = ExcelUtil.checkExcelField(levelImportDto, y, i);
                            }
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
                Map<String, TBModule> moduleMap = new LinkedHashMap<>();
                LinkedMultiValueMap<String, TBLevel> levelMap = new LinkedMultiValueMap<>();
                for (int i = 0; i < finalList.size(); i++) {
                    LinkedMultiValueMap<Integer, Object> finalMap = finalList.get(i);
                    List<Object> moduleImportDtoList = finalMap.get(i);
                    int min = 0;
                    int max = SystemConstant.MAX_IMPORT_SIZE, size = moduleImportDtoList.size();
                    if (max >= size) {
                        max = size;
                    }
                    while (max <= size) {
                        List subList = moduleImportDtoList.subList(min, max);
                        for (int y = 0; y < subList.size(); y++) {
                            line++;
                            if (subList.get(y) instanceof ModuleImportDto) {
                                ModuleImportDto moduleImportDto = (ModuleImportDto) subList.get(y);
                                if (!moduleMap.containsKey(moduleImportDto.getName())) {
                                    TBModule tbModule = new TBModule(tbSchool.getId(), moduleImportDto.getName(), ModuleEnum.convertToName(moduleImportDto.getName()), moduleImportDto.getDescription(), moduleImportDto.getProficiency(), moduleImportDto.getRemark(), moduleImportDto.getProficiencyDegree());
                                    moduleMap.put(moduleImportDto.getName(), tbModule);
                                }
                            } else if (subList.get(y) instanceof LevelImportDto) {
                                LevelImportDto levelImportDto = (LevelImportDto) subList.get(y);
                                TBLevel tbLevel = new TBLevel(tbSchool.getId(), moduleMap.get(levelImportDto.getModuleName()).getId(), levelImportDto.getCode(), levelImportDto.getLevel(), levelImportDto.getLevelDegree(), levelImportDto.getDiagnoseResult(), levelImportDto.getLearnAdvice(), levelImportDto.getFormula());
                                levelMap.add(levelImportDto.getModuleName(), tbLevel);
                            }
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
                tbModuleService.deleteAll(tbSchool.getId(), new HashSet<>(moduleMap.keySet()));
                tbLevelService.deleteAll(tbSchool.getId());

                List<TBModule> moduleList = new ArrayList<>();
                List<TBLevel> tbLevelList = new ArrayList<>();
                moduleMap.forEach((k, v) -> {
                    moduleList.add(v);
                    tbLevelList.addAll(levelMap.get(k));
                });
                tbModuleService.saveOrUpdateBatch(moduleList);
                tbLevelService.saveOrUpdateBatch(tbLevelList);
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
