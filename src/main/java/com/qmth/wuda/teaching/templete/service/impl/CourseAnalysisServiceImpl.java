package com.qmth.wuda.teaching.templete.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.entity.*;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.*;
import com.qmth.wuda.teaching.templete.service.CourseAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @Description: 公用分析impl
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/1/21
 */
@Service
public class CourseAnalysisServiceImpl implements CourseAnalysisService {
    private final static Logger log = LoggerFactory.getLogger(CourseAnalysisServiceImpl.class);

    @Resource
    CallApiService callApiService;

    @Resource
    TECourseService teCourseService;

    @Resource
    TEStudentService teStudentService;

    @Resource
    TEExamStudentService teExamStudentService;

    @Resource
    TBTeacherService tbTeacherService;

    @Resource
    TBTeacherExamStudentService tbTeacherExamStudentService;

    @Resource
    TEExamRecordService teExamRecordService;

    @Resource
    TEAnswerService teAnswerService;

    @Resource
    TBSchoolCollegeService tbSchoolCollegeService;

    /**
     * 抽取云阅卷源数据
     *
     * @param examId
     * @param examId
     * @return
     */
    @Override
    public List<Map> yyjSourceDataAnalysis(Long examId, String examCode) throws IOException {
        return callApiService.callStudentScore(examId, examCode);
    }

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
    @Override
    @Transactional
    public void saveYyjSourceDataForDb(TBSchool tbSchool, Long examId, Map<String, String> courseMap, Map<String, TBSchoolCollege> collegeMap, Map<String, TEStudent> studentMap, Map<String, TEExamStudent> examStudentMap, Map<String, TBTeacher> teacherMap, LinkedMultiValueMap<Long, TBTeacherExamStudent> teacherExamStudentMap, Map<Long, TEExamRecord> examRecordMap, Map<Long, List<TEAnswer>> teAnswerMap, Map<String, TEPaper> tePaperMap, Map<Long, Map<String, TEPaperStruct>> tePaperStructTranMap) {
        courseMap.forEach((k, v) -> {
            int count = teCourseService.countByCourseCode(k);
            if (count == 0) {
                throw new BusinessException("科目编码" + k + "不存在");
            }
        });

        tbSchoolCollegeService.deleteAll(tbSchool.getId());
        int min = 0;
        int max = SystemConstant.MAX_IMPORT_SIZE, size = studentMap.keySet().size();
        if (max >= size) {
            max = size;
        }
        List list = Arrays.asList(studentMap.keySet().toArray());
        while (max <= size) {
            List subList = list.subList(min, max);
            teStudentService.deleteAll(tbSchool.getId(), new HashSet<>(subList));
            if (max == size) {
                break;
            }
            min = max;
            max += SystemConstant.MAX_IMPORT_SIZE;
            if (max >= size) {
                max = size;
            }
        }
        tbTeacherService.deleteAll(tbSchool.getId());

        List<TBSchoolCollege> tbSchoolCollegeList = new ArrayList();
        collegeMap.forEach((k, v) -> {
            tbSchoolCollegeList.add(v);
        });
        tbSchoolCollegeService.saveOrUpdateBatch(tbSchoolCollegeList);

        List<TEStudent> teStudentList = new ArrayList();
        studentMap.forEach((k, v) -> {
            teStudentList.add(v);
        });
        teStudentService.saveOrUpdateBatch(teStudentList);

        List<TEExamStudent> teExamStudentList = new ArrayList();
        examStudentMap.forEach((k, v) -> {
            QueryWrapper<TEExamStudent> teExamStudentQueryWrapper = new QueryWrapper<>();
            teExamStudentQueryWrapper.lambda().eq(TEExamStudent::getExamId, examId)
                    .eq(TEExamStudent::getStudentCode, v.getStudentCode())
                    .eq(TEExamStudent::getExamNumber, v.getExamNumber())
                    .eq(TEExamStudent::getCourseCode, v.getCourseCode());
            TEExamStudent teExamStudent = teExamStudentService.getOne(teExamStudentQueryWrapper);
            if (Objects.nonNull(teExamStudent)) {
                QueryWrapper<TEExamRecord> teExamRecordQueryWrapper = new QueryWrapper<>();
                teExamRecordQueryWrapper.lambda().eq(TEExamRecord::getExamId, examId)
                        .eq(TEExamRecord::getExamStudentId, teExamStudent.getId())
                        .eq(TEExamRecord::getPaperId, tePaperMap.get(teExamStudent.getCourseCode()).getId());
                TEExamRecord teExamRecord = teExamRecordService.getOne(teExamRecordQueryWrapper);
                if (Objects.nonNull(teExamRecord)) {
                    teAnswerService.deleteAll(teExamRecord.getId());
                    teExamRecordService.remove(teExamRecordQueryWrapper);
                }
                teExamStudentService.remove(teExamStudentQueryWrapper);
            }
            teExamStudentList.add(v);
        });
        teExamStudentService.saveOrUpdateBatch(teExamStudentList);

        List<TBTeacher> tbTeacherList = new ArrayList();
        teacherMap.forEach((k, v) -> {
            QueryWrapper<TBTeacher> tbTeacherQueryWrapper = new QueryWrapper<>();
            tbTeacherQueryWrapper.lambda().eq(TBTeacher::getSchoolId, tbSchool.getId())
                    .eq(TBTeacher::getName, v.getName());
            TBTeacher tbTeacher = tbTeacherService.getOne(tbTeacherQueryWrapper);

            if (Objects.nonNull(tbTeacher)) {
                QueryWrapper<TBTeacherExamStudent> tbTeacherExamStudentQueryWrapper = new QueryWrapper<>();
                tbTeacherExamStudentQueryWrapper.lambda().eq(TBTeacherExamStudent::getTeacherId, tbTeacher.getId());
                tbTeacherExamStudentService.remove(tbTeacherExamStudentQueryWrapper);
            }
            tbTeacherList.add(v);
        });
        tbTeacherService.saveOrUpdateBatch(tbTeacherList);

        List<TBTeacherExamStudent> tbTeacherExamStudentList = new ArrayList();
        teacherExamStudentMap.forEach((k, v) -> {
            tbTeacherExamStudentList.addAll(v);
        });
        tbTeacherExamStudentService.saveOrUpdateBatch(tbTeacherExamStudentList);

        List<TEExamRecord> teExamRecordList = new ArrayList();
        examRecordMap.forEach((k, v) -> {
            teExamRecordList.add(v);
        });
        teExamRecordService.saveOrUpdateBatch(teExamRecordList);

        List<TEAnswer> teAnswerList = new ArrayList();
        teAnswerMap.forEach((k, v) -> {
            teAnswerList.addAll(v);
        });
        teAnswerService.saveOrUpdateBatch(teAnswerList);
    }
}
