package com.qmth.wuda.teaching.service.impl;

import com.google.gson.Gson;
import com.qmth.wuda.teaching.bean.report.*;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.dto.DimensionFirstDto;
import com.qmth.wuda.teaching.dto.DimensionSecondDto;
import com.qmth.wuda.teaching.dto.ExamStudentDto;
import com.qmth.wuda.teaching.entity.*;
import com.qmth.wuda.teaching.enums.MissEnum;
import com.qmth.wuda.teaching.enums.ModuleEnum;
import com.qmth.wuda.teaching.enums.PaperDifficultEnum;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.*;
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
        List<TBLevel> tbLevelList = new ArrayList<>();
        tbModuleList.forEach(s -> {
            tbLevelList.addAll(tbLevelService.findByModuleId(s.getId()));
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
        List<TEAnswer> teAnswerList = teAnswerService.findByExamRecordId(examStudentDto.getExamRecordId());
        if (Objects.isNull(teAnswerList) || teAnswerList.size() == 0) {
            throw new BusinessException("答题信息为空");
        }
        Map<String, TEAnswer> teAnswerMap = teAnswerList.stream().collect(Collectors.toMap(s -> s.getMainNumber() + "-" + s.getSubNumber(), Function.identity(), (dto1, dto2) -> dto1));
        Map<String, TEPaperStruct> tePaperStructMap = tePaperStructList.stream().collect(Collectors.toMap(s -> s.getMainNumber() + "-" + s.getSubNumber(), Function.identity(), (dto1, dto2) -> dto1));

        Map<String, List<DimensionMasterysBean>> dimensionSecondMasterysBeanMap = new HashMap<>();
        LinkedMultiValueMap<String, DimensionSecondDto> dimensionSecondMap = new LinkedMultiValueMap<>();
        Map<String, Map<String, DimensionFirstDto>> dimensionFirstMap = new LinkedHashMap<>();
        Gson gson = new Gson();
        tbModuleList.forEach(s -> {
            if (Objects.nonNull(s.getDegree()) && !s.getDegree().contains("无")) {
                String[] strDegrees = s.getDegree().split(";");
                List<DimensionMasterysBean> dimensionMasterysBeanList = new ArrayList<>();
                for (int i = 0; i < strDegrees.length; i++) {
                    String str = strDegrees[i];
                    String[] strs = str.split(":");
                    String[] grades = strs[1].split(",");
                    grades[0] = grades[0].replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\[", "").replaceAll("]", "");
                    grades[1] = grades[1].replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("\\[", "").replaceAll("]", "");
                    List<Integer> gradeInteger = new ArrayList<>();
                    gradeInteger.add(Integer.parseInt(grades[0]));
                    gradeInteger.add(Integer.parseInt(grades[1]));
//                    dimensionMasterysBeanList.add(new DimensionMasterysBean(strs[0], Arrays.asList((Integer[]) ConvertUtils.convert(strs, Integer.class))));
                    dimensionMasterysBeanList.add(new DimensionMasterysBean(strs[0], gradeInteger));
                    dimensionSecondMasterysBeanMap.put(s.getName(), dimensionMasterysBeanList);
                }
            }
            List<TBDimension> tbDimensionList = tbDimensionService.findByModuleIdAndCourseCode(s.getId(), examStudentDto.getCourseCode());
            if (Objects.nonNull(tbDimensionList) && tbDimensionList.size() > 0) {
                Map<String, DimensionFirstDto> dimensionMap = new LinkedHashMap<>();
                tbDimensionList.forEach(o -> {
                    if (!dimensionMap.containsKey(o.getKnowledgeFirst())) {
                        dimensionMap.put(o.getKnowledgeFirst(), new DimensionFirstDto(o.getModuleId(), s.getCode(), o.getCourseCode(), o.getKnowledgeFirst(), o.getIdentifierFirst(), s.getDescription(), s.getRemark(), Objects.nonNull(o.getIdentifierSecond()) ? new LinkedHashSet<>(Arrays.asList(o.getIdentifierSecond())) : null));
                    } else {
                        if (Objects.nonNull(dimensionMap.get(o.getKnowledgeFirst()).getIdentifierSecond())) {
                            dimensionMap.get(o.getKnowledgeFirst()).getIdentifierSecond().add(o.getIdentifierSecond());
                        }
                    }
                    DimensionSecondDto dimensionSecondDto = gson.fromJson(gson.toJson(o), DimensionSecondDto.class);
                    dimensionSecondDto.setModuleCode(s.getCode());
                    dimensionSecondMap.add(o.getIdentifierFirst(), dimensionSecondDto);
                });
                dimensionFirstMap.put(s.getName(), dimensionMap);
            }
        });

        //维度过滤
        dimensionFirstMap.forEach((k, v) -> {
            String moduleCode = ModuleEnum.convertToSqlByCode(k);
            List<String> studentDimensions = tePaperStructService.findStudentDimension(examStudentDto.getExamId(), examStudentDto.getStudentNo(), examStudentDto.getCourseCode(), moduleCode);
//            v.forEach((k1,v1)->{
//                tePaperStructService.findStudentDimension(examStudentDto.getExamId(),examStudentDto.getStudentNo(),examStudentDto.getCourseCode(),moduleCode);
//            });
        });

        PersonalReportBean personalReportBean = new PersonalReportBean();
        ExamStudentBean examStudentBean = gson.fromJson(gson.toJson(examStudentDto), ExamStudentBean.class);
        List<LevelBean> levelBeanList = new ArrayList();
        examStudentBean.setLevels(levelBeanList);

        SystemConstant.levelMap.forEach((k, v) -> {
            levelBeanList.add(new LevelBean(k, v));
            if (examStudentDto.getMyScore().doubleValue() >= Double.parseDouble(String.valueOf(v.get(0))) && examStudentDto.getMyScore().doubleValue() <= Double.parseDouble(String.valueOf(v.get(1)))) {
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
        Integer actualCount = teExamStudentService.findByActualCount(examStudentDto.getExamId(), examStudentDto.getCollegeId(), MissEnum.NORMAL.getValue());
        BigDecimal bigActualCount = new BigDecimal(actualCount);
        SynthesisBean finalSynthesis = new SynthesisBean(examStudentDto.getMyScore(), actualCount, examStudentDto.getFullScore());
        Integer lowScoreCount = teExamRecordService.getLowScoreByMe(examStudentDto.getExamId(), examStudentDto.getCollegeId(), examStudentDto.getExamRecordId(), examStudentDto.getCourseCode());
        BigDecimal fullRate = new BigDecimal(100);
        BigDecimal bigZero = new BigDecimal(0);
        BigDecimal bigDecimal = actualCount > 0 ? new BigDecimal(lowScoreCount).divide(bigActualCount.subtract(new BigDecimal(1)), SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate) : bigZero;
        finalSynthesis.setOverRate(bigDecimal);
        finalSynthesis.setCollegeScore(collegeScore);
        finalSynthesis.setClassScore(calssScore);

        BigDecimal difficult = Objects.nonNull(tePaper.getTotalScore()) && tePaper.getTotalScore().doubleValue() > 0 ? finalSynthesis.getCollegeAvgScore().divide(tePaper.getTotalScore(), SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP) : bigZero;
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

        //维度过滤
//        dimensionFirstMap.forEach((k, v) -> {
//            v.forEach((k1, v1) -> {
//                List<DimensionSecondDto> dimensionSecondDtoList = dimensionSecondMap.get(k);
//                for (DimensionSecondDto dimensionSecondDto : dimensionSecondDtoList) {
//                    tePaperStructMap.forEach((k2, v2) -> {
//                        if (Objects.nonNull(v1.getModuleCode()) && Objects.equals(v1.getModuleCode(), ModuleEnum.KNOWLEDGE.name().toLowerCase())) {
//                            if (Objects.nonNull(v2.getKnowledge()) && Arrays.asList(v2.getKnowledge().split(",")).contains(v1.getIdentifierFirst())) {
//
//                            }else if (Objects.nonNull(v2.getKnowledge()) && Arrays.asList(v2.getKnowledge().split(",")).contains(dimensionSecondDto.getIdentifierSecond())) {
//
//                            }
//                        }
//                    });
//                }
//            });
//        });

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
                tePaperStructMap.forEach((k2, v2) -> {
                    if (Objects.nonNull(v1.getModuleCode()) && Objects.equals(v1.getModuleCode(), ModuleEnum.KNOWLEDGE.name().toLowerCase())) {
                        if (Objects.nonNull(v2.getKnowledge()) && Arrays.asList(v2.getKnowledge().split(",")).contains(v1.getIdentifierFirst())) {
                            v1.setSumScore(v1.getSumScore().add(v2.getScore()));
                            if (Objects.nonNull(teAnswerMap.get(k2))) {
                                v1.setMyScore(v1.getMyScore().add(teAnswerMap.get(k2).getScore()));
                            }
                        }
                    } else if (Objects.nonNull(v1.getModuleCode()) && Objects.equals(v1.getModuleCode(), ModuleEnum.CAPABILITY.name().toLowerCase())) {
                        if (Objects.nonNull(v2.getCapability()) && Arrays.asList(v2.getCapability().split(",")).contains(v1.getIdentifierFirst())) {
                            v1.setSumScore(v1.getSumScore().add(v2.getScore()));
                            if (Objects.nonNull(teAnswerMap.get(k2))) {
                                v1.setMyScore(v1.getMyScore().add(teAnswerMap.get(k2).getScore()));
                            }
                        }
                    }
                });
                moduleDetailBean.setRate(Objects.nonNull(v1.getSumScore()) && v1.getSumScore().doubleValue() > 0 ? v1.getMyScore().divide(v1.getSumScore(), SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate) : bigZero);
                List<String> dimensionFirst = dimensionSecondMap.get(v1.getIdentifierFirst()).stream().map(o -> o.getIdentifierSecond()).filter(Objects::nonNull).collect(Collectors.toList());
                dimensionFirst.add(v1.getIdentifierFirst());
                String moduleCode = ModuleEnum.convertToSql(v1.getModuleCode());
                BigDecimal paperStructSumScore = tePaperStructService.paperStructSumScoreByDimension(dimensionFirst, moduleCode);
                BigDecimal collegeAvgScoreByDimension = teAnswerService.calculateCollegeAvgScoreByDimension(examStudentDto.getExamId(), examStudentDto.getCollegeId(), examStudentDto.getCourseCode(), dimensionFirst, moduleCode);
                moduleDetailBean.setCollegeRate(Objects.nonNull(collegeAvgScoreByDimension) && Objects.nonNull(paperStructSumScore) ? collegeAvgScoreByDimension.divide(bigActualCount, SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).divide(paperStructSumScore, SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate) : bigZero);
                BigDecimal studentAvgScoreByDimension = teAnswerService.calculateStudentAvgScoreByDimension(examStudentDto.getExamId(), examStudentDto.getCollegeId(), examStudentDto.getStudentNo(), examStudentDto.getCourseCode(), dimensionFirst, moduleCode);
                moduleDetailBean.setRate(Objects.nonNull(studentAvgScoreByDimension) && Objects.nonNull(paperStructSumScore) ? studentAvgScoreByDimension.divide(paperStructSumScore, SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate) : bigZero);
                dios.add(moduleDetailBean);
            });
            moduleBean.setDios(dios);
            diagnosisDetailBean.setModules(moduleBean);
            diagnosisDetailBeanList.add(diagnosisDetailBean);
        });
        //一级维度end

        //二级维度start
        dimensionSecondMap.forEach((k, v) -> {
            v.forEach(s -> {
                tePaperStructMap.forEach((k1, v1) -> {
                    if (Objects.nonNull(s.getModuleCode()) && Objects.equals(s.getModuleCode(), ModuleEnum.KNOWLEDGE.name().toLowerCase())) {
                        if (Objects.nonNull(v1.getKnowledge()) && Arrays.asList(v1.getKnowledge().split(",")).contains(s.getIdentifierSecond())) {
                            s.setSumScore(s.getSumScore().add(v1.getScore()));
                            if (Objects.nonNull(teAnswerMap.get(k1))) {
                                s.setMyScore(s.getMyScore().add(teAnswerMap.get(k1).getScore()));
                            }
                        }
                    } else if (Objects.nonNull(s.getModuleCode()) && Objects.equals(s.getModuleCode(), ModuleEnum.CAPABILITY.name().toLowerCase())) {
                        if (Objects.nonNull(v1.getCapability()) && Arrays.asList(v1.getCapability().split(",")).contains(s.getIdentifierSecond())) {
                            s.setSumScore(s.getSumScore().add(v1.getScore()));
                            if (Objects.nonNull(teAnswerMap.get(k1))) {
                                s.setMyScore(s.getMyScore().add(teAnswerMap.get(k1).getScore()));
                            }
                        }
                    }
                });
            });
        });
        Map<String, DiagnosisDetailBean> diagnosisDetailBeanMap = diagnosisDetailBeanList.stream().collect(Collectors.toMap(s -> s.getName(), Function.identity(), (dto1, dto2) -> dto1));
        dimensionFirstMap.forEach((k, v) -> {
            List<DimensionMasterysBean> dimensionMasterysBeanList = dimensionSecondMasterysBeanMap.get(k);
            DimensionBean dimensionBean = new DimensionBean();
            dimensionBean.setMasterys(dimensionMasterysBeanList);
            AtomicReference<BigDecimal> myScore = new AtomicReference<>(bigZero);
            AtomicReference<BigDecimal> dioFullScore = new AtomicReference<>(bigZero);
            List<DimensionDetailBean> subDios = new ArrayList<>();
            v.forEach((k1, v1) -> {
                List<DimensionSecondDto> dimensionSecondList = dimensionSecondMap.get(v1.getIdentifierFirst());
                dimensionSecondList.forEach(s -> {
                    myScore.set(myScore.get().add(s.getMyScore()));
                    dioFullScore.set(dioFullScore.get().add(s.getSumScore()));
                    if (Objects.nonNull(s.getIdentifierSecond())) {
                        DimensionDetailBean dimensionDetailBean = new DimensionDetailBean();
                        dimensionDetailBean.setCode(s.getIdentifierSecond());
                        dimensionDetailBean.setName(s.getKnowledgeSecond());
                        dimensionDetailBean.setScoreRate(Objects.nonNull(s.getSumScore()) && s.getSumScore().doubleValue() > 0 ? s.getMyScore().divide(s.getSumScore(), SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate) : bigZero);
                        List<String> dimensionFirst = Arrays.asList(s.getIdentifierSecond());
                        String moduleCode = ModuleEnum.convertToSql(s.getModuleCode());
                        BigDecimal paperStructSumScore = tePaperStructService.paperStructSumScoreByDimension(dimensionFirst, moduleCode);
                        BigDecimal collegeAvgScoreByDimension = teAnswerService.calculateCollegeAvgScoreByDimension(examStudentDto.getExamId(), examStudentDto.getCollegeId(), examStudentDto.getCourseCode(), dimensionFirst, moduleCode);
                        dimensionDetailBean.setCollegeAvgScore(Objects.nonNull(collegeAvgScoreByDimension) && Objects.nonNull(paperStructSumScore) ? collegeAvgScoreByDimension.divide(bigActualCount, SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).divide(paperStructSumScore, SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate) : bigZero);

                        if (Objects.nonNull(dimensionMasterysBeanList) && dimensionMasterysBeanList.size() > 0) {
                            dimensionMasterysBeanList.forEach(o -> {
                                if (dimensionDetailBean.getScoreRate().intValue() >= o.getGrade().get(0) && dimensionDetailBean.getScoreRate().intValue() <= o.getGrade().get(1)) {
                                    dimensionDetailBean.setProficiency(o.getLevel());
                                    return;
                                }
                            });
                        }
                        subDios.add(dimensionDetailBean);
                    }
                });
            });
            dimensionBean.setMyScore(myScore.get());
            dimensionBean.setDioFullScore(dioFullScore.get());
            dimensionBean.setMasteryRate(dioFullScore.get().doubleValue() > 0 ? myScore.get().divide(dioFullScore.get(), SystemConstant.OPER_SCALE, BigDecimal.ROUND_HALF_UP).multiply(fullRate) : bigZero);

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
