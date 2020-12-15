package com.qmth.wuda.teaching.api;

import com.qmth.wuda.teaching.annotation.ApiJsonObject;
import com.qmth.wuda.teaching.annotation.ApiJsonProperty;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.dto.ExamCourseDto;
import com.qmth.wuda.teaching.dto.ExamDto;
import com.qmth.wuda.teaching.entity.TEExam;
import com.qmth.wuda.teaching.entity.TEStudent;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.TEExamService;
import com.qmth.wuda.teaching.service.TEExamStudentService;
import com.qmth.wuda.teaching.service.TEStudentService;
import com.qmth.wuda.teaching.util.ResultUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @ApiOperation(value = "获取考生科目接口")
    @RequestMapping(value = "/examStudent/course", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result examStudentCourse(
            @ApiJsonObject(name = "openExamStudentCourse", value = {
                    @ApiJsonProperty(key = "examId", description = "考试id"),
                    @ApiJsonProperty(key = "studentCode", description = "学号")
            })
            @ApiParam(value = "获取考生科目信息", required = true) @RequestBody Map<String, Object> mapParameter) {
        if (Objects.isNull(mapParameter.get("examId")) || Objects.equals(mapParameter.get("examId"), "")) {
            throw new BusinessException("考试id不能为空");
        }
        Long examId = Long.parseLong(String.valueOf(mapParameter.get("examId")));
        if (Objects.isNull(mapParameter.get("studentCode")) || Objects.equals(mapParameter.get("studentCode"), "")) {
            throw new BusinessException("学号不能为空");
        }
        String studentCode = (String) mapParameter.get("studentCode");
        TEExam teExam = teExamService.getById(examId);
        TEStudent teStudent = teStudentService.findByStudentCode(studentCode);
        List<ExamCourseDto> examCourseDtoList = teExamStudentService.findByStudentIdAndExamId(teStudent.getId(), examId);
        ExamDto examDto = new ExamDto(teExam.getId(), teExam.getName(), teStudent.getId(), teStudent.getName(), examCourseDtoList);
        return ResultUtil.ok(examDto);
    }

    @ApiOperation(value = "根据科目获取报告接口")
    @RequestMapping(value = "/examStudent/report", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result examStudentReport(
            @ApiJsonObject(name = "openExamStudentCourse", value = {
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
        TEExam teExam = teExamService.getById(examId);
        return ResultUtil.ok(true);
    }
}
