package com.qmth.wuda.teaching.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.qmth.wuda.teaching.constant.SystemConstant;
import com.qmth.wuda.teaching.dto.common.ExamStudentCommonDto;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @Description: 考生临时传输对象 dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/4
 */
public class ExamStudentDto extends ExamStudentCommonDto implements Serializable {

    @ApiModelProperty(value = "客观分总分")
    private BigDecimal objectiveScore;

    @ApiModelProperty(value = "主观分总分")
    private BigDecimal subjectiveScore;

    @ApiModelProperty(value = "我的分数")
    private BigDecimal myScore;

    @ApiModelProperty(value = "学院id")
    private Long collegeId;

    @ApiModelProperty(value = "考试id")
    private Long examId;

    @ApiModelProperty(value = "试卷满分")
    private BigDecimal fullScore;

    @ApiModelProperty(value = "考试记录id")
    private Long examRecordId;

    @ApiModelProperty(value = "科目编码")
    private String courseCode;

    @ApiModelProperty(value = "及格分")
    private BigDecimal passScore;

    @ApiModelProperty(value = "赋分")
    private BigDecimal contributionScore;

    @ApiModelProperty(value = "是否赋分，0：不启用，1：启用")
    private Integer contribution;

    @ApiModelProperty(value = "试卷编码")
    private String paperCode;

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public Integer getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public BigDecimal getPassScore() {
        if (Objects.nonNull(passScore)) {
            return passScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setPassScore(BigDecimal passScore) {
        this.passScore = passScore;
    }

    public BigDecimal getContributionScore() {
        if (Objects.nonNull(contributionScore)) {
            return contributionScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setContributionScore(BigDecimal contributionScore) {
        this.contributionScore = contributionScore;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Long getExamRecordId() {
        return examRecordId;
    }

    public void setExamRecordId(Long examRecordId) {
        this.examRecordId = examRecordId;
    }

    public BigDecimal getMyScore() {
        if (Objects.nonNull(myScore)) {
            return myScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setMyScore(BigDecimal myScore) {
        this.myScore = myScore;
    }

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public BigDecimal getFullScore() {
        if (Objects.nonNull(fullScore)) {
            return fullScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setFullScore(BigDecimal fullScore) {
        this.fullScore = fullScore;
    }

    public BigDecimal getObjectiveScore() {
        if (Objects.nonNull(objectiveScore)) {
            return objectiveScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setObjectiveScore(BigDecimal objectiveScore) {
        this.objectiveScore = objectiveScore;
    }

    public BigDecimal getSubjectiveScore() {
        if (Objects.nonNull(subjectiveScore)) {
            return subjectiveScore.setScale(SystemConstant.FINAL_SCALE, BigDecimal.ROUND_HALF_UP);
        } else {
            return new BigDecimal(0);
        }
    }

    public void setSubjectiveScore(BigDecimal subjectiveScore) {
        this.subjectiveScore = subjectiveScore;
    }
}
