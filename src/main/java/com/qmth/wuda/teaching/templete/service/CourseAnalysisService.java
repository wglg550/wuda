package com.qmth.wuda.teaching.templete.service;

import com.qmth.wuda.teaching.entity.*;
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description: 公用分析service
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/1/21
 */
public interface CourseAnalysisService {

    /**
     * 抽取云阅卷源数据
     *
     * @param examId
     * @param examCode
     * @return
     */
    List<Map> yyjSourceDataAnalysis(Long examId, String examCode) throws IOException;

    /**
     * 保存数据库
     *
     * @param tbSchool
     * @param examId
     * @param courseMap
     * @param collegeMap
     * @param studentMap
     * @param examStudentMap
     * @param teacherMap
     * @param teacherExamStudentMap
     * @param examRecordMap
     * @param teAnswerMap
     * @param tePaperMap
     * @param tePaperStructTranMap
     */
    void saveYyjSourceDataForDb(TBSchool tbSchool,
                                Long examId,
                                Map<String, String> courseMap,
                                Map<String, TBSchoolCollege> collegeMap,
                                Map<String, TEStudent> studentMap,
                                Map<String, TEExamStudent> examStudentMap,
                                Map<String, TBTeacher> teacherMap,
                                LinkedMultiValueMap<Long, TBTeacherExamStudent> teacherExamStudentMap,
                                Map<Long, TEExamRecord> examRecordMap,
                                Map<Long, List<TEAnswer>> teAnswerMap,
                                Map<String, TEPaper> tePaperMap,
                                Map<Long, Map<String, TEPaperStruct>> tePaperStructTranMap);
}
