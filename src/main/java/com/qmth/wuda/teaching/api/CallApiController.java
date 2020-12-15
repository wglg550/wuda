package com.qmth.wuda.teaching.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qmth.wuda.teaching.annotation.ApiJsonObject;
import com.qmth.wuda.teaching.annotation.ApiJsonProperty;
import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.config.DictionaryConfig;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.entity.TEExam;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.TEExamService;
import com.qmth.wuda.teaching.service.TEExamStudentService;
import com.qmth.wuda.teaching.service.TEStudentService;
import com.qmth.wuda.teaching.signature.SignatureInfo;
import com.qmth.wuda.teaching.signature.SignatureType;
import com.qmth.wuda.teaching.util.HttpUtil;
import com.qmth.wuda.teaching.util.ResultUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Api(tags = "调用层apiController")
@RestController
@RequestMapping("/${prefix.url.wuda}/call")
public class CallApiController {

    @Resource
    DictionaryConfig dictionaryConfig;

    @Resource
    TEStudentService teStudentService;

    @Resource
    TEExamStudentService teExamStudentService;

    @Resource
    TEExamService teExamService;

    @ApiOperation(value = "获取学生信息接口")
    @RequestMapping(value = "/student/info", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result studentInfo(
            @ApiJsonObject(name = "callStudentInfo", value = {
                    @ApiJsonProperty(key = "studentNo", description = "身份证号")
            })
            @ApiParam(value = "获取学生信息", required = true) @RequestBody Map<String, Object> mapParameter) {
        if (Objects.isNull(mapParameter.get("studentNo")) || Objects.equals(mapParameter.get("studentNo"), "")) {
            throw new BusinessException("学生身份证号不能为空");
        }
        String studentNo = (String) mapParameter.get("studentNo");
        return ResultUtil.ok(teStudentService.findByStudentNo(studentNo));
    }

    @ApiOperation(value = "获取考生科目接口")
    @RequestMapping(value = "/examStudent/course", method = RequestMethod.POST)
    @ApiResponses({@ApiResponse(code = 200, message = "{\"success\":true}", response = Result.class)})
    public Result examStudentCourse(
            @ApiJsonObject(name = "callExamStudentCourse", value = {
                    @ApiJsonProperty(key = "examId", description = "考试id"),
                    @ApiJsonProperty(key = "studentId", description = "学生id")
            })
            @ApiParam(value = "获取考生科目信息", required = true) @RequestBody Map<String, Object> mapParameter) {
        if (Objects.isNull(mapParameter.get("examId")) || Objects.equals(mapParameter.get("examId"), "")) {
            throw new BusinessException("考试id不能为空");
        }
        Long examId = Long.parseLong(String.valueOf(mapParameter.get("examId")));
        if (Objects.isNull(mapParameter.get("studentId")) || Objects.equals(mapParameter.get("studentId"), "")) {
            throw new BusinessException("学生id不能为空");
        }
        Long studentId = Long.parseLong(String.valueOf(mapParameter.get("studentId")));
        return ResultUtil.ok(teExamStudentService.findByStudentIdAndExamId(studentId, examId));
    }

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
        String url = dictionaryConfig.yunMarkDomain().getUrl() + dictionaryConfig.yunMarkDomain().getStudentScoreApi();
        Map<String, String> params = new HashMap<>();
        if (Objects.nonNull(examId)) {
            params.put("examId", String.valueOf(examId));
        } else if (Objects.nonNull(examCode)) {
            params.put("examCode", examCode);
        }
        Long timestamp = System.currentTimeMillis();
        String accessKey = "a063f96182164154bf7428b3cb0fadf2";
        String accessSecret = "M6SCQbJELhbtshzG6Kyz8jvh";
        String test = SignatureInfo.build(SignatureType.SECRET, SystemConstant.METHOD, dictionaryConfig.yunMarkDomain().getStudentScoreApi(), timestamp, accessKey, accessSecret);
        String result = HttpUtil.post(url, params, test, timestamp);
        List<Map> students = JSONObject.parseArray(JSONObject.toJSON(result).toString(), Map.class);
        return ResultUtil.ok(students);
    }
}
