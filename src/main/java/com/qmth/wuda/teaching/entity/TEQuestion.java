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
 * 试卷结构题型说明
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_e_question", description = "试卷结构题型说明")
public class TEQuestion extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "试卷id")
    @TableField(value = "paper_id")
    private Long paperId;

    @ApiModelProperty(value = "大题号")
    @TableField(value = "main_number")
    private Integer mainNumber;

    @ApiModelProperty(value = "小题号")
    @TableField(value = "sub_number")
    private Integer subNumber;

    @ApiModelProperty(value = "类型")
    @TableField(value = "type")
    private String type;

    @ApiModelProperty(value = "题目分数")
    @TableField(value = "score")
    private BigDecimal score;

    @ApiModelProperty(value = "计分规则")
    @TableField(value = "rule")
    private String rule;

    @ApiModelProperty(value = "规则说明")
    @TableField(value = "description")
    private String description;

    @ApiModelProperty(value = "知识维度")
    @TableField(value = "knowledge")
    private String knowledge;

    @ApiModelProperty(value = "能力维度")
    @TableField(value = "capability")
    private String capability;

    public TEQuestion() {

    }

    public TEQuestion(Long paperId, Integer mainNumber, Integer subNumber, String type, BigDecimal score, String rule, String description, String knowledge, String capability) {
        setId(UidUtil.nextId());
        this.paperId = paperId;
        this.mainNumber = mainNumber;
        this.subNumber = subNumber;
        this.type = type;
        this.score = score;
        this.rule = rule;
        this.description = description;
        this.knowledge = knowledge;
        this.capability = capability;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
