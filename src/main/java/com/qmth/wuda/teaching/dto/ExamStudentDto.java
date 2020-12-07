package com.qmth.wuda.teaching.dto;

import com.qmth.wuda.teaching.dto.common.ExamStudentCommonDto;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: 考生临时传输对象 dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/4
 */
public class ExamStudentDto extends ExamStudentCommonDto implements Serializable {

    @ApiModelProperty(value = "客观分总分")
    private Double objectiveScore;

    @ApiModelProperty(value = "主观分总分")
    private Double subjectiveScore;

    @ApiModelProperty(value = "总分")
    private Double sumScore;

    public Double getObjectiveScore() {
        return objectiveScore;
    }

    public void setObjectiveScore(Double objectiveScore) {
        this.objectiveScore = objectiveScore;
    }

    public Double getSubjectiveScore() {
        return subjectiveScore;
    }

    public void setSubjectiveScore(Double subjectiveScore) {
        this.subjectiveScore = subjectiveScore;
    }

    public Double getSumScore() {
        return sumScore;
    }

    public void setSumScore(Double sumScore) {
        this.sumScore = sumScore;
    }
}
