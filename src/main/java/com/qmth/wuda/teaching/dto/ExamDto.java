package com.qmth.wuda.teaching.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: 考试临时传输对象dto
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/15
 */
public class ExamDto implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "考试id")
    private Long id;

    @ApiModelProperty(value = "考试名称")
    private String examName;

    @ApiModelProperty(value = "考试编码")
    private String examCode;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
