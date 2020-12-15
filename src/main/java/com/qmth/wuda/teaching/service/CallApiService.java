package com.qmth.wuda.teaching.service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 云阅卷调用服务
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/15
 */
public interface CallApiService {

    /**
     * 根据考试id或考试编码调用云阅卷学生成绩接口
     *
     * @param examId
     * @param examCode
     * @return
     */
    List<Map> callStudentScore(Long examId, String examCode);
}
