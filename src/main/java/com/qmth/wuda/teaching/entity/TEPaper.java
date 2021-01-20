package com.qmth.wuda.teaching.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qmth.wuda.teaching.base.BaseEntity;
import com.qmth.wuda.teaching.util.UidUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 试卷信息表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_e_paper", description = "试卷信息表")
public class TEPaper extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "考试批次id")
    @TableField(value = "exam_id")
    private Long examId;

    @ApiModelProperty(value = "名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "编码")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "试卷总分")
    @TableField(value = "total_score")
    private BigDecimal totalScore;

    @ApiModelProperty(value = "及格分")
    @TableField(value = "pass_score")
    private BigDecimal passScore;

    @ApiModelProperty(value = "赋分")
    @TableField(value = "contribution_score")
    private BigDecimal contributionScore;

    @ApiModelProperty(value = "是否赋分，0：不启用，1：启用")
    @TableField(value = "contribution")
    private Integer contribution;

    @ApiModelProperty(value = "科目名称")
    @TableField(value = "course_name")
    private String courseName;

    @ApiModelProperty(value = "科目编码")
    @TableField(value = "course_code")
    private String courseCode;

    @ApiModelProperty(value = "赋分分数")
    @TableField(value = "contribution_final_score")
    private BigDecimal contributionFinalScore;

    public TEPaper() {

    }

    public TEPaper(Long examId, String name, String code, BigDecimal totalScore, BigDecimal passScore, Integer contribution, BigDecimal contributionScore, String courseName, String courseCode) {
        setId(UidUtil.nextId());
        this.examId = examId;
        this.name = name;
        this.code = code;
        this.totalScore = totalScore;
        this.passScore = passScore;
        this.contribution = contribution;
        this.contributionScore = contributionScore;
        this.courseName = courseName;
        this.courseCode = courseCode;
    }

    public BigDecimal getContributionFinalScore() {
        return contributionFinalScore;
    }

    public void setContributionFinalScore(BigDecimal contributionFinalScore) {
        this.contributionFinalScore = contributionFinalScore;
    }

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

    public Integer getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public BigDecimal getPassScore() {
        return passScore;
    }

    public void setPassScore(BigDecimal passScore) {
        this.passScore = passScore;
    }

    public BigDecimal getContributionScore() {
        return contributionScore;
    }

    public void setContributionScore(BigDecimal contributionScore) {
        this.contributionScore = contributionScore;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }
}
