package com.qmth.wuda.teaching.dto.excel;

import com.qmth.wuda.teaching.annotation.ExcelNotNull;
import com.qmth.wuda.teaching.annotation.ExcelNote;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

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
    private Double contributionScore;

    @ExcelNote(value = "及格分")
    @ExcelNotNull
    private Double passScore;

    @ExcelNote(value = "试卷总分")
    @ExcelNotNull
    private Double totalScore;

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

    public Double getContributionScore() {
        return contributionScore;
    }

    public void setContributionScore(Double contributionScore) {
        this.contributionScore = contributionScore;
    }

    public Double getPassScore() {
        return passScore;
    }

    public void setPassScore(Double passScore) {
        this.passScore = passScore;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }
}
