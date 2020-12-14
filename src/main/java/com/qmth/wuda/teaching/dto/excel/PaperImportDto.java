package com.qmth.wuda.teaching.dto.excel;

import com.qmth.wuda.teaching.annotation.ExcelNotNull;
import com.qmth.wuda.teaching.annotation.ExcelNote;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description: 试卷和题型导入 dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/2
 */
public class PaperImportDto implements Serializable {

    @ExcelNote(value = "试卷类型")
    @ExcelNotNull
    private String paperCode;

    @ExcelNote(value = "是否赋分，0：不启用，1：启用")
    private String contribution;

    @ExcelNote(value = "赋分系数")
    private String contributionScore;

    @ExcelNote(value = "及格分")
    @ExcelNotNull
    private String passScore;

    @ExcelNote(value = "试卷总分")
    @ExcelNotNull
    private String totalScore;

    @ExcelNote(value = "科目名称")
    @ExcelNotNull
    private String courseName;

    @ExcelNote(value = "科目编码")
    @ExcelNotNull
    private String courseCode;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public String getContributionScore() {
        return contributionScore;
    }

    public void setContributionScore(String contributionScore) {
        this.contributionScore = contributionScore;
    }

    public String getPassScore() {
        return passScore;
    }

    public void setPassScore(String passScore) {
        this.passScore = passScore;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }
}
