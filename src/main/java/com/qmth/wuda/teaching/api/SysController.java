package com.qmth.wuda.teaching.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.qmth.wuda.teaching.annotation.ApiJsonObject;
import com.qmth.wuda.teaching.annotation.ApiJsonProperty;
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
import com.qmth.wuda.teaching.util.ResultUtil;
import com.qmth.wuda.teaching.util.ServletUtil;
import io.swagger.annotations.*;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
    TEPaperService tePaperService;

    @Resource
    TEPaperStructService tePaperStructService;

    @Resource
    TBLevelService tbLevelService;

    @Resource
    TEExamService teExamService;

    @Resource
    SequenceService sequenceService;

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

//    @ApiOperation(value = "考生导入接口")
//    @RequestMapping(value = "/examStudent/import", method = RequestMethod.POST)
//    @Transactional
//    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
//    public Result examStudentImport(@ApiParam(value = "上传文件", required = true) @RequestParam MultipartFile file) {
//        if (Objects.isNull(file) || Objects.equals(file, "")) {
//            throw new BusinessException(ExceptionResultEnum.ATTACHMENT_IS_NULL);
//        }
//        TBAttachment tbAttachment = null;
//        try {
//            tbAttachment = tbAttachmentService
//                    .saveAttachment(file, ServletUtil.getRequestMd5(), ServletUtil.getRequestPath(),
//                            UploadFileEnum.file, null, null);
//            if (Objects.isNull(tbAttachment)) {
//                throw new BusinessException(ExceptionResultEnum.ATTACHMENT_ERROR);
//            }
//            List<LinkedMultiValueMap<Integer, Object>> finalList = ExcelUtil.excelReader(file.getInputStream(), Lists.newArrayList(ExamStudentImportDto.class), new ExcelCallback() {
//                @Override
//                public List<LinkedMultiValueMap<Integer, Object>> callback(List<LinkedMultiValueMap<Integer, Object>> finalList, List<LinkedMultiValueMap<Integer, String>> finalColumnNameList) throws IllegalAccessException {
//                    List<ExcelError> excelErrorList = new ArrayList<>();
//                    for (int i = 0; i < finalList.size(); i++) {
//                        LinkedMultiValueMap<Integer, Object> map = finalList.get(i);
//                        List<Object> examStudentImportDtoList = map.get(i);
//                        for (int y = 0; y < examStudentImportDtoList.size(); y++) {
//                            ExamStudentImportDto examStudentImportDto = (ExamStudentImportDto) examStudentImportDtoList.get(y);
//                            List<ExcelError> excelErrorTemp = ExcelUtil.checkExcelField(examStudentImportDto, y, i);
//                            if (excelErrorTemp.size() > 0) {
//                                excelErrorList.addAll(excelErrorTemp);
//                            }
//                        }
//                    }
//                    if (excelErrorList.size() > 0) {
//                        throw new BusinessException(JSONObject.toJSONString(excelErrorList));
//                    }
//                    return finalList;
//                }
//            });
//            int line = 0;
//            //保存到数据库
//            if (Objects.nonNull(finalList) && finalList.size() > 0) {
//                QueryWrapper<TBSchool> tbSchoolQueryWrapper = new QueryWrapper<>();
//                tbSchoolQueryWrapper.lambda().eq(TBSchool::getCode, "whdx");
//                TBSchool tbSchool = tbSchoolService.getOne(tbSchoolQueryWrapper);
//
//                ExamStudentImportDto examStudent = (ExamStudentImportDto) finalList.get(0).get(0).get(0);
//                QueryWrapper<TEExam> teExamQueryWrapper = new QueryWrapper<>();
//                teExamQueryWrapper.lambda().eq(TEExam::getSchoolId, tbSchool.getId())
//                        .eq(TEExam::getCode, examStudent.getCourseCode());
//                TEExam teExam = teExamService.getOne(teExamQueryWrapper);
//
//                QueryWrapper<TEPaper> tePaperQueryWrapper = new QueryWrapper<>();
//                tePaperQueryWrapper.lambda().eq(TEPaper::getExamId, teExam.getId());
//                TEPaper tePaper = tePaperService.getOne(tePaperQueryWrapper);
//
//                QueryWrapper<TEQuestion> teQuestionQueryWrapper = new QueryWrapper<>();
//                teQuestionQueryWrapper.lambda().eq(TEQuestion::getPaperId, tePaper.getId());
//                List<TEQuestion> teQuestionList = teQuestionService.list(teQuestionQueryWrapper);
//                Map<String, TEQuestion> teQuestionMap = new LinkedHashMap<>();
//                if (Objects.nonNull(teQuestionList) && teQuestionList.size() > 0) {
//                    teQuestionList.forEach(s -> {
//                        teQuestionMap.put(s.getMainNumber() + "-" + s.getSubNumber(), s);
//                    });
//                }
//
//                QueryWrapper<TECourse> teCourseQueryWrapper = new QueryWrapper<>();
//                teCourseQueryWrapper.lambda().eq(TECourse::getSchoolId, tbSchool.getId())
//                        .eq(TECourse::getCourseCode, examStudent.getCourseCode());
//                TECourse teCourse = teCourseService.getOne(teCourseQueryWrapper);
//
//                Map<String, TBSchoolCollege> collegeMap = new LinkedHashMap<>();
//                Map<String, TEStudent> studentMap = new LinkedHashMap<>();
//                Map<String, TEExamStudent> examStudentMap = new LinkedHashMap<>();
//                Map<String, TBMajor> majorMap = new LinkedHashMap<>();
//                Map<String, TBTeacher> teacherMap = new LinkedHashMap<>();
//                LinkedMultiValueMap<Long, TBTeacherExamStudent> teacherExamStudentMap = new LinkedMultiValueMap<>();
//                Map<Long, TEExamRecord> examRecordMap = new LinkedHashMap<>();
//                List<TEAnswer> teAnswerList = new ArrayList();
//
//                for (int i = 0; i < finalList.size(); i++) {
//                    LinkedMultiValueMap<Integer, Object> finalMap = finalList.get(i);
//                    List<Object> examStudentImportDtoList = finalMap.get(i);
//                    int min = 0;
//                    int max = SystemConstant.MAX_IMPORT_SIZE, size = examStudentImportDtoList.size();
//                    if (max >= size) {
//                        max = size;
//                    }
//                    while (max <= size) {
//                        List subList = examStudentImportDtoList.subList(min, max);
//                        for (int y = 0; y < subList.size(); y++) {
//                            line++;
//                            ExamStudentImportDto examStudentImportDto = (ExamStudentImportDto) subList.get(y);
//                            Map<String, Object> extendColumnMap = examStudentImportDto.getExtendColumn();
//                            if (!collegeMap.containsKey(examStudentImportDto.getCollegeName())) {
//                                TBSchoolCollege tbSchoolCollege = new TBSchoolCollege(tbSchool.getId(), examStudentImportDto.getCollegeName(), examStudentImportDto.getCollegeName());
//                                collegeMap.put(examStudentImportDto.getCollegeName(), tbSchoolCollege);
//                            }
//                            if (!majorMap.containsKey(examStudentImportDto.getMajorName())) {
//                                TBMajor tbMajor = new TBMajor(tbSchool.getId(), examStudentImportDto.getMajorName(), examStudentImportDto.getMajorName());
//                                majorMap.put(examStudentImportDto.getMajorName(), tbMajor);
//                            }
//                            if (!studentMap.containsKey(examStudentImportDto.getIdentity())) {
//                                TEStudent teStudent = new TEStudent(tbSchool.getId(), examStudentImportDto.getExamStudentName(), examStudentImportDto.getIdentity());
//                                studentMap.put(examStudentImportDto.getIdentity(), teStudent);
//                            }
//                            if (!examStudentMap.containsKey(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity())) {
//                                TEExamStudent teExamStudent = new TEExamStudent(teExam.getId(), studentMap.get(examStudentImportDto.getIdentity()).getId(), collegeMap.get(examStudentImportDto.getCollegeName()).getId(), majorMap.get(examStudentImportDto.getMajorName()).getId(), teCourse.getCourseName(), teCourse.getCourseCode(), examStudentImportDto.getExamStudentName(), examStudentImportDto.getIdentity(), examStudentImportDto.getIdcardNumber(), Objects.isNull(extendColumnMap) || extendColumnMap.size() == 0 ? 1 : 0, examStudentImportDto.getClassNo());
//                                examStudentMap.put(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity(), teExamStudent);
//                            }
//                            if (!teacherMap.containsKey(examStudentImportDto.getTeacherName())) {
//                                TBTeacher tbTeacher = new TBTeacher(tbSchool.getId(), collegeMap.get(examStudentImportDto.getCollegeName()).getId(), majorMap.get(examStudentImportDto.getMajorName()).getId(), examStudentImportDto.getTeacherName());
//                                teacherMap.put(examStudentImportDto.getTeacherName(), tbTeacher);
//                            }
//                            TBTeacherExamStudent tbTeacherExamStudent = new TBTeacherExamStudent(teacherMap.get(examStudentImportDto.getTeacherName()).getId(), examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId());
//                            teacherExamStudentMap.add(teacherMap.get(examStudentImportDto.getTeacherName()).getId(), tbTeacherExamStudent);
//
//                            if (!examRecordMap.containsKey(examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId())) {
//                                TEExamRecord teExamRecord = new TEExamRecord(teExam.getId(), tePaper.getId(), examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId(), new BigDecimal(examStudentImportDto.getObjectiveScore()), new BigDecimal(examStudentImportDto.getSubjectiveScore()), new BigDecimal(examStudentImportDto.getSumScore()), examStudentImportDto.getMarkDetail(), examStudentImportDto.getRemark());
//                                examRecordMap.put(examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId(), teExamRecord);
//                            }
//
//                            Map<Long, TEAnswer> answerMap = new LinkedHashMap<>();
//                            extendColumnMap.forEach((k, v) -> {
//                                TEAnswer teAnswer = null;
//                                String filterTitle = SystemConstant.filterQuestion(k);
//                                log.info("filterTitle:{}", filterTitle);
//                                TEQuestion teQuestion = teQuestionMap.get(filterTitle);
//                                log.info("teQuestion:{}", JacksonUtil.parseJson(teQuestion));
//                                Long examRecordId = examRecordMap.get(examStudentMap.get(examStudentImportDto.getIdcardNumber() + "_" + examStudentImportDto.getIdentity()).getId()).getId();
//                                if (!answerMap.containsKey(teQuestion.getId())) {
//                                    teAnswer = new TEAnswer(teQuestion.getMainNumber(), teQuestion.getSubNumber(), teQuestion.getType(), examRecordId);
//                                } else {
//                                    teAnswer = answerMap.get(teQuestion.getId());
//                                }
//                                if ((k.contains("作答") || k.contains("选项")) && Objects.nonNull(v)) {
//                                    teAnswer.setAnswer((String) v);
//                                } else if ((k.contains("得分") || Objects.equals(SystemConstant.filterQuestion(k), filterTitle)) && Objects.nonNull(v)) {
//                                    teAnswer.setScore(new BigDecimal(String.valueOf(v)));
//                                }
//                                teAnswer.setVersion(teAnswer.getVersion() + 1);
//                                answerMap.put(teQuestion.getId(), teAnswer);
//                            });
//
//                            answerMap.forEach((k, v) -> {
//                                teAnswerList.add(v);
//                            });
//                        }
//                        if (max == size) {
//                            break;
//                        }
//                        min = max;
//                        max += SystemConstant.MAX_IMPORT_SIZE;
//                        if (max >= size) {
//                            max = size;
//                        }
//                    }
//                }
//                tbSchoolCollegeService.deleteAll();
//                teStudentService.deleteAll();
//                teExamStudentService.deleteAll();
//                tbMajorService.deleteAll();
//                tbTeacherService.deleteAll();
//                tbTeacherExamStudentService.deleteAll();
//                teExamRecordService.deleteAll();
//                teAnswerService.deleteAll();
//
//                List<TBSchoolCollege> tbSchoolCollegeList = new ArrayList();
//                collegeMap.forEach((k, v) -> {
//                    tbSchoolCollegeList.add(v);
//                });
//                tbSchoolCollegeService.saveBatch(tbSchoolCollegeList);
//
//                List<TBMajor> tbMajorList = new ArrayList<>();
//                majorMap.forEach((k, v) -> {
//                    tbMajorList.add(v);
//                });
//                tbMajorService.saveBatch(tbMajorList);
//
//                List<TEStudent> teStudentList = new ArrayList();
//                studentMap.forEach((k, v) -> {
//                    teStudentList.add(v);
//                });
//                teStudentService.saveBatch(teStudentList);
//
//                List<TEExamStudent> teExamStudentList = new ArrayList();
//                examStudentMap.forEach((k, v) -> {
//                    teExamStudentList.add(v);
//                });
//                teExamStudentService.saveBatch(teExamStudentList);
//
//                List<TBTeacher> tbTeacherList = new ArrayList();
//                teacherMap.forEach((k, v) -> {
//                    tbTeacherList.add(v);
//                });
//                tbTeacherService.saveBatch(tbTeacherList);
//
//                List<TBTeacherExamStudent> tbTeacherExamStudentList = new ArrayList();
//                teacherExamStudentMap.forEach((k, v) -> {
//                    tbTeacherExamStudentList.addAll(v);
//                });
//                tbTeacherExamStudentService.saveBatch(tbTeacherExamStudentList);
//
//                List<TEExamRecord> teExamRecordList = new ArrayList();
//                examRecordMap.forEach((k, v) -> {
//                    teExamRecordList.add(v);
//                });
//                teExamRecordService.saveBatch(teExamRecordList);
//
//                teAnswerService.saveBatch(teAnswerList);
//            }
//        } catch (Exception e) {
//            log.error("请求出错", e);
//            if (Objects.nonNull(tbAttachment)) {
//                tbAttachmentService.deleteAttachment(UploadFileEnum.file, tbAttachment);
//            }
//            if (e instanceof BusinessException) {
//                throw new BusinessException(e.getMessage());
//            } else {
//                throw new RuntimeException(e);
//            }
//        }
//        return ResultUtil.ok(Collections.singletonMap(SystemConstant.SUCCESS, true));
//    }

    @ApiOperation(value = "试卷题型导入接口")
    @RequestMapping(value = "/paperStruct/import", method = RequestMethod.POST)
    @Transactional
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result paperStructImport(@ApiParam(value = "上传文件", required = true) @RequestParam MultipartFile file,
                                    @ApiParam(value = "考试id", required = true) @RequestParam Long examId) {
        if (Objects.isNull(file) || Objects.equals(file, "")) {
            throw new BusinessException(ExceptionResultEnum.ATTACHMENT_IS_NULL);
        }
        if (Objects.isNull(examId) || Objects.equals(examId, "")) {
            throw new BusinessException("考试id不能为空");
        }
        TBAttachment tbAttachment = null;
        try {
            tbAttachment = tbAttachmentService
                    .saveAttachment(file, ServletUtil.getRequestMd5(), ServletUtil.getRequestPath(),
                            UploadFileEnum.file, null, null);
            if (Objects.isNull(tbAttachment)) {
                throw new BusinessException(ExceptionResultEnum.ATTACHMENT_ERROR);
            }
            List<LinkedMultiValueMap<Integer, Object>> finalList = ExcelUtil.excelReader(file.getInputStream(), Lists.newArrayList(PaperImportDto.class, PaperStructImportDto.class), new ExcelCallback() {
                @Override
                public List<LinkedMultiValueMap<Integer, Object>> callback(List<LinkedMultiValueMap<Integer, Object>> finalList, List<LinkedMultiValueMap<Integer, String>> finalColumnNameList) throws IllegalAccessException {
                    List<ExcelError> excelErrorList = new ArrayList<>();
                    for (int i = 0; i < finalList.size(); i++) {
                        LinkedMultiValueMap<Integer, Object> map = finalList.get(i);
                        List<Object> paperStructImportDtoList = map.get(i);
                        for (int y = 0; y < paperStructImportDtoList.size(); y++) {
                            List<ExcelError> excelErrorTemp = null;
                            if (paperStructImportDtoList.get(y) instanceof PaperImportDto) {
                                PaperImportDto paperImportDto = (PaperImportDto) paperStructImportDtoList.get(y);
                                excelErrorTemp = ExcelUtil.checkExcelField(paperImportDto, y, i);
                            } else if (paperStructImportDtoList.get(y) instanceof PaperStructImportDto) {
                                PaperStructImportDto paperStructImportDto = (PaperStructImportDto) paperStructImportDtoList.get(y);
                                excelErrorTemp = ExcelUtil.checkExcelField(paperStructImportDto, y, i);
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
            //保存到数据库
            if (Objects.nonNull(finalList) && finalList.size() > 0) {
                Map<String, TEPaper> paperMap = new LinkedHashMap<>();
                LinkedMultiValueMap<Long, TEPaperStruct> paperStructMap = new LinkedMultiValueMap<>();
                for (int i = 0; i < finalList.size(); i++) {
                    LinkedMultiValueMap<Integer, Object> finalMap = finalList.get(i);
                    List<Object> paperStructImportDtoList = finalMap.get(i);
                    int min = 0;
                    int max = SystemConstant.MAX_IMPORT_SIZE, size = paperStructImportDtoList.size();
                    if (max >= size) {
                        max = size;
                    }
                    while (max <= size) {
                        List subList = paperStructImportDtoList.subList(min, max);
                        for (int y = 0; y < subList.size(); y++) {
                            if (subList.get(y) instanceof PaperImportDto) {
                                PaperImportDto paperImportDto = (PaperImportDto) paperStructImportDtoList.get(y);
                                if (!paperMap.containsKey(paperImportDto.getPaperCode() + "_" + paperImportDto.getCourseCode())) {
                                    TEPaper tePaper = new TEPaper(examId, paperImportDto.getPaperCode(), paperImportDto.getPaperCode(), new BigDecimal(paperImportDto.getTotalScore()), new BigDecimal(paperImportDto.getPassScore()), Objects.nonNull(paperImportDto.getContribution()) && Objects.equals(paperImportDto.getContribution(), "是") ? 1 : 0, Objects.nonNull(paperImportDto.getContributionScore()) ? new BigDecimal(paperImportDto.getContributionScore()) : new BigDecimal(0), paperImportDto.getCourseName(), paperImportDto.getCourseCode());
                                    paperMap.put(paperImportDto.getPaperCode() + "_" + paperImportDto.getCourseCode(), tePaper);
                                }
                            } else if (subList.get(y) instanceof PaperStructImportDto) {
                                PaperStructImportDto paperStructImportDto = (PaperStructImportDto) paperStructImportDtoList.get(y);
                                TEPaperStruct tePaperStruct = new TEPaperStruct(paperMap.get(paperStructImportDto.getPaperCode() + "_" + paperStructImportDto.getCourseCode()).getId(), paperStructImportDto.getMainNumber().intValue(), paperStructImportDto.getSubNumber().intValue(), paperStructImportDto.getType(), new BigDecimal(paperStructImportDto.getScore()), paperStructImportDto.getRule(), paperStructImportDto.getDescription(), paperStructImportDto.getKnowledge(), paperStructImportDto.getCapability());
                                paperStructMap.add(paperMap.get(paperStructImportDto.getPaperCode() + "_" + paperStructImportDto.getCourseCode()).getId(), tePaperStruct);
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
                List<TEPaper> paperList = new ArrayList();
                paperMap.forEach((k, v) -> {
                    QueryWrapper<TEPaper> tePaperQueryWrapper = new QueryWrapper<>();
                    tePaperQueryWrapper.lambda().eq(TEPaper::getExamId, examId)
                            .eq(TEPaper::getCode, v.getCode())
                            .eq(TEPaper::getCourseCode, v.getCourseCode());
                    TEPaper tePaper = tePaperService.getOne(tePaperQueryWrapper);
                    if (Objects.nonNull(tePaper)) {
                        tePaperStructService.deleteAll(tePaper.getId());
                    }
                    tePaperService.remove(tePaperQueryWrapper);
                    paperList.add(v);
                });
                tePaperService.saveOrUpdateBatch(paperList);

                List<TEPaperStruct> paperStructList = new ArrayList();
                paperStructMap.forEach((k, v) -> {
                    paperStructList.addAll(v);
                });
                tePaperStructService.saveOrUpdateBatch(paperStructList);
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
                public List<LinkedMultiValueMap<Integer, Object>> callback(List<LinkedMultiValueMap<Integer, Object>> finalList, List<LinkedMultiValueMap<Integer, String>> finalColumnNameList) throws IllegalAccessException {
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
            //保存到数据库
            if (Objects.nonNull(finalList) && finalList.size() > 0) {
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
                            DimensionImportDto dimensionImportDto = (DimensionImportDto) subList.get(y);
                            if (!moduleMap.containsKey(dimensionImportDto.getModuleName())) {
                                QueryWrapper<TBModule> tbModuleQueryWrapper = new QueryWrapper<>();
                                tbModuleQueryWrapper.lambda().eq(TBModule::getCode, ModuleEnum.convertToName(dimensionImportDto.getModuleName()).toLowerCase())
                                        .eq(TBModule::getCourseCode, dimensionImportDto.getCourseCode());
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
                tbDimensionService.deleteAll(new HashSet<>(dimensionMap.keySet()));
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
                public List<LinkedMultiValueMap<Integer, Object>> callback(List<LinkedMultiValueMap<Integer, Object>> finalList, List<LinkedMultiValueMap<Integer, String>> finalColumnNameList) throws IllegalAccessException {
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
            //保存到数据库
            if (Objects.nonNull(finalList) && finalList.size() > 0) {
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
                            if (subList.get(y) instanceof ModuleImportDto) {
                                ModuleImportDto moduleImportDto = (ModuleImportDto) subList.get(y);
                                if (!moduleMap.containsKey(moduleImportDto.getName())) {
                                    TBModule tbModule = new TBModule(moduleImportDto.getName(), ModuleEnum.convertToName(moduleImportDto.getName()), moduleImportDto.getDescription(), moduleImportDto.getProficiency(), moduleImportDto.getRemark(), moduleImportDto.getProficiencyDegree(), moduleImportDto.getPaperCode(), moduleImportDto.getCourseName(), moduleImportDto.getCourseCode());
                                    moduleMap.put(moduleImportDto.getName(), tbModule);
                                }
                            } else if (subList.get(y) instanceof LevelImportDto) {
                                LevelImportDto levelImportDto = (LevelImportDto) subList.get(y);
                                TBLevel tbLevel = new TBLevel(moduleMap.get(levelImportDto.getModuleName()).getId(), levelImportDto.getCode(), levelImportDto.getLevel(), levelImportDto.getLevelDegree(), levelImportDto.getDiagnoseResult(), levelImportDto.getLearnAdvice(), levelImportDto.getFormula());
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
                List<TBModule> moduleList = new ArrayList<>();
                List<TBLevel> tbLevelList = new ArrayList<>();
                moduleMap.forEach((k, v) -> {
                    QueryWrapper<TBModule> tbModuleQueryWrapper = new QueryWrapper<>();
                    tbModuleQueryWrapper.lambda().eq(TBModule::getPaperCode, v.getPaperCode())
                            .eq(TBModule::getCourseCode, v.getCourseCode())
                            .eq(TBModule::getCode, v.getCode().toLowerCase());
                    TBModule tbModule = tbModuleService.getOne(tbModuleQueryWrapper);
                    if (Objects.nonNull(tbModule)) {
                        tbLevelService.deleteAll(tbModule.getId());
                        tbModuleService.remove(tbModuleQueryWrapper);
                    }
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

    @ApiOperation(value = "创建考试信息接口")
    @RequestMapping(value = "/create/exam", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    @Transactional
    public Result createExam(
            @ApiJsonObject(name = "sysCreateExam", value = {
                    @ApiJsonProperty(key = "examName", description = "考试名称"),
                    @ApiJsonProperty(key = "examId", description = "考试id"),
                    @ApiJsonProperty(key = "examCode", description = "考试编码"),
                    @ApiJsonProperty(key = "accessKey", description = "密钥key"),
                    @ApiJsonProperty(key = "accessSecret", description = "密钥secret"),
            })
            @ApiParam(value = "创建考试信息", required = true) @RequestBody Map<String, Object> mapParameter) {
        if (Objects.isNull(mapParameter.get("examName")) || Objects.equals(mapParameter.get("examName"), "")) {
            throw new BusinessException("考试名称不能为空");
        }
        String examName = (String) mapParameter.get("examName");
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
        if (Objects.isNull(mapParameter.get("accessKey")) || Objects.equals(mapParameter.get("accessKey"), "")) {
            throw new BusinessException("密钥key不能为空");
        }
        String accessKey = (String) mapParameter.get("accessKey");
        if (Objects.isNull(mapParameter.get("accessSecret")) || Objects.equals(mapParameter.get("accessSecret"), "")) {
            throw new BusinessException("密钥secret不能为空");
        }
        String accessSecret = (String) mapParameter.get("accessSecret");
        teExamService.saveExam(examName, examId, examCode, accessKey, accessSecret);
        return ResultUtil.ok(Collections.singletonMap(SystemConstant.SUCCESS, true));
    }

    @ApiOperation(value = "创建科目信息接口")
    @RequestMapping(value = "/create/course", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    @Transactional
    public Result createCourse(
            @ApiJsonObject(name = "sysCreateCourse", value = {
                    @ApiJsonProperty(key = "examId", description = "考试id"),
                    @ApiJsonProperty(key = "courseNames", description = "科目名称数组"),
            })
            @ApiParam(value = "创建考试科目信息", required = true) @RequestBody Map<String, Object> mapParameter) {
        Long examId = null;
        if (Objects.nonNull(mapParameter.get("examId")) && !Objects.equals(mapParameter.get("examId"), "")) {
            examId = Long.parseLong(String.valueOf(mapParameter.get("examId")));
        }
        if (Objects.isNull(mapParameter.get("courseNames")) || Objects.equals(mapParameter.get("courseNames"), "")) {
            throw new BusinessException("科目名称不能为空");
        }
        List<String> courseNameList = (List<String>) mapParameter.get("courseNames");
        Long finalExamId = examId;
        courseNameList.forEach(s -> {
            TECourse teCourse = null;
            if (Objects.nonNull(finalExamId)) {
                QueryWrapper<TECourse> teCourseQueryWrapper = new QueryWrapper<>();
                teCourseQueryWrapper.lambda().eq(TECourse::getExamId, finalExamId)
                        .eq(TECourse::getCourseName, s);
                int count = teCourseService.count(teCourseQueryWrapper);
                if (count == 0) {
                    teCourse = new TECourse(finalExamId, s, String.valueOf(sequenceService.selectNextVal()));
                }
            } else {
                QueryWrapper<TECourse> teCourseQueryWrapper = new QueryWrapper<>();
                teCourseQueryWrapper.lambda().eq(TECourse::getCourseName, s);
                int count = teCourseService.count(teCourseQueryWrapper);
                if (count == 0) {
                    teCourse = new TECourse(s, String.valueOf(sequenceService.selectNextVal()));
                }
            }
            teCourseService.saveOrUpdate(teCourse);
        });
        return ResultUtil.ok(Collections.singletonMap(SystemConstant.SUCCESS, true));
    }
}
