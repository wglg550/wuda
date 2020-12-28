package com.qmth.wuda.teaching.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qmth.wuda.teaching.annotation.ApiJsonObject;
import com.qmth.wuda.teaching.annotation.ApiJsonProperty;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.bean.report.course.CourseInfoBean;
import com.qmth.wuda.teaching.bean.report.course.ExamInfoBean;
import com.qmth.wuda.teaching.bean.report.course.StudentInfoBean;
import com.qmth.wuda.teaching.bean.report.course.StudentReallyInfoBean;
import com.qmth.wuda.teaching.config.DictionaryConfig;
import com.qmth.wuda.teaching.dto.ExamDto;
import com.qmth.wuda.teaching.entity.TBSchool;
import com.qmth.wuda.teaching.entity.TECourse;
import com.qmth.wuda.teaching.entity.TEExamStudent;
import com.qmth.wuda.teaching.entity.TEStudent;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.*;
import com.qmth.wuda.teaching.util.ResultUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Api(tags = "开放接口层apiController")
@RestController
@RequestMapping("/${prefix.url.wuda}/open")
public class OpenApiController {

    @Resource
    TEExamStudentService teExamStudentService;

    @Resource
    TEStudentService teStudentService;

    @Resource
    TEExamService teExamService;

    @Resource
    DictionaryConfig dictionaryConfig;

    @Resource
    CacheService cacheService;

    @Resource
    TECourseService teCourseService;

    @Resource
    TBSchoolService tbSchoolService;

    @ApiOperation(value = "获取考生科目接口")
    @RequestMapping(value = "/exam/findStudentReallyInfo", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = StudentReallyInfoBean.class)})
    public Result examStudentCourse(
            @ApiJsonObject(name = "openFindStudentReallyInfo", value = {
                    @ApiJsonProperty(key = "studentCode", description = "学号")
            })
            @ApiParam(value = "获取考生科目信息", required = true) @RequestBody Map<String, Object> mapParameter) {
        if (Objects.isNull(mapParameter.get("studentCode")) || Objects.equals(mapParameter.get("studentCode"), "")) {
            throw new BusinessException("学号不能为空");
        }
        String studentCode = (String) mapParameter.get("studentCode");
        QueryWrapper<TEStudent> teStudentQueryWrapper = new QueryWrapper<>();
        teStudentQueryWrapper.lambda().eq(TEStudent::getStudentCode, studentCode);
        TEStudent teStudent = teStudentService.getOne(teStudentQueryWrapper);
        if (Objects.isNull(teStudent)) {
            throw new BusinessException("没有当前这个学生");
        }
        QueryWrapper<TBSchool> tbSchoolQueryWrapper = new QueryWrapper<>();
        tbSchoolQueryWrapper.lambda().eq(TBSchool::getCode, "whdx");
        TBSchool tbSchool = tbSchoolService.getOne(tbSchoolQueryWrapper);

        StudentInfoBean studentInfoBean = new StudentInfoBean(teStudent.getName(), tbSchool.getName(), tbSchool.getCode());

        List<ExamDto> teExamList = teExamService.findByExamName(dictionaryConfig.sysDomain().getExamName());
        if (Objects.isNull(teExamList) || teExamList.size() == 0) {
            throw new BusinessException("当前学生没有考试");
        }
        AtomicReference<String> examCode = new AtomicReference<>();
        AtomicReference<String> createTime = new AtomicReference<>();
        List<Long> examIds = new ArrayList<>();
        teExamList.forEach(s -> {
            examCode.set(s.getExamCode());
            createTime.set(s.getCreateTime());
            examIds.add(s.getId());
        });
        ExamInfoBean examInfoBean = new ExamInfoBean(examCode.get(), dictionaryConfig.sysDomain().getExamName(), createTime.get());

        QueryWrapper<TECourse> teCourseQueryWrapper = new QueryWrapper<>();
        teCourseQueryWrapper.lambda().in(TECourse::getExamId, examIds);
        List<TECourse> teCourseList = teCourseService.list(teCourseQueryWrapper);
        if (Objects.isNull(teCourseList) || teCourseList.size() == 0) {
            throw new BusinessException("当前学生没有科目信息");
        }

        QueryWrapper<TEExamStudent> teExamStudentQueryWrapper = new QueryWrapper<>();
        teExamStudentQueryWrapper.lambda().eq(TEExamStudent::getStudentId, teStudent.getId())
                .in(TEExamStudent::getExamId, examIds);
        List<TEExamStudent> teExamStudentList = teExamStudentService.list(teExamStudentQueryWrapper);
        if (Objects.isNull(teExamStudentList) || teExamStudentList.size() == 0) {
            throw new BusinessException("当前学生没有考生信息");
        }
        Map<String, TEExamStudent> teExamStudentMap = teExamStudentList.stream().collect(Collectors.toMap(s -> s.getCourseCode(), Function.identity(), (dto1, dto2) -> dto1));

        List<CourseInfoBean> courseInfoBeanList = new ArrayList<>();
        teCourseList.forEach(s -> {
            CourseInfoBean courseInfoBean = new CourseInfoBean(examCode.get(), teExamStudentMap.get(s.getCourseCode()).getMiss() == 1 ? false : true, s.getCourseCode(), s.getCourseName(), String.valueOf(s.getStatus()));
            courseInfoBeanList.add(courseInfoBean);
        });
        examInfoBean.setCourseInfo(courseInfoBeanList);

        StudentReallyInfoBean studentReallyInfoBean = new StudentReallyInfoBean();
        studentReallyInfoBean.setExamInfo(Collections.singletonList(examInfoBean));
        studentReallyInfoBean.setStudentInfo(studentInfoBean);
        return ResultUtil.ok(studentReallyInfoBean);
    }

    @ApiOperation(value = "根据科目获取报告接口")
    @RequestMapping(value = "/examStudent/report", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result examStudentReport(
            @ApiJsonObject(name = "openExamStudentReport", value = {
                    @ApiJsonProperty(key = "examId", description = "考试id"),
                    @ApiJsonProperty(key = "courseCode", description = "科目编码"),
                    @ApiJsonProperty(key = "examStudentId", description = "考生id")
            })
            @ApiParam(value = "获取考生科目报告信息", required = true) @RequestBody Map<String, Object> mapParameter) {
        if (Objects.isNull(mapParameter.get("examId")) || Objects.equals(mapParameter.get("examId"), "")) {
            throw new BusinessException("考试id不能为空");
        }
        Long examId = Long.parseLong(String.valueOf(mapParameter.get("examId")));
        if (Objects.isNull(mapParameter.get("courseCode")) || Objects.equals(mapParameter.get("courseCode"), "")) {
            throw new BusinessException("科目编码不能为空");
        }
        String courseCode = (String) mapParameter.get("courseCode");
        if (Objects.isNull(mapParameter.get("examStudentId")) || Objects.equals(mapParameter.get("examStudentId"), "")) {
            throw new BusinessException("考生id不能为空");
        }
        Long examStudentId = Long.parseLong(String.valueOf(mapParameter.get("examStudentId")));
        return ResultUtil.ok(cacheService.addPersonalReport(examId, examStudentId, courseCode));
    }
}
