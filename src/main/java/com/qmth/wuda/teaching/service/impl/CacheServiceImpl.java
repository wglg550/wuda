package com.qmth.wuda.teaching.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmth.wuda.teaching.bean.report.*;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.dto.*;
import com.qmth.wuda.teaching.entity.*;
import com.qmth.wuda.teaching.enums.MissEnum;
import com.qmth.wuda.teaching.enums.ModuleEnum;
import com.qmth.wuda.teaching.enums.PaperDifficultEnum;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.*;
import com.qmth.wuda.teaching.util.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Description: ehcache 服务实现类
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/6/27
 */
@Service
public class CacheServiceImpl implements CacheService {
    private final static Logger log = LoggerFactory.getLogger(CacheServiceImpl.class);

    @Resource
    TBLevelService tbLevelService;

    @Resource
    TEExamStudentService teExamStudentService;

    @Resource
    TEExamRecordService teExamRecordService;

    @Resource
    TEPaperService tePaperService;

    @Resource
    TEPaperStructService tePaperStructService;

    @Resource
    TBModuleService tbModuleService;

    @Resource
    TBDimensionService tbDimensionService;

    @Resource
    TEAnswerService teAnswerService;

    /**
     * 生成个人报告
     *
     * @param examId
     * @param examStudentId
     * @param courseCode
     * @return
     */
    @Override
//    @Cacheable(value = "personal_report_cache", key = "#examId + '-' + #examStudentId + '-' + #courseCode")
    public PersonalReportBean addPersonalReport(Long examId, Long examStudentId, String courseCode) {
        //报告第一页start
        ExamStudentDto examStudentDto = teExamStudentService.findById(examStudentId);
        if (Objects.isNull(examStudentDto)) {
            throw new BusinessException("考生信息为空");
        }
        List<TBModule> tbModuleList = tbModuleService.findByCourseCode(courseCode);
        if (Objects.isNull(tbModuleList) || tbModuleList.size() == 0) {
            throw new BusinessException("模块为空");
        }
        LinkedMultiValueMap<String, TBLevel> tbLevelList = new LinkedMultiValueMap<>();
        tbModuleList.forEach(s -> {
            tbLevelList.put(s.getName(), tbLevelService.findByModuleId(s.getId()));
        });
        if (Objects.isNull(tbLevelList) || tbLevelList.size() == 0) {
            throw new BusinessException("等级为空");
        }
        TEPaper tePaper = tePaperService.findByExamIdAndCodeAndCourseCode(examId, examStudentDto.getPaperCode(), courseCode);
        if (Objects.isNull(tePaper)) {
            throw new BusinessException("试卷信息为空");
        }
        List<TEPaperStruct> tePaperStructList = tePaperStructService.findByPaperId(tePaper.getId());
        if (Objects.isNull(tePaperStructList) || tePaperStructList.size() == 0) {
            throw new BusinessException("试卷结构信息为空");
        }
        AtomicReference<BigDecimal> paperStructScore = new AtomicReference<>(new BigDecimal(0));
        Map<String, TEPaperStruct> tePaperStructMap = new LinkedHashMap<>();
        tePaperStructList.forEach(s -> {
            paperStructScore.set(paperStructScore.get().add(s.getScore()));
            tePaperStructMap.put(s.getMainNumber() + "-" + s.getSubNumber(), s);
        });

        Map<String, List<DimensionMasterysDto>> dimensionSecondMasterysDtoMap = new HashMap<>();
        Map<String, Map<String, DimensionFirstDto>> dimensionFirstMap = new LinkedHashMap<>();
        Map<String, Map<String, DimensionSecondDto>> dimensionSecondMap = new LinkedHashMap<>();
        Gson gson = new Gson();
        tbModuleList.forEach(s -> {
            if (Objects.nonNull(s.getDegree()) && !s.getDegree().contains("无")) {
                String[] strDegrees = s.getDegree().split(";");
                List<DimensionMasterysDto> dimensionMasterysDtoList = new ArrayList<>();
                for (int i = 0; i < strDegrees.length; i++) {
                    String str = strDegrees[i];
                    String[] strs = str.split(":");
                    String[] grades = strs[1].split(",");
                    String gradesO = grades[0].replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\[", "").replaceAll("]", "");
                    String gradesT = grades[1].replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\[", "").replaceAll("]", "");
                    List<Double> gradeDouble = new ArrayList<>();
                    gradeDouble.add(Double.parseDouble(gradesO) * 100);
                    gradeDouble.add(Double.parseDouble(gradesT) * 100);
//                    dimensionMasterysBeanList.add(new DimensionMasterysBean(strs[0], Arrays.asList((Integer[]) ConvertUtils.convert(strs, Integer.class))));
                    dimensionMasterysDtoList.add(new DimensionMasterysDto(strs[0], gradeDouble, grades));
                    dimensionSecondMasterysDtoMap.put(s.getName(), dimensionMasterysDtoList);
                }
            }
            List<TBDimension> tbDimensionList = tbDimensionService.findByModuleIdAndCourseCode(s.getId(), examStudentDto.getCourseCode());
            if (Objects.nonNull(tbDimensionList) && tbDimensionList.size() > 0) {
                Map<String, DimensionFirstDto> dimensionMap = new LinkedHashMap<>();
                Map<String, DimensionSecondDto> dimensionSMap = new LinkedHashMap<>();
                tbDimensionList.forEach(o -> {
                    DimensionSecondDto dimensionSecondDto = gson.fromJson(gson.toJson(o), DimensionSecondDto.class);
                    dimensionSecondDto.setModuleCode(s.getCode());
                    if (!dimensionMap.containsKey(o.getKnowledgeFirst())) {
                        dimensionMap.put(o.getKnowledgeFirst(), new DimensionFirstDto(o.getModuleId(), s.getCode(), o.getCourseCode(), o.getKnowledgeFirst(), o.getIdentifierFirst(), s.getDescription(), s.getRemark(), Objects.nonNull(o.getIdentifierSecond()) ? new LinkedHashSet<>(Arrays.asList(dimensionSecondDto)) : null, o.getDescription()));
                    } else {
                        if (Objects.nonNull(dimensionMap.get(o.getKnowledgeFirst()).getIdentifierSecond())) {
                            dimensionMap.get(o.getKnowledgeFirst()).getIdentifierSecond().add(dimensionSecondDto);
                        }
                    }
                    if (Objects.nonNull(o.getIdentifierSecond())) {
                        dimensionSMap.put(o.getIdentifierSecond(), dimensionSecondDto);
                    }
                });
                dimensionFirstMap.put(s.getName(), dimensionMap);
                dimensionSecondMap.put(s.getName(), dimensionSMap);
            }
        });

        //维度过滤
        Map<String, Map<String, DimensionFirstDto>> studentDimensionFirstMap = new LinkedHashMap<>();
        Map<String, Map<String, DimensionSecondDto>> studentDimensionSecondMap = new LinkedHashMap<>();
        Map<String, BigDecimal> studentDimensionScoreMap = new LinkedHashMap<>();
        dimensionFirstMap.forEach((k, v) -> {
            String moduleCode = ModuleEnum.convertToSqlByCode(k);
            List<StudentDimensionDto> studentDimensions = tePaperStructService.findStudentDimension(examStudentDto.getExamId(), tePaper.getId(), examStudentDto.getStudentNo(), examStudentDto.getCourseCode(), moduleCode);
            for (StudentDimensionDto s : studentDimensions) {
                if (Objects.isNull(studentDimensionScoreMap.get(k))) {
                    studentDimensionScoreMap.put(k, s.getDimensionScore());
                } else {
                    BigDecimal bigDecimal = studentDimensionScoreMap.get(k);
                    bigDecimal = bigDecimal.add(s.getDimensionScore());
                    studentDimensionScoreMap.put(k, bigDecimal);
                }
                v.forEach((k1, v1) -> {
                    if (Arrays.asList(s.getDimension().split(",")).contains(v1.getIdentifierFirst())) {//一级维度
                        Map<String, DimensionFirstDto> dimensionFirstDtoMap = studentDimensionFirstMap.get(k);
                        if (Objects.isNull(dimensionFirstDtoMap)) {
                            dimensionFirstDtoMap = new LinkedHashMap<>();
                        }
                        dimensionFirstDtoMap.put(k1, v1);
                        studentDimensionFirstMap.put(k, dimensionFirstDtoMap);
                    } else if (Objects.nonNull(v1.getIdentifierSecond())) {//二级维度
                        for (DimensionSecondDto dimensionSecondDto : v1.getIdentifierSecond()) {
                            if (Arrays.asList(s.getDimension().split(",")).contains(dimensionSecondDto.getIdentifierSecond())) {
                                Map<String, DimensionFirstDto> dimensionFirstDtoMap = studentDimensionFirstMap.get(k);
                                if (Objects.isNull(dimensionFirstDtoMap)) {
                                    dimensionFirstDtoMap = new LinkedHashMap<>();
                                }
                                dimensionFirstDtoMap.put(k1, v1);
                                studentDimensionFirstMap.put(k, dimensionFirstDtoMap);

                                Map<String, DimensionSecondDto> dimensionSecondDtoMap = studentDimensionSecondMap.get(k);
                                if (Objects.isNull(dimensionSecondDtoMap)) {
                                    dimensionSecondDtoMap = new LinkedHashMap<>();
                                }
                                dimensionSecondDtoMap.put(dimensionSecondDto.getIdentifierSecond(), dimensionSecondMap.get(k).get(dimensionSecondDto.getIdentifierSecond()));
                                studentDimensionSecondMap.put(k, dimensionSecondDtoMap);
                            }
                        }
                    }
                });
            }
        });

        PersonalReportBean personalReportBean = new PersonalReportBean();
        ExamStudentBean examStudentBean = gson.fromJson(gson.toJson(examStudentDto), ExamStudentBean.class);
        List<LevelBean> levelBeanList = new ArrayList();
        examStudentBean.setLevels(levelBeanList);

        BigDecimal finalMyScore = tePaper.getContribution() == 1 ? tePaper.getContributionScore() : examStudentDto.getMyScore();
        SystemConstant.levelMap.forEach((k, v) -> {
            levelBeanList.add(new LevelBean(k, v));
            if (finalMyScore.doubleValue() >= Double.parseDouble(String.valueOf(v.get(0))) && finalMyScore.doubleValue() <= Double.parseDouble(String.valueOf(v.get(1)))) {
                examStudentBean.setLevel(k);
            }
        });

        personalReportBean.setStudent(examStudentBean);
        //报告第一页end

        //报告第二页start
        //学院分数
        SynthesisBean collegeScore = teExamRecordService.findByCollegeScore(examStudentDto.getExamId(), examStudentDto.getCollegeId(), examStudentDto.getCourseCode());
        //班级分数
        SynthesisBean calssScore = teExamRecordService.findByClassScore(examStudentDto.getExamId(), examStudentDto.getClazz(), examStudentDto.getCourseCode());
        //获取实考人数
        Integer actualCount = teExamStudentService.findByActualCount(examStudentDto.getExamId(), examStudentDto.getCollegeId(), examStudentDto.getCourseCode(), MissEnum.NORMAL.getValue());
        BigDecimal bigActualCount = new BigDecimal(actualCount);
        SynthesisBean finalSynthesis = new SynthesisBean(examStudentDto.getMyScore(), actualCount, examStudentDto.getFullScore());
        Integer lowScoreCount = teExamRecordService.getLowScoreByMe(examStudentDto.getExamId(), examStudentDto.getCollegeId(), examStudentDto.getExamRecordId(), examStudentDto.getCourseCode());
        BigDecimal fullRate = new BigDecimal(100);
        BigDecimal bigDecimal = (Objects.nonNull(lowScoreCount) && lowScoreCount.intValue() > 0) && (Objects.nonNull(actualCount) && actualCount.intValue() > 0) ? new BigDecimal(lowScoreCount).divide(bigActualCount.subtract(new BigDecimal(1)), SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate) : BigDecimal.ZERO;
        finalSynthesis.setOverRate(bigDecimal);
        finalSynthesis.setCollegeScore(collegeScore);
        finalSynthesis.setClassScore(calssScore);

        BigDecimal difficult = Objects.nonNull(tePaper.getTotalScore()) && tePaper.getTotalScore().doubleValue() > 0 ? finalSynthesis.getCollegeAvgScore().divide(tePaper.getTotalScore(), SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;
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
        diagnosisBean.setAssignedScore(tePaper.getContribution() == 1 ? true : false);

        List<DiagnosisDetailBean> diagnosisDetailBeanList = new ArrayList<>();
        //一级维度start
        studentDimensionFirstMap.forEach((k, v) -> {
            BigDecimal myScore = studentDimensionScoreMap.get(k);
            DiagnosisDetailBean diagnosisDetailBean = new DiagnosisDetailBean();
            if (Objects.nonNull(tbLevelList.get(k))) {
                tbLevelList.get(k).forEach(s -> {
                    String[] strs = s.getDegree().split(",");
                    BigDecimal oneB = new BigDecimal(strs[0].replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\[", "").replaceAll("]", ""));
                    BigDecimal twoB = new BigDecimal(strs[1].replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\[", "").replaceAll("]", ""));
                    if (strs[0].contains("[") && strs[1].contains("]")) {
                        if (oneB.doubleValue() <= myScore.doubleValue() && myScore.doubleValue() <= twoB.doubleValue()) {
                            diagnosisDetailBean.setResult(s.getDiagnoseResult());
                            diagnosisDetailBean.setAdvice(s.getLearnAdvice());
                            return;
                        }
                    } else if (strs[0].contains("[") && strs[1].contains(")")) {
                        if (oneB.doubleValue() <= myScore.doubleValue() && myScore.doubleValue() < twoB.doubleValue()) {
                            diagnosisDetailBean.setResult(s.getDiagnoseResult());
                            diagnosisDetailBean.setAdvice(s.getLearnAdvice());
                            return;
                        }
                    } else if (strs[0].contains("(") && strs[1].contains("]")) {
                        if (oneB.doubleValue() < myScore.doubleValue() && myScore.doubleValue() <= twoB.doubleValue()) {
                            diagnosisDetailBean.setResult(s.getDiagnoseResult());
                            diagnosisDetailBean.setAdvice(s.getLearnAdvice());
                            return;
                        }
                    } else if (strs[0].contains("(") && strs[1].contains(")")) {
                        if (oneB.doubleValue() < myScore.doubleValue() && myScore.doubleValue() < twoB.doubleValue()) {
                            diagnosisDetailBean.setResult(s.getDiagnoseResult());
                            diagnosisDetailBean.setAdvice(s.getLearnAdvice());
                            return;
                        }
                    }
                });
            }
            diagnosisDetailBean.setName(k);
            ModuleBean moduleBean = new ModuleBean();
            List<ModuleDetailBean> dios = new ArrayList<>();
            v.forEach((k1, v1) -> {
                moduleBean.setInfo(v1.getInfo());
                moduleBean.setRemark(v1.getRemark());
                ModuleDetailBean moduleDetailBean = new ModuleDetailBean();
                moduleDetailBean.setName(v1.getKnowledgeFirst());
                Set<String> dimensionSet = null;
                if (Objects.nonNull(v1.getIdentifierSecond())) {
                    dimensionSet = v1.getIdentifierSecond().stream().map(o -> o.getIdentifierSecond()).collect(Collectors.toSet());
                } else {
                    dimensionSet = new LinkedHashSet<>();
                }
                dimensionSet.add(v1.getIdentifierFirst());
                String moduleCode = ModuleEnum.convertToSql(v1.getModuleCode());
                BigDecimal paperStructSumScore = tePaperStructService.paperStructSumScoreByDimension(dimensionSet, moduleCode, tePaper.getId());
                BigDecimal collegeAvgScoreByDimension = teAnswerService.calculateCollegeAvgScoreByDimension(examStudentDto.getExamId(), tePaper.getId(), examStudentDto.getCollegeId(), examStudentDto.getCourseCode(), dimensionSet, moduleCode);
                moduleDetailBean.setCollegeRate((Objects.nonNull(collegeAvgScoreByDimension) && collegeAvgScoreByDimension.doubleValue() > 0) && (Objects.nonNull(paperStructSumScore) && paperStructSumScore.doubleValue() > 0) ? collegeAvgScoreByDimension.divide(bigActualCount, SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).divide(paperStructSumScore, SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate) : BigDecimal.ZERO);
                BigDecimal studentAvgScoreByDimension = teAnswerService.calculateStudentAvgScoreByDimension(examStudentDto.getExamId(), tePaper.getId(), examStudentDto.getCollegeId(), examStudentDto.getStudentNo(), examStudentDto.getCourseCode(), dimensionSet, moduleCode);
                moduleDetailBean.setRate((Objects.nonNull(studentAvgScoreByDimension) && studentAvgScoreByDimension.doubleValue() > 0) && (Objects.nonNull(paperStructSumScore) && paperStructSumScore.doubleValue() > 0) ? studentAvgScoreByDimension.divide(paperStructSumScore, SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate) : BigDecimal.ZERO);
                moduleDetailBean.setInterpretation(v1.getInterpretation());
                dios.add(moduleDetailBean);
            });
            moduleBean.setDios(dios);
            diagnosisDetailBean.setModules(moduleBean);
            diagnosisDetailBeanList.add(diagnosisDetailBean);
        });
        //一级维度end

        //二级维度start
        Map<String, DiagnosisDetailBean> diagnosisDetailBeanMap = diagnosisDetailBeanList.stream().collect(Collectors.toMap(s -> s.getName(), Function.identity(), (dto1, dto2) -> dto1));
        studentDimensionFirstMap.forEach((k, v) -> {
            List<DimensionMasterysDto> dimensionMasterysDtoList = dimensionSecondMasterysDtoMap.get(k);
            List<DimensionMasterysBean> dmbList = gson.fromJson(JacksonUtil.parseJson(dimensionMasterysDtoList), new TypeToken<List<DimensionMasterysBean>>() {
            }.getType());
            DimensionBean dimensionBean = new DimensionBean();
            dimensionBean.setMasterys(dmbList);
            List<DimensionDetailBean> subDios = new ArrayList<>();
            v.forEach((k1, v1) -> {
                if (Objects.nonNull(v1.getIdentifierSecond())) {
                    v1.getIdentifierSecond().forEach(s -> {
                        if (Objects.nonNull(s.getIdentifierSecond()) && Objects.nonNull(studentDimensionSecondMap.get(k).get(s.getIdentifierSecond()))) {
                            DimensionDetailBean dimensionDetailBean = new DimensionDetailBean();
                            dimensionDetailBean.setCode(s.getIdentifierSecond());
                            dimensionDetailBean.setName(s.getKnowledgeSecond());
                            Set<String> dimensionsSet = new HashSet<>(Arrays.asList(s.getIdentifierSecond()));
                            String moduleCode = ModuleEnum.convertToSql(s.getModuleCode());
                            BigDecimal paperStructSumScore = tePaperStructService.paperStructSumScoreByDimension(dimensionsSet, moduleCode, tePaper.getId());
                            BigDecimal collegeAvgScoreByDimension = teAnswerService.calculateCollegeAvgScoreByDimension(examStudentDto.getExamId(), tePaper.getId(), examStudentDto.getCollegeId(), examStudentDto.getCourseCode(), dimensionsSet, moduleCode);
                            dimensionDetailBean.setCollegeAvgScore((Objects.nonNull(collegeAvgScoreByDimension) && collegeAvgScoreByDimension.doubleValue() > 0) && (Objects.nonNull(paperStructSumScore) && paperStructSumScore.doubleValue() > 0) ? collegeAvgScoreByDimension.divide(bigActualCount, SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).divide(paperStructSumScore, SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate) : BigDecimal.ZERO);
                            BigDecimal studentAvgScoreByDimension = teAnswerService.calculateStudentAvgScoreByDimension(examStudentDto.getExamId(), tePaper.getId(), examStudentDto.getCollegeId(), examStudentDto.getStudentNo(), examStudentDto.getCourseCode(), dimensionsSet, moduleCode);
                            dimensionDetailBean.setScoreRate((Objects.nonNull(studentAvgScoreByDimension) && studentAvgScoreByDimension.doubleValue() > 0) && (Objects.nonNull(paperStructSumScore) && paperStructSumScore.doubleValue() > 0) ? studentAvgScoreByDimension.divide(paperStructSumScore, SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate) : BigDecimal.ZERO);
                            if (Objects.nonNull(dimensionMasterysDtoList) && dimensionMasterysDtoList.size() > 0) {
                                dimensionMasterysDtoList.forEach(o -> {
                                    String[] strs = o.getSourceGrade();
                                    BigDecimal oneB = new BigDecimal(o.getGrade().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP);
                                    BigDecimal twoB = new BigDecimal(o.getGrade().get(1)).setScale(2, BigDecimal.ROUND_HALF_UP);
                                    if (strs[0].contains("[") && strs[1].contains("]")) {
                                        if (oneB.doubleValue() <= dimensionDetailBean.getScoreRate().doubleValue() && dimensionDetailBean.getScoreRate().doubleValue() <= twoB.doubleValue()) {
                                            dimensionDetailBean.setProficiency(o.getLevel());
                                            return;
                                        }
                                    } else if (strs[0].contains("[") && strs[1].contains(")")) {
                                        if (oneB.doubleValue() <= dimensionDetailBean.getScoreRate().doubleValue() && dimensionDetailBean.getScoreRate().doubleValue() < twoB.doubleValue()) {
                                            dimensionDetailBean.setProficiency(o.getLevel());
                                            return;
                                        }
                                    } else if (strs[0].contains("(") && strs[1].contains("]")) {
                                        if (oneB.doubleValue() < dimensionDetailBean.getScoreRate().doubleValue() && dimensionDetailBean.getScoreRate().doubleValue() <= twoB.doubleValue()) {
                                            dimensionDetailBean.setProficiency(o.getLevel());
                                            return;
                                        }
                                    } else if (strs[0].contains("(") && strs[1].contains(")")) {
                                        if (oneB.doubleValue() < dimensionDetailBean.getScoreRate().doubleValue() && dimensionDetailBean.getScoreRate().doubleValue() < twoB.doubleValue()) {
                                            dimensionDetailBean.setProficiency(o.getLevel());
                                            return;
                                        }
                                    }
                                });
                            }
                            subDios.add(dimensionDetailBean);
                        }
                    });
                }
            });
            if (Objects.nonNull(studentDimensionScoreMap.get(k))) {
                dimensionBean.setMyScore(studentDimensionScoreMap.get(k));
                dimensionBean.setDioFullScore(paperStructScore.get());
                dimensionBean.setMasteryRate(studentDimensionScoreMap.get(k).divide(paperStructScore.get(), SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate));
            }
            dimensionBean.setSubDios(subDios);
            diagnosisDetailBeanMap.get(k).setDetail(dimensionBean);
        });
        //二级维度end

        diagnosisBean.setList(diagnosisDetailBeanList);
        collegeBean.setDiagnosis(diagnosisBean);
        return personalReportBean;
    }

    /**
     * 删除个人报告
     *
     * @param examId
     * @param examStudentId
     * @param courseCode
     */
    @Override
//    @CacheEvict(value = "personal_report_cache", key = "#examId + '-' + #examStudentId + '-' + #courseCode")
    public void removePersonalReport(Long examId, Long examStudentId, String courseCode) {

    }
}
