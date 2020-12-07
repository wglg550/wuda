package com.qmth.wuda.teaching.api;

import com.google.gson.Gson;
import com.qmth.wuda.teaching.annotation.ApiJsonObject;
import com.qmth.wuda.teaching.annotation.ApiJsonProperty;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.bean.report.*;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.dto.ExamStudentDto;
import com.qmth.wuda.teaching.dto.common.ExamStudentCommonDto;
import com.qmth.wuda.teaching.entity.TBLevel;
import com.qmth.wuda.teaching.enums.MissEnum;
import com.qmth.wuda.teaching.service.TBLevelService;
import com.qmth.wuda.teaching.service.TEExamRecordService;
import com.qmth.wuda.teaching.service.TEExamStudentService;
import com.qmth.wuda.teaching.util.ResultUtil;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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

    @ApiOperation(value = "个人报告")
    @RequestMapping(value = "personal", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result personal(
            @ApiJsonObject(name = "reportPersonal", value = {
                    @ApiJsonProperty(key = "schoolId", description = "学校id", required = true),
                    @ApiJsonProperty(key = "studentNo", description = "考生学号", required = true)
            })
            @ApiParam(value = "个人报告信息", required = true) @RequestBody Map<String, Object> mapParameter) {
        //报告第一页start
        List<TBLevel> tbLevelList = tbLevelService.findAll();
        ExamStudentDto examStudentDto = teExamStudentService.findByStudentNo((String) mapParameter.get("studentNo"));
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
        SynthesisBean collegeScore = teExamRecordService.findByCollegeScore(examStudentDto.getSchoolId(), examStudentDto.getExamId(), examStudentDto.getCollegeId());
        //班级分数
        SynthesisBean calssScore = teExamRecordService.findByClassScore(examStudentDto.getSchoolId(), examStudentDto.getExamId(), examStudentDto.getCollegeId(), examStudentDto.getClazz());
        //获取实考人数
        Integer actualCount = teExamStudentService.findByActualCount(examStudentDto.getSchoolId(), examStudentDto.getExamId(), examStudentDto.getCollegeId(), MissEnum.FALSE.getValue());
        SynthesisBean finalSynthesis = new SynthesisBean(examStudentDto.getMyScore(), actualCount, examStudentDto.getFullScore());
        finalSynthesis.setCollegeScore(collegeScore);
        finalSynthesis.setClassScore(calssScore);
        CollegeBean collegeBean = new CollegeBean(finalSynthesis);
        personalReportBean.setCollege(collegeBean);
        //报告第二页end


        //诊断页start
        DiagnosisBean diagnosisBean = new DiagnosisBean();
        diagnosisBean.setResult(true);

        List<DiagnosisDetailBean> diagnosisDetailBeanList = new ArrayList<>();
        DiagnosisDetailBean diagnosisDetailBean = new DiagnosisDetailBean();
        diagnosisDetailBean.setName("知识");

        ModuleBean moduleBean = new ModuleBean();
        moduleBean.setInfo("测试1");
        moduleBean.setRemark("测试1remark");
        List<ModuleDetailBean> dios = new ArrayList<>();
        ModuleDetailBean moduleDetailBean = new ModuleDetailBean();
        moduleDetailBean.setName("记忆");
        moduleDetailBean.setRate(new BigDecimal(8));
        moduleDetailBean.setCollegeRate(new BigDecimal(10));
        dios.add(moduleDetailBean);
        moduleBean.setDios(dios);

        diagnosisDetailBean.setModules(moduleBean);

        DimensionBean dimensionBean = new DimensionBean();
        dimensionBean.setMyScore(examStudentDto.getMyScore());
        dimensionBean.setMasteryRate(new BigDecimal(9));
        dimensionBean.setDioFullScore(new BigDecimal(100));
        List<DimensionDetailBean> subDios = new ArrayList<>();
        DimensionDetailBean dimensionDetailBean = new DimensionDetailBean();
        dimensionDetailBean.setCode("A1");
        dimensionDetailBean.setName("碱金属元素化合物");
        dimensionDetailBean.setProficiency("H");
        dimensionDetailBean.setScoreRate(new BigDecimal(10));
        dimensionDetailBean.setCollegeAvgScore(new BigDecimal(60));
        subDios.add(dimensionDetailBean);

        dimensionBean.setSubDios(subDios);
        dimensionBean.setMasterys(levelBeanList);

        diagnosisDetailBean.setDetail(dimensionBean);

        diagnosisDetailBeanList.add(diagnosisDetailBean);
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
