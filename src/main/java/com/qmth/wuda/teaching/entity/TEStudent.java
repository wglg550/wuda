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
 * 学生信息表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_e_student", description = "学生信息表")
public class TEStudent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "学校id")
    @TableField(value = "school_id")
    private Long schoolId;

    @ApiModelProperty(value = "姓名")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "密码")
    @TableField(value = "password")
    private String password;

    @ApiModelProperty(value = "身份证号")
    @TableField(value = "idcard_number")
    private String idcardNumber;

    @ApiModelProperty(value = "年龄")
    @TableField(value = "age")
    private Integer age;

    @ApiModelProperty(value = "性别")
    @TableField(value = "gender")
    private String gender;

    @ApiModelProperty(value = "手机号")
    @TableField(value = "mobile_number")
    private String mobileNumber;

    @ApiModelProperty(value = "底照")
    @TableField(value = "base_photo_path")
    private String basePhotoPath;

    @ApiModelProperty(value = "是否启用，0：停用，1：启用")
    @TableField(value = "enable")
    private Integer enable;

    public TEStudent() {

    }

    public TEStudent(Long schoolId, String name, String idcardNumber) {
        setId(UidUtil.nextId());
        this.schoolId = schoolId;
        this.name = name;
        this.idcardNumber = idcardNumber;
        this.password = "123456";
        this.enable = 1;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdcardNumber() {
        return idcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getBasePhotoPath() {
        return basePhotoPath;
    }

    public void setBasePhotoPath(String basePhotoPath) {
        this.basePhotoPath = basePhotoPath;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
