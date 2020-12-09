package com.qmth.wuda.teaching.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qmth.wuda.teaching.annotation.ApiJsonObject;
import com.qmth.wuda.teaching.annotation.ApiJsonProperty;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.bean.report.*;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.dto.DimensionFirstDto;
import com.qmth.wuda.teaching.dto.ExamStudentDto;
import com.qmth.wuda.teaching.entity.*;
import com.qmth.wuda.teaching.enums.MissEnum;
import com.qmth.wuda.teaching.enums.ModuleEnum;
import com.qmth.wuda.teaching.enums.PaperDifficultEnum;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.*;
import com.qmth.wuda.teaching.util.ResultUtil;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.ConvertUtils;
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
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 报告 前端控制器
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@Api(tags = "报告Controller")
@RestController
@RequestMapping("/${prefix.url.wuda}/report")
public class ReportController {
    private final static Logger log = LoggerFactory.getLogger(ReportController.class);

    @Resource
    TBLevelService tbLevelService;

    @Resource
    TEExamStudentService teExamStudentService;

    @Resource
    TEExamRecordService teExamRecordService;

    @Resource
    TEPaperService tePaperService;

    @Resource
    TEQuestionService teQuestionService;

    @Resource
    TBModuleService tbModuleService;

    @Resource
    TBDimensionService tbDimensionService;

    @Resource
    TEAnswerService teAnswerService;

    @ApiOperation(value = "个人报告")
    @RequestMapping(value = "personal", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result personal(
            @ApiJsonObject(name = "reportPersonal", value = {
                    @ApiJsonProperty(key = "schoolId", description = "学校id", required = true),
                    @ApiJsonProperty(key = "collegeId", description = "学院id", required = false),
                    @ApiJsonProperty(key = "studentNo", description = "考生学号", required = true)
            })
            @ApiParam(value = "个人报告信息", required = true) @RequestBody Map<String, Object> mapParameter) {
        if (Objects.isNull(mapParameter.get("schoolId")) || Objects.equals(mapParameter.get("schoolId"), "")) {
            throw new BusinessException("学校id为空");
        }
        Long schoolId = Long.parseLong(String.valueOf(mapParameter.get("schoolId")));
        if (Objects.isNull(mapParameter.get("studentNo")) || Objects.equals(mapParameter.get("studentNo"), "")) {
            throw new BusinessException("考生学号为空");
        }
        String studentNo = (String) mapParameter.get("studentNo");
        //报告第一页start
        List<TBLevel> tbLevelList = tbLevelService.findAll();
        if (Objects.isNull(tbLevelList) || tbLevelList.size() == 0) {
            throw new BusinessException("等级为空");
        }
        List<TBModule> tbModuleList = tbModuleService.findBySchoolId(schoolId);
        if (Objects.isNull(tbModuleList) || tbModuleList.size() == 0) {
            throw new BusinessException("模块为空");
        }
        ExamStudentDto examStudentDto = teExamStudentService.findByStudentNo(studentNo);
        if (Objects.isNull(examStudentDto)) {
            throw new BusinessException("考生信息为空");
        }
        TEPaper tePaper = tePaperService.findByExamIdAndCourseCode(examStudentDto.getExamId(), examStudentDto.getCourseCode());
        if (Objects.isNull(tePaper)) {
            throw new BusinessException("试卷信息为空");
        }
        List<TEQuestion> teQuestionList = teQuestionService.findByPaperId(tePaper.getId());
        if (Objects.isNull(teQuestionList) || teQuestionList.size() == 0) {
            throw new BusinessException("题目信息为空");
        }
        List<TEAnswer> teAnswerList = teAnswerService.findByExamRecordId(examStudentDto.getExamRecordId());
        if (Objects.isNull(teAnswerList) || teAnswerList.size() == 0) {
            throw new BusinessException("答题信息为空");
        }
        Map<String, TEAnswer> teAnswerMap = teAnswerList.stream().collect(Collectors.toMap(s -> s.getMainNumber() + "-" + s.getSubNumber(), Function.identity(), (dto1, dto2) -> dto1));
//        LinkedHashMap<String, TEQuestion> teQuestionMap = teQuestionList.stream().collect(
//                Collectors.toMap(s -> s.getMainNumber() + "-" + s.getSubNumber(), Function.identity(), (dto1, dto2) -> dto1))
//                .entrySet().stream()
//                .sorted(Map.Entry.comparingByKey())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        Map<String, TEQuestion> teQuestionMap = teQuestionList.stream().collect(Collectors.toMap(s -> s.getMainNumber() + "-" + s.getSubNumber(), Function.identity(), (dto1, dto2) -> dto1));

        Map<String, List<DimensionMasterysBean>> dimensionSecondMasterysBeanMap = new HashMap<>();
        LinkedMultiValueMap<String, TBDimension> dimensionSecondMap = new LinkedMultiValueMap<>();
        Map<String, Map<String, DimensionFirstDto>> dimensionFirstMap = new LinkedHashMap<>();
        tbModuleList.forEach(s -> {
            JSONObject jsonObject = JSONObject.parseObject(s.getDegree());
            if (Objects.nonNull(jsonObject)) {
                JSONArray jsonArray = jsonObject.getJSONArray("dimensionSecondMastery");
                if (Objects.nonNull(jsonArray) && jsonArray.size() > 0) {
                    List<DimensionMasterysBean> dimensionMasterysBeanList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String[] strs = String.valueOf(object.get("degree")).split(",");
                        dimensionMasterysBeanList.add(new DimensionMasterysBean((String) object.get("level"), Arrays.asList((Integer[]) ConvertUtils.convert(strs, Integer.class))));
                        dimensionSecondMasterysBeanMap.put(s.getCode(), dimensionMasterysBeanList);
                    }
                }
            }
            List<TBDimension> tbDimensionList = tbDimensionService.findByModuleIdAndCourseCode(s.getId(), examStudentDto.getCourseCode());
            if (Objects.nonNull(tbDimensionList) && tbDimensionList.size() > 0) {
                Map<String, DimensionFirstDto> dimensionMap = new LinkedHashMap<>();
                tbDimensionList.forEach(o -> {
                    if (!dimensionMap.containsKey(o.getKnowledgeFirst())) {
                        dimensionMap.put(o.getKnowledgeFirst(), new DimensionFirstDto(o.getModuleId(), s.getCode(), o.getCourseCode(), o.getKnowledgeFirst(), o.getIdentifierFirst(), s.getInfo(), s.getRemark()));
                    }
                    dimensionSecondMap.add(o.getIdentifierFirst(), o);
                });
                dimensionFirstMap.put(s.getName(), dimensionMap);
            }
        });
        PersonalReportBean personalReportBean = new PersonalReportBean();
        Gson gson = new Gson();
        ExamStudentBean examStudentBean = gson.fromJson(gson.toJson(examStudentDto), ExamStudentBean.class);
        List<LevelBean> levelBeanList = new ArrayList();
        examStudentBean.setLevels(levelBeanList);
        if (Objects.nonNull(tbLevelList) && tbLevelList.size() > 0) {
            tbLevelList.forEach(s -> {
                String[] strs = s.getDegree().split(",");
                levelBeanList.add(new LevelBean(s.getCode(), Arrays.asList((Integer[]) ConvertUtils.convert(strs, Integer.class))));
                if (examStudentDto.getMyScore().doubleValue() >= Double.parseDouble(strs[0]) && examStudentDto.getMyScore().doubleValue() <= Double.parseDouble(strs[1])) {
                    examStudentBean.setLevel(s.getCode());
                }
            });
        }
        personalReportBean.setStudent(examStudentBean);
        //报告第一页end

        //报告第二页start
        //学院分数
        SynthesisBean collegeScore = teExamRecordService.findByCollegeScore(examStudentDto.getSchoolId(), examStudentDto.getExamId(), examStudentDto.getCollegeId(), examStudentDto.getCourseCode());
        //班级分数
        SynthesisBean calssScore = teExamRecordService.findByClassScore(examStudentDto.getSchoolId(), examStudentDto.getExamId(), examStudentDto.getCollegeId(), examStudentDto.getClazz(), examStudentDto.getCourseCode());
        //获取实考人数
        Integer actualCount = teExamStudentService.findByActualCount(examStudentDto.getSchoolId(), examStudentDto.getExamId(), examStudentDto.getCollegeId(), MissEnum.FALSE.getValue());
        SynthesisBean finalSynthesis = new SynthesisBean(examStudentDto.getMyScore(), actualCount, examStudentDto.getFullScore());
        Integer lowScoreCount = teExamRecordService.getLowScoreByMe(examStudentDto.getSchoolId(), examStudentDto.getExamId(), examStudentDto.getCollegeId(), examStudentDto.getExamRecordId(), examStudentDto.getCourseCode());
        BigDecimal bigDecimal = new BigDecimal(lowScoreCount).divide(new BigDecimal(finalSynthesis.getActualCount()), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        finalSynthesis.setOverRate(bigDecimal);
        finalSynthesis.setCollegeScore(collegeScore);
        finalSynthesis.setClassScore(calssScore);

        BigDecimal difficult = finalSynthesis.getCollegeAvgScore().divide(tePaper.getTotalScore(), 2, BigDecimal.ROUND_HALF_UP);
        finalSynthesis.setDifficult(difficult);
        finalSynthesis.setDifficultInfo(PaperDifficultEnum.convertToCode(difficult.doubleValue()));
        CollegeBean collegeBean = new CollegeBean(finalSynthesis);
        personalReportBean.setCollege(collegeBean);
        //报告第二页end

        //诊断页start
        DiagnosisBean diagnosisBean = new DiagnosisBean();
        if (examStudentDto.getMyScore().doubleValue() >= examStudentDto.getPassScore().doubleValue()) {
            diagnosisBean.setResult(true);
        }

        List<DiagnosisDetailBean> diagnosisDetailBeanList = new ArrayList<>();
        //一级维度start
        dimensionFirstMap.forEach((k, v) -> {
            DiagnosisDetailBean diagnosisDetailBean = new DiagnosisDetailBean();
            diagnosisDetailBean.setName(k);
            ModuleBean moduleBean = new ModuleBean();
            List<ModuleDetailBean> dios = new ArrayList<>();
            v.forEach((k1, v1) -> {
                moduleBean.setInfo(v1.getInfo());
                moduleBean.setRemark(v1.getRemark());
                ModuleDetailBean moduleDetailBean = new ModuleDetailBean();
                moduleDetailBean.setName(v1.getKnowledgeFirst());
                teQuestionMap.forEach((k2, v2) -> {
                    if (Objects.nonNull(v1.getModuleCode()) && Objects.equals(v1.getModuleCode(), ModuleEnum.KNOWLEDGE.name().toLowerCase())) {
                        if (Objects.nonNull(v2.getKnowledge()) && Arrays.asList(v2.getKnowledge().split(",")).contains(v1.getIdentifierFirst())) {
                            v1.setSumScore(v1.getSumScore().add(v2.getScore()));
                            v1.setMyScore(v1.getMyScore().add(teAnswerMap.get(k2).getScore()));
                        }
                    } else if (Objects.nonNull(v1.getModuleCode()) && Objects.equals(v1.getModuleCode(), ModuleEnum.CAPABILITY.name().toLowerCase())) {
                        if (Objects.nonNull(v2.getCapability()) && Arrays.asList(v2.getCapability().split(",")).contains(v1.getIdentifierFirst())) {
                            v1.setSumScore(v1.getSumScore().add(v2.getScore()));
                            v1.setMyScore(v1.getMyScore().add(teAnswerMap.get(k2).getScore()));
                        }
                    }
                });
                moduleDetailBean.setRate(v1.getMyScore().divide(v1.getSumScore(), 2, BigDecimal.ROUND_HALF_UP));
                moduleDetailBean.setCollegeRate(new BigDecimal(10));
                dios.add(moduleDetailBean);
            });
            moduleBean.setDios(dios);
            diagnosisDetailBean.setModules(moduleBean);
            diagnosisDetailBeanList.add(diagnosisDetailBean);
        });
        //一级维度end

//        List<DiagnosisDetailBean> diagnosisDetailBeanList = new ArrayList<>();
//        DiagnosisDetailBean diagnosisDetailBean = new DiagnosisDetailBean();
//        diagnosisDetailBean.setName("知识");

//        ModuleBean moduleBean = new ModuleBean();
//        moduleBean.setInfo("测试1");
//        moduleBean.setRemark("测试1remark");
//        List<ModuleDetailBean> dios = new ArrayList<>();
//        ModuleDetailBean moduleDetailBean = new ModuleDetailBean();
//        moduleDetailBean.setName("记忆");
//        moduleDetailBean.setRate(new BigDecimal(8));
//        moduleDetailBean.setCollegeRate(new BigDecimal(10));
//        dios.add(moduleDetailBean);
//        moduleBean.setDios(dios);

//        diagnosisDetailBean.setModules(moduleBean);
//
//        DimensionBean dimensionBean = new DimensionBean();
//        dimensionBean.setMyScore(examStudentDto.getMyScore());
//        dimensionBean.setMasteryRate(new BigDecimal(9));
//        dimensionBean.setDioFullScore(new BigDecimal(100));
//        List<DimensionDetailBean> subDios = new ArrayList<>();
//        DimensionDetailBean dimensionDetailBean = new DimensionDetailBean();
//        dimensionDetailBean.setCode("A1");
//        dimensionDetailBean.setName("碱金属元素化合物");
//        dimensionDetailBean.setProficiency("H");
//        dimensionDetailBean.setScoreRate(new BigDecimal(10));
//        dimensionDetailBean.setCollegeAvgScore(new BigDecimal(60));
//        subDios.add(dimensionDetailBean);
//
//        dimensionBean.setSubDios(subDios);
////        dimensionBean.setMasterys(levelBeanList);
//
//        diagnosisDetailBean.setDetail(dimensionBean);

//        diagnosisDetailBeanList.add(diagnosisDetailBean);
        diagnosisBean.setList(diagnosisDetailBeanList);
        collegeBean.setDiagnosis(diagnosisBean);
        //诊断页end
        return ResultUtil.ok(personalReportBean);
    }

    @ApiOperation(value = "生成报告")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result create(
            @ApiJsonObject(name = "reportCreate", value = {
                    @ApiJsonProperty(key = "schoolId", description = "学校id", required = true),
                    @ApiJsonProperty(key = "examId", description = "考试id", required = true)
            })
            @ApiParam(value = "生成报告信息", required = true) @RequestBody Map<String, Object> mapParameter) {
        return ResultUtil.ok(Collections.singletonMap(SystemConstant.SUCCESS, true));
    }
}
