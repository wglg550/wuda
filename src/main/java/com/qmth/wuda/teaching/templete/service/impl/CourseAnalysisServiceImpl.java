package com.qmth.wuda.teaching.templete.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qmth.wuda.teaching.bean.YyjSourceDataBean;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.entity.*;
import com.qmth.wuda.teaching.enums.CourseEnum;
import com.qmth.wuda.teaching.exception.BusinessException;
import com.qmth.wuda.teaching.service.*;
import com.qmth.wuda.teaching.templete.CourseAnalysisTemplete;
import com.qmth.wuda.teaching.templete.service.CourseAnalysisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Resource
    TBSchoolService tbSchoolService;

    @Resource
    TEPaperService tePaperService;

    @Resource
    TEPaperStructService tePaperStructService;

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
     * 获取学校信息
     *
     * @return
     */
    @Override
    public TBSchool getSchoolData() {
        QueryWrapper<TBSchool> tbSchoolQueryWrapper = new QueryWrapper<>();
        tbSchoolQueryWrapper.lambda().eq(TBSchool::getCode, "whdx");
        return tbSchoolService.getOne(tbSchoolQueryWrapper);
    }

    /**
     * 云阅卷数据解析
     *
     * @param examId
     * @param tbSchool
     * @param yyjSourceDataBean
     * @param courseAnalysisTemplete
     * @return
     */
    @Override
    public boolean yyjResolveData(Long examId, TBSchool tbSchool, YyjSourceDataBean yyjSourceDataBean, CourseAnalysisTemplete courseAnalysisTemplete) {
        yyjSourceDataBean.setSubjectCode(CourseEnum.convertToCode(yyjSourceDataBean.getSubjectName()));
        TEPaper tePaper = null;
        if (!courseAnalysisTemplete.getTePaperMap().containsKey(yyjSourceDataBean.getSubjectCode())) {
            QueryWrapper<TEPaper> tePaperQueryWrapper = new QueryWrapper<>();
            tePaperQueryWrapper.lambda().eq(TEPaper::getExamId, examId)
                    .eq(TEPaper::getCourseCode, yyjSourceDataBean.getSubjectCode());
            tePaper = tePaperService.getOne(tePaperQueryWrapper);
            if (Objects.isNull(tePaper)) {
                return false;
            }
            courseAnalysisTemplete.getTePaperMap().put(yyjSourceDataBean.getSubjectCode(), tePaper);
        } else {
            tePaper = courseAnalysisTemplete.getTePaperMap().get(yyjSourceDataBean.getSubjectCode());
        }

        Map<String, TEPaperStruct> tePaperStructMap = null;
        if (!courseAnalysisTemplete.getTePaperStructTranMap().containsKey(tePaper.getId())) {
            QueryWrapper<TEPaperStruct> tePaperStructQueryWrapper = new QueryWrapper<>();
            tePaperStructQueryWrapper.lambda().eq(TEPaperStruct::getPaperId, tePaper.getId());
            List<TEPaperStruct> tePaperStructList = tePaperStructService.list(tePaperStructQueryWrapper);
            if (Objects.isNull(tePaperStructList) || tePaperStructList.size() == 0) {
                throw new BusinessException("试卷结构为空");
            }
            if (Objects.nonNull(tePaperStructList) && tePaperStructList.size() > 0) {
                tePaperStructMap = tePaperStructList.stream().collect(Collectors.toMap(s -> s.getMainNumber() + "-" + s.getSubNumber(), Function.identity(), (dto1, dto2) -> dto1));
                courseAnalysisTemplete.getTePaperStructTranMap().put(tePaper.getId(), tePaperStructMap);
            }
        } else {
            tePaperStructMap = courseAnalysisTemplete.getTePaperStructTranMap().get(tePaper.getId());
        }
        courseAnalysisTemplete.setTePaperStructMap(tePaperStructMap);

        if (!courseAnalysisTemplete.getCourseMap().containsKey(yyjSourceDataBean.getSubjectCode())) {
            courseAnalysisTemplete.getCourseMap().put(yyjSourceDataBean.getSubjectCode(), yyjSourceDataBean.getSubjectName());
        }
        if (!courseAnalysisTemplete.getCollegeMap().containsKey(yyjSourceDataBean.getCollege())) {
            TBSchoolCollege tbSchoolCollege = new TBSchoolCollege(tbSchool.getId(), yyjSourceDataBean.getCollege(), yyjSourceDataBean.getCollege());
            courseAnalysisTemplete.getCollegeMap().put(yyjSourceDataBean.getCollege(), tbSchoolCollege);
        }
        if (!courseAnalysisTemplete.getStudentMap().containsKey(yyjSourceDataBean.getStudentCode())) {
            TEStudent teStudent = new TEStudent(tbSchool.getId(), yyjSourceDataBean.getName(), yyjSourceDataBean.getStudentCode());
            courseAnalysisTemplete.getStudentMap().put(yyjSourceDataBean.getStudentCode(), teStudent);
        }
        if (!courseAnalysisTemplete.getExamStudentMap().containsKey(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber())) {
            TEExamStudent teExamStudent = new TEExamStudent(examId, courseAnalysisTemplete.getStudentMap().get(yyjSourceDataBean.getStudentCode()).getId(), courseAnalysisTemplete.getCollegeMap().get(yyjSourceDataBean.getCollege()).getId(), null, yyjSourceDataBean.getSubjectName(), yyjSourceDataBean.getSubjectCode(), yyjSourceDataBean.getName(), yyjSourceDataBean.getStudentCode(), yyjSourceDataBean.getExamNumber(), yyjSourceDataBean.getStatus(), yyjSourceDataBean.getClassName(), yyjSourceDataBean.getPaperType());
            courseAnalysisTemplete.getExamStudentMap().put(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber(), teExamStudent);
        }
        if (!courseAnalysisTemplete.getTeacherMap().containsKey(yyjSourceDataBean.getTeacher())) {
            TBTeacher tbTeacher = new TBTeacher(tbSchool.getId(), courseAnalysisTemplete.getCollegeMap().get(yyjSourceDataBean.getCollege()).getId(), null, yyjSourceDataBean.getTeacher());
            courseAnalysisTemplete.getTeacherMap().put(yyjSourceDataBean.getTeacher(), tbTeacher);
        }
        TBTeacherExamStudent tbTeacherExamStudent = new TBTeacherExamStudent(courseAnalysisTemplete.getTeacherMap().get(yyjSourceDataBean.getTeacher()).getId(), courseAnalysisTemplete.getExamStudentMap().get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId());
        courseAnalysisTemplete.getTeacherExamStudentMap().add(courseAnalysisTemplete.getTeacherMap().get(yyjSourceDataBean.getTeacher()).getId(), tbTeacherExamStudent);

        if (!courseAnalysisTemplete.getExamRecordMap().containsKey(courseAnalysisTemplete.getExamStudentMap().get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId())) {
            TEExamRecord teExamRecord = new TEExamRecord(examId, tePaper.getId(), courseAnalysisTemplete.getExamStudentMap().get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId(), new BigDecimal(yyjSourceDataBean.getObjectiveScore()), new BigDecimal(yyjSourceDataBean.getSubjectiveScore()), new BigDecimal(yyjSourceDataBean.getTotalScore()), null, null);
            courseAnalysisTemplete.getExamRecordMap().put(courseAnalysisTemplete.getExamStudentMap().get(yyjSourceDataBean.getStudentCode() + "_" + yyjSourceDataBean.getExamNumber()).getId(), teExamRecord);
        }
        return true;
    }

    /**
     * 保存数据库
     *
     * @param examId
     * @param tbSchool
     * @param courseAnalysisTemplete
     */
    @Override
    @Transactional
    public void saveYyjSourceDataForDb(Long examId, TBSchool tbSchool, CourseAnalysisTemplete courseAnalysisTemplete) {
        courseAnalysisTemplete.getCourseMap().forEach((k, v) -> {
            int count = teCourseService.countByCourseCode(k);
            if (count == 0) {
                throw new BusinessException("科目编码" + k + "不存在");
            }
        });

        tbSchoolCollegeService.deleteAll(tbSchool.getId());
        int min = 0;
        int max = SystemConstant.MAX_IMPORT_SIZE, size = courseAnalysisTemplete.getStudentMap().keySet().size();
        if (max >= size) {
            max = size;
        }
        List list = Arrays.asList(courseAnalysisTemplete.getStudentMap().keySet().toArray());
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
        courseAnalysisTemplete.getCollegeMap().forEach((k, v) -> {
            tbSchoolCollegeList.add(v);
        });
        tbSchoolCollegeService.saveOrUpdateBatch(tbSchoolCollegeList);

        List<TEStudent> teStudentList = new ArrayList();
        courseAnalysisTemplete.getStudentMap().forEach((k, v) -> {
            teStudentList.add(v);
        });
        teStudentService.saveOrUpdateBatch(teStudentList);

        List<TEExamStudent> teExamStudentList = new ArrayList();
        courseAnalysisTemplete.getExamStudentMap().forEach((k, v) -> {
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
                        .eq(TEExamRecord::getPaperId, courseAnalysisTemplete.getTePaperMap().get(teExamStudent.getCourseCode()).getId());
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
        courseAnalysisTemplete.getTeacherMap().forEach((k, v) -> {
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
        courseAnalysisTemplete.getTeacherExamStudentMap().forEach((k, v) -> {
            tbTeacherExamStudentList.addAll(v);
        });
        tbTeacherExamStudentService.saveOrUpdateBatch(tbTeacherExamStudentList);

        List<TEExamRecord> teExamRecordList = new ArrayList();
        courseAnalysisTemplete.getExamRecordMap().forEach((k, v) -> {
            teExamRecordList.add(v);
        });
        teExamRecordService.saveOrUpdateBatch(teExamRecordList);

        List<TEAnswer> teAnswerList = new ArrayList();
        courseAnalysisTemplete.getTeAnswerMap().forEach((k, v) -> {
            teAnswerList.addAll(v);
        });
        teAnswerService.saveOrUpdateBatch(teAnswerList);
    }
}
