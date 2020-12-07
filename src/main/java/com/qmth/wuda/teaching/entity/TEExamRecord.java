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
    private BigDecimal objectiveScore;

    @ApiModelProperty(value = "主观分")
    @TableField(value = "subjective_score")
    private BigDecimal subjectiveScore;

    @ApiModelProperty(value = "总分")
    @TableField(value = "sum_score")
    private BigDecimal sumScore;

    public TEExamRecord() {

    }

    public TEExamRecord(Long examId, Long paperId, Long examStudentId, BigDecimal objectiveScore, BigDecimal subjectiveScore, BigDecimal sumScore) {
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

    public BigDecimal getObjectiveScore() {
        return objectiveScore;
    }

    public void setObjectiveScore(BigDecimal objectiveScore) {
        this.objectiveScore = objectiveScore;
    }

    public BigDecimal getSubjectiveScore() {
        return subjectiveScore;
    }

    public void setSubjectiveScore(BigDecimal subjectiveScore) {
        this.subjectiveScore = subjectiveScore;
    }

    public BigDecimal getSumScore() {
        return sumScore;
    }

    public void setSumScore(BigDecimal sumScore) {
        this.sumScore = sumScore;
    }
}
