package com.qmth.wuda.teaching.api;

import com.qmth.wuda.teaching.annotation.ApiJsonObject;
import com.qmth.wuda.teaching.annotation.ApiJsonProperty;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.CallApiService;
import com.qmth.wuda.teaching.util.ResultUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

@Api(tags = "调用层apiController")
@RestController
@RequestMapping("/${prefix.url.wuda}/call")
public class CallApiController {

    @Resource
    CallApiService callApiService;

    @ApiOperation(value = "获取考生成绩接口")
    @RequestMapping(value = "/student/score", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result studentScore(
            @ApiJsonObject(name = "callStudentScore", value = {
                    @ApiJsonProperty(key = "examId", description = "考试id"),
                    @ApiJsonProperty(key = "examCode", description = "考试code")
            })
            @ApiParam(value = "获取考生成绩", required = true) @RequestBody Map<String, Object> mapParameter) {
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
        return ResultUtil.ok(callApiService.callStudentScore(examId, examCode));
    }
}
