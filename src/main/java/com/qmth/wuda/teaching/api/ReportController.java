package com.qmth.wuda.teaching.api;

import com.qmth.wuda.teaching.annotation.ApiJsonObject;
import com.qmth.wuda.teaching.annotation.ApiJsonProperty;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.bean.report.PersonalReportBean;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.CacheService;
import com.qmth.wuda.teaching.util.ResultUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

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
    CacheService cacheService;

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
        PersonalReportBean personalReportBean = cacheService.addPersonalReport(schoolId, Objects.nonNull(mapParameter.get("collegeId")) ? Long.parseLong(String.valueOf(mapParameter.get("collegeId"))) : null, studentNo);
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
