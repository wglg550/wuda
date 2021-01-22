package com.qmth.wuda.teaching.dto.excel;

import com.qmth.wuda.teaching.annotation.ExcelNotNull;
import com.qmth.wuda.teaching.annotation.ExcelNote;

import java.io.Serializable;

/**
* @Description: assign dto 
* @Param:  
* @return:  
* @Author: wangliang
* @Date: 2021/1/22 
*/ 
public class AssignImportDto implements Serializable {

    @ExcelNote(value = "考试编号")
    @ExcelNotNull
    private String examCode;

    @ExcelNote(value = "科目编号")
    @ExcelNotNull
    private String courseCode;

    @ExcelNote(value = "学号")
    @ExcelNotNull
    private String studentCode;

    @ExcelNote(value = "赋分")
    @ExcelNotNull
    private Double contributionScore;

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public Double getContributionScore() {
        return contributionScore;
    }

    public void setContributionScore(Double contributionScore) {
        this.contributionScore = contributionScore;
    }
}
