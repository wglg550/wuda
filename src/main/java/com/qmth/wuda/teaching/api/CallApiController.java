package com.qmth.wuda.teaching.api;

import com.qmth.wuda.teaching.annotation.ApiJsonObject;
import com.qmth.wuda.teaching.annotation.ApiJsonProperty;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.templete.impl.MathCourseAnalysisTemplete;
import com.qmth.wuda.teaching.templete.impl.PhysicsCourseAnalysisTemplete;
import com.qmth.wuda.teaching.templete.service.CourseAnalysisService;
import com.qmth.wuda.teaching.util.ResultUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Api(tags = "调用层apiController")
@RestController
@RequestMapping("/${prefix.url.wuda}/call")
public class CallApiController {
    private final static Logger log = LoggerFactory.getLogger(CallApiController.class);

    @Resource
    PhysicsCourseAnalysisTemplete physicsCourseAnalysisTemplete;

    @Resource
    MathCourseAnalysisTemplete mathCourseAnalysisTemplete;

    @Resource
    CourseAnalysisService courseAnalysisService;

    @ApiOperation(value = "获取考生成绩接口")
    @RequestMapping(value = "/student/score", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result studentScore(
            @ApiJsonObject(name = "callStudentScore", value = {
                    @ApiJsonProperty(key = "examId", description = "考试id"),
                    @ApiJsonProperty(key = "examCode", description = "考试code")
            })
            @ApiParam(value = "获取考生成绩", required = true) @RequestBody Map<String, Object> mapParameter) throws IOException {
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
        switch (examId.intValue()) {
            case 10:
                return physicsCourseAnalysisTemplete.dataAnalysis(examId, examCode);
            case 63:
                return mathCourseAnalysisTemplete.dataAnalysis(examId, examCode);
            default:
                return ResultUtil.ok(courseAnalysisService.yyjSourceDataAnalysis(examId, examCode));
        }
    }
}
