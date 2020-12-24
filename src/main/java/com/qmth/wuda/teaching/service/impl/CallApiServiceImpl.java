package com.qmth.wuda.teaching.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qmth.wuda.teaching.config.DictionaryConfig;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.entity.TEExam;
import com.qmth.wuda.teaching.enums.ExceptionResultEnum;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.CallApiService;
import com.qmth.wuda.teaching.service.TEExamService;
import com.qmth.wuda.teaching.signature.SignatureInfo;
import com.qmth.wuda.teaching.signature.SignatureType;
import com.qmth.wuda.teaching.util.HttpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @Description: 云阅卷调用服务api
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/15
 */
@Service
public class CallApiServiceImpl implements CallApiService {

    @Resource
    DictionaryConfig dictionaryConfig;

    @Resource
    TEExamService teExamService;

    /**
     * 根据考试id或考试编码调用云阅卷学生成绩接口
     *
     * @param examId
     * @param examCode
     * @return
     */
    @Override
    public List<Map> callStudentScore(Long examId, String examCode) throws UnsupportedEncodingException {
        String url = dictionaryConfig.yunMarkDomain().getUrl() + dictionaryConfig.yunMarkDomain().getStudentScoreApi();
        Map<String, Object> params = new HashMap<>();
        if (Objects.nonNull(examId)) {
            params.put("examId", examId);
        } else if (Objects.nonNull(examCode)) {
            params.put("examCode", examCode);
        }
        TEExam teExam = null;
        if (Objects.nonNull(examId)) {
            teExam = teExamService.getById(examId);
        } else {
            QueryWrapper<TEExam> teExamQueryWrapper = new QueryWrapper<>();
            teExamQueryWrapper.lambda().eq(TEExam::getCode, examCode);
            teExam = teExamService.getOne(teExamQueryWrapper);
        }
        if (Objects.isNull(teExam)) {
            throw new BusinessException("找不到该考试");
        }
        int pageNumber = 1;
        int pageSize = 100;
        List<Map> students = null;
        for (; ; ) {
            params.put("pageNumber", pageNumber);
            params.put("pageSize", pageSize);
            Long timestamp = System.currentTimeMillis();
            String test = SignatureInfo.build(SignatureType.SECRET, SystemConstant.METHOD, dictionaryConfig.yunMarkDomain().getStudentScoreApi(), timestamp, teExam.getAccessKey(), teExam.getAccessSecret());
            String result = HttpUtil.post(url, params, test, timestamp);
            if (Objects.nonNull(result)) {
                if (result.contains("HTTP Status 401 – 未经授权的")) {
                    throw new BusinessException(ExceptionResultEnum.YUN_API_INVALID);
                }
                List<Map> tempList = JSONObject.parseArray(JSONObject.toJSON(URLDecoder.decode(result, SystemConstant.CHARSET_NAME)).toString(), Map.class);
                if (Objects.nonNull(tempList) && tempList.size() > 0) {
                    if (Objects.isNull(students)) {
                        students = new ArrayList<>();
                    }
                    students.addAll(tempList);
                    pageNumber++;
                    pageSize = pageSize + 100;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return students;
    }
}
