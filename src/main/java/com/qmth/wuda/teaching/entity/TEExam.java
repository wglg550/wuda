package com.qmth.wuda.teaching.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 考试批次表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_e_exam", description = "考试批次表")
public class TEExam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "学校id")
    @TableField(value = "school_id")
    private Long schoolId;

    @ApiModelProperty(value = "名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "编码")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "开始时间")
    @TableField(value = "start_time")
    private Long startTime;

    @ApiModelProperty(value = "结束时间")
    @TableField(value = "end_time")
    private Long endTime;

    @ApiModelProperty(value = "是否启用，0：停用，1：启用")
    @TableField(value = "enable")
    private Integer enable;

    public TEExam() {

    }

    public TEExam(Long schoolId, String name, String code, Long startTime, Long endTime) {
        this.schoolId = schoolId;
        this.name = name;
        this.code = code;
        this.startTime = startTime;
        this.endTime = endTime;
        enable = 1;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
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

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
