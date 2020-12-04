package com.qmth.wuda.teaching.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.qmth.wuda.teaching.base.BaseEntity;
import com.qmth.wuda.teaching.util.UidUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 考试记录表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_e_exam_record", description = "考试记录表")
public class TEExamRecord extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "考试批次id")
    @TableField(value = "exam_id")
    private Long examId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "考生主键")
    @TableField(value = "exam_student_id")
    private Long examStudentId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "试卷id")
    @TableField(value = "paper_id")
    private Long paperId;

    @ApiModelProperty(value = "客观分")
    @TableField(value = "objective_score")
    private Double objectiveScore;

    @ApiModelProperty(value = "主观分")
    @TableField(value = "subjective_score")
    private Double subjectiveScore;

    @ApiModelProperty(value = "总分")
    @TableField(value = "sum_score")
    private Double sumScore;

    public TEExamRecord() {

    }

    public TEExamRecord(Long examId, Long paperId, Long examStudentId, Double objectiveScore, Double subjectiveScore, Double sumScore) {
        setId(UidUtil.nextId());
        this.examId = examId;
        this.paperId = paperId;
        this.examStudentId = examStudentId;
        this.objectiveScore = objectiveScore;
        this.subjectiveScore = subjectiveScore;
        this.sumScore = sumScore;
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

    public Long getExamStudentId() {
        return examStudentId;
    }

    public void setExamStudentId(Long examStudentId) {
        this.examStudentId = examStudentId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

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
