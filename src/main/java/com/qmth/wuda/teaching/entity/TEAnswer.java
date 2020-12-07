package com.qmth.wuda.teaching.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * 考生作答信息表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_e_answer", description = "考生作答信息表")
public class TEAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty(value = "大题号")
    @TableField(value = "main_number")
    private Integer mainNumber;

    @ApiModelProperty(value = "小题号")
    @TableField(value = "sub_number")
    private Integer subNumber;

    @ApiModelProperty(value = "类型")
    @TableField(value = "type")
    private String type;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "考试记录id")
    @TableField(value = "exam_record_id")
    private Long examRecordId;

    @ApiModelProperty(value = "作答内容")
    @TableField(value = "answer")
    private String answer;

    @ApiModelProperty(value = "分数")
    @TableField(value = "score")
    private BigDecimal score;

    @ApiModelProperty(value = "作答轨迹")
    @TableField(value = "history")
    private String history;

    @ApiModelProperty(value = "作答时长")
    @TableField(value = "duration_seconds")
    private Integer durationSeconds;

    @ApiModelProperty(value = "版本号")
    @TableField(value = "version")
    private Integer version = 0;

    public TEAnswer() {

    }

    public TEAnswer(Integer mainNumber, Integer subNumber, String type, Long examRecordId) {
        setId(UidUtil.nextId());
        this.mainNumber = mainNumber;
        this.subNumber = subNumber;
        this.type = type;
        this.examRecordId = examRecordId;
    }

    public TEAnswer(Integer mainNumber, Integer subNumber, String type, Long examRecordId, String answer, Integer version) {
        setId(UidUtil.nextId());
        this.mainNumber = mainNumber;
        this.subNumber = subNumber;
        this.type = type;
        this.examRecordId = examRecordId;
        this.answer = answer;
        this.version = version;
    }

    public TEAnswer(Integer mainNumber, Integer subNumber, String type, Long examRecordId, BigDecimal score, Integer version) {
        setId(UidUtil.nextId());
        this.mainNumber = mainNumber;
        this.subNumber = subNumber;
        this.type = type;
        this.examRecordId = examRecordId;
        this.score = score;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getMainNumber() {
        return mainNumber;
    }

    public void setMainNumber(Integer mainNumber) {
        this.mainNumber = mainNumber;
    }

    public Integer getSubNumber() {
        return subNumber;
    }

    public void setSubNumber(Integer subNumber) {
        this.subNumber = subNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getExamRecordId() {
        return examRecordId;
    }

    public void setExamRecordId(Long examRecordId) {
        this.examRecordId = examRecordId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
