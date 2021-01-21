package com.qmth.wuda.teaching.templete;

import com.qmth.wuda.teaching.bean.Result;
import com.qmth.wuda.teaching.entity.*;
import org.springframework.util.LinkedMultiValueMap;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 科目分析模版
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2021/1/21
 */
public abstract class CourseAnalysisTemplete {

    Map<String, String> courseMap;

    Map<String, TBSchoolCollege> collegeMap;

    Map<String, TEStudent> studentMap;

    Map<String, TEExamStudent> examStudentMap;

    Map<String, TBTeacher> teacherMap;

    LinkedMultiValueMap<Long, TBTeacherExamStudent> teacherExamStudentMap;

    Map<Long, TEExamRecord> examRecordMap;

    Map<Long, List<TEAnswer>> teAnswerMap;

    Map<String, TEPaper> tePaperMap;

    Map<Long, Map<String, TEPaperStruct>> tePaperStructTranMap;

    Map<String, TEPaperStruct> tePaperStructMap;

    public CourseAnalysisTemplete(){
        courseMap = new LinkedHashMap<>();
        collegeMap = new LinkedHashMap<>();
        studentMap = new LinkedHashMap<>();
        examStudentMap = new LinkedHashMap<>();
        teacherMap = new LinkedHashMap<>();
        teacherExamStudentMap = new LinkedMultiValueMap<>();
        examRecordMap = new LinkedHashMap<>();
        teAnswerMap = new LinkedHashMap<>();
        tePaperMap = new LinkedHashMap<>();
        tePaperStructTranMap = new LinkedHashMap<>();
    }

    public Map<String, String> getCourseMap() {
        return courseMap;
    }

    public void setCourseMap(Map<String, String> courseMap) {
        this.courseMap = courseMap;
    }

    public Map<String, TBSchoolCollege> getCollegeMap() {
        return collegeMap;
    }

    public void setCollegeMap(Map<String, TBSchoolCollege> collegeMap) {
        this.collegeMap = collegeMap;
    }

    public Map<String, TEStudent> getStudentMap() {
        return studentMap;
    }

    public void setStudentMap(Map<String, TEStudent> studentMap) {
        this.studentMap = studentMap;
    }

    public Map<String, TEExamStudent> getExamStudentMap() {
        return examStudentMap;
    }

    public void setExamStudentMap(Map<String, TEExamStudent> examStudentMap) {
        this.examStudentMap = examStudentMap;
    }

    public Map<String, TBTeacher> getTeacherMap() {
        return teacherMap;
    }

    public void setTeacherMap(Map<String, TBTeacher> teacherMap) {
        this.teacherMap = teacherMap;
    }

    public LinkedMultiValueMap<Long, TBTeacherExamStudent> getTeacherExamStudentMap() {
        return teacherExamStudentMap;
    }

    public void setTeacherExamStudentMap(LinkedMultiValueMap<Long, TBTeacherExamStudent> teacherExamStudentMap) {
        this.teacherExamStudentMap = teacherExamStudentMap;
    }

    public Map<Long, TEExamRecord> getExamRecordMap() {
        return examRecordMap;
    }

    public void setExamRecordMap(Map<Long, TEExamRecord> examRecordMap) {
        this.examRecordMap = examRecordMap;
    }

    public Map<Long, List<TEAnswer>> getTeAnswerMap() {
        return teAnswerMap;
    }

    public void setTeAnswerMap(Map<Long, List<TEAnswer>> teAnswerMap) {
        this.teAnswerMap = teAnswerMap;
    }

    public Map<String, TEPaper> getTePaperMap() {
        return tePaperMap;
    }

    public void setTePaperMap(Map<String, TEPaper> tePaperMap) {
        this.tePaperMap = tePaperMap;
    }

    public Map<Long, Map<String, TEPaperStruct>> getTePaperStructTranMap() {
        return tePaperStructTranMap;
    }

    public void setTePaperStructTranMap(Map<Long, Map<String, TEPaperStruct>> tePaperStructTranMap) {
        this.tePaperStructTranMap = tePaperStructTranMap;
    }

    public Map<String, TEPaperStruct> getTePaperStructMap() {
        return tePaperStructMap;
    }

    public void setTePaperStructMap(Map<String, TEPaperStruct> tePaperStructMap) {
        this.tePaperStructMap = tePaperStructMap;
    }

    /**
     * 数据分析模版
     *
     * @param examId
     * @param examCode
     * @return
     */
    public abstract Result dataAnalysis(Long examId, String examCode) throws IOException;
}
