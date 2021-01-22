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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Api(tags = "开放接口层apiController")
@RestController
@RequestMapping("/${prefix.url.wuda}/wuda/open")
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

        List<ExamDto> teExamList = teExamService.findByExamName(null);
        if (Objects.isNull(teExamList) || teExamList.size() == 0) {
            throw new BusinessException("当前学生没有考试");
        }
        ExamInfoBean examInfoBean = new ExamInfoBean(teExamList.get(0).getExamCode(), teExamList.get(0).getExamName(), teExamList.get(0).getCreateTime());

        QueryWrapper<TECourse> teCourseQueryWrapper = new QueryWrapper<>();
        teCourseQueryWrapper.lambda().in(TECourse::getExamCode, examInfoBean.getExamCode());
        List<TECourse> teCourseList = teCourseService.list(teCourseQueryWrapper);
        if (Objects.isNull(teCourseList) || teCourseList.size() == 0) {
            throw new BusinessException("当前学生没有科目信息");
        }
        List<Long> examIds = teCourseList.stream().map(TECourse::getExamId).collect(Collectors.toList());
        Map<Long, TECourse> teCourseMap = teCourseList.stream().collect(Collectors.toMap(s -> s.getExamId(), Function.identity(), (dto1, dto2) -> dto1));

        QueryWrapper<TEExamStudent> teExamStudentQueryWrapper = new QueryWrapper<>();
        teExamStudentQueryWrapper.lambda().eq(TEExamStudent::getStudentId, teStudent.getId())
                .in(TEExamStudent::getExamId, examIds);
        List<TEExamStudent> teExamStudentList = teExamStudentService.list(teExamStudentQueryWrapper);
        if (Objects.isNull(teExamStudentList) || teExamStudentList.size() == 0) {
            throw new BusinessException("当前学生没有考生信息");
        }
        Map<String, TEExamStudent> teExamStudentMap = teExamStudentList.stream().collect(Collectors.toMap(s -> s.getCourseCode(), Function.identity(), (dto1, dto2) -> dto1));

        List<CourseInfoBean> courseInfoBeanList = new ArrayList<>();
        teExamStudentList.forEach(s -> {
            CourseInfoBean courseInfoBean = new CourseInfoBean(examInfoBean.getExamCode(), teExamStudentMap.get(s.getCourseCode()).getMiss() == 1 ? false : true, s.getCourseCode(), s.getCourseName(), String.valueOf(teCourseMap.get(s.getExamId()).getStatus()));
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
                    @ApiJsonProperty(key = "examCode", description = "考试编码"),
                    @ApiJsonProperty(key = "paperCode", description = "科目编码"),
                    @ApiJsonProperty(key = "studentCode", description = "考生学号")
            })
            @ApiParam(value = "获取考生科目报告信息", required = true) @RequestBody Map<String, Object> mapParameter) {
        if (Objects.isNull(mapParameter.get("examCode")) || Objects.equals(mapParameter.get("examCode"), "")) {
            throw new BusinessException("考试编码不能为空");
        }
        String examCode = (String) mapParameter.get("examCode");
        if (Objects.isNull(mapParameter.get("paperCode")) || Objects.equals(mapParameter.get("paperCode"), "")) {
            throw new BusinessException("科目编码不能为空");
        }
        String paperCode = (String) mapParameter.get("paperCode");
        if (Objects.isNull(mapParameter.get("studentCode")) || Objects.equals(mapParameter.get("studentCode"), "")) {
            throw new BusinessException("考生学号不能为空");
        }
        String studentCode = (String) mapParameter.get("studentCode");
        QueryWrapper<TECourse> teCourseQueryWrapper = new QueryWrapper<>();
        teCourseQueryWrapper.lambda().eq(TECourse::getExamCode, examCode)
                .eq(TECourse::getCourseCode, paperCode);
        TECourse teCourse = teCourseService.getOne(teCourseQueryWrapper);
        if (Objects.isNull(teCourse)) {
            throw new BusinessException("科目为空");
        }

        QueryWrapper<TEExamStudent> teExamStudentQueryWrapper = new QueryWrapper<>();
        teExamStudentQueryWrapper.lambda().eq(TEExamStudent::getStudentCode, studentCode)
                .eq(TEExamStudent::getExamId, teCourse.getExamId());
        TEExamStudent teExamStudent = teExamStudentService.getOne(teExamStudentQueryWrapper);
        if (Objects.isNull(teExamStudent)) {
            throw new BusinessException("考生为空");
        }
        return ResultUtil.ok(cacheService.addPersonalReport(teCourse.getExamId(), teExamStudent.getId(), paperCode));
    }

    @ApiOperation(value = "cas鉴权接口")
    @RequestMapping(value = "/authentication/{studentCode}", method = RequestMethod.GET)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public void authentication(HttpServletRequest request, HttpServletResponse response, @PathVariable String studentCode) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.sendRedirect(dictionaryConfig.sysDomain().getReportUrl() + studentCode);
    }
}
