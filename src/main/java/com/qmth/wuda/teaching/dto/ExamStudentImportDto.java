package com.qmth.wuda.teaching.dto;

import com.qmth.wuda.teaching.annotation.ExcelNotNull;
import com.qmth.wuda.teaching.annotation.ExcelNote;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description: 考生导入 dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/2
 */
public class ExamStudentImportDto implements Serializable {

    @ExcelNote(value = "学院")
    @ExcelNotNull
    private String collegeName;

    @ExcelNote(value = "科目代码")
    @ExcelNotNull
    private String courseCode;

    @ExcelNote(value = "科目名称")
    @ExcelNotNull
    private String courseName;

    @ExcelNote(value = "层次")
    @ExcelNotNull
    private String level;

    @ExcelNote(value = "是否缺考")
    @ExcelNotNull
    private String miss;

    @ExcelNote(value = "身份证号")
    @ExcelNotNull
    private String idcardNumber;

    @ExcelNote(value = "学号")
    @ExcelNotNull
    private String identity;

    @ExcelNote(value = "姓名")
    @ExcelNotNull
    private String examStudentName;

    @ExcelNote(value = "教师")
    @ExcelNotNull
    private String teacherName;

    @ExcelNote(value = "年级")
    @ExcelNotNull
    private String grade;

    @ExcelNote(value = "专业")
    @ExcelNotNull
    private String majorName;

    @ExcelNote(value = "客观分")
    @ExcelNotNull
    private String objectiveScore;

    @ExcelNote(value = "主观分")
    @ExcelNotNull
    private String subjectiveScore;

    @ExcelNote(value = "总分")
    @ExcelNotNull
    private String sumScore;

    @ExcelNote(value = "答题记录扩展")
    private Map<String, String> extendColumn;

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMiss() {
        return miss;
    }

    public void setMiss(String miss) {
        this.miss = miss;
    }

    public String getIdcardNumber() {
        return idcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getExamStudentName() {
        return examStudentName;
    }

    public void setExamStudentName(String examStudentName) {
        this.examStudentName = examStudentName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getObjectiveScore() {
        return objectiveScore;
    }

    public void setObjectiveScore(String objectiveScore) {
        this.objectiveScore = objectiveScore;
    }

    public String getSubjectiveScore() {
        return subjectiveScore;
    }

    public void setSubjectiveScore(String subjectiveScore) {
        this.subjectiveScore = subjectiveScore;
    }

    public String getSumScore() {
        return sumScore;
    }

    public void setSumScore(String sumScore) {
        this.sumScore = sumScore;
    }

    public Map<String, String> getExtendColumn() {
        return extendColumn;
    }

    public void setExtendColumn(Map<String, String> extendColumn) {
        this.extendColumn = extendColumn;
    }
}
