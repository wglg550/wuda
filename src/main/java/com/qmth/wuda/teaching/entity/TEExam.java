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

/**
 * <p>
 * 考试批次表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_e_exam", description = "考试批次表")
public class TEExam extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "编码")
    @TableField(value = "code")
    private String code;

    @ApiModelProperty(value = "是否启用，0：停用，1：启用")
    @TableField(value = "enable")
    private Integer enable;

    @ApiModelProperty(value = "密钥key")
    @TableField(value = "access_key")
    private String accessKey;

    @ApiModelProperty(value = "密钥secret")
    @TableField(value = "access_secret")
    private String accessSecret;

    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;

    public TEExam() {

    }

    public TEExam(Long examId, String name, String code, String accessKey, String accessSecret) {
        setId(examId);
        this.name = name + "_" + examId;
        this.code = code;
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.enable = 1;
    }

    public TEExam(String name, String code, String accessKey, String accessSecret) {
        setId(UidUtil.nextId());
        this.name = name + "_" + code;
        this.code = code;
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.enable = 1;
    }

    public TEExam(String name, String code) {
        setId(UidUtil.nextId());
        this.name = name;
        this.code = code;
        this.enable = 1;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
