package com.qmth.wuda.teaching.dto.excel;

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

    @ExcelNote(value = "专业类别")
    private String majorType;

    @ExcelNote(value = "准考证号")
    @ExcelNotNull
    private String idcardNumber;

    @ExcelNote(value = "学号")
    @ExcelNotNull
    private String identity;

    @ExcelNote(value = "姓名")
    @ExcelNotNull
    private String examStudentName;

    @ExcelNote(value = "客观分")
    @ExcelNotNull
    private String objectiveScore;

    @ExcelNote(value = "主观分")
    @ExcelNotNull
    private String subjectiveScore;

    @ExcelNote(value = "总分")
    @ExcelNotNull
    private String sumScore;

    @ExcelNote(value = "评分明细")
    @ExcelNotNull
    private String markDetail;

    @ExcelNote(value = "备注")
    @ExcelNotNull
    private String remark;

    @ExcelNote(value = "班级")
    @ExcelNotNull
    private String classNo;

    @ExcelNote(value = "专业")
    @ExcelNotNull
    private String majorName;

    @ExcelNote(value = "任课老师")
    @ExcelNotNull
    private String teacherName;

    @ExcelNote(value = "答题记录扩展")
    private Map<String, Object> extendColumn;

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public String getMarkDetail() {
        return markDetail;
    }

    public void setMarkDetail(String markDetail) {
        this.markDetail = markDetail;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

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

    public Map<String, Object> getExtendColumn() {
        return extendColumn;
    }

    public void setExtendColumn(Map<String, Object> extendColumn) {
        this.extendColumn = extendColumn;
    }
}
