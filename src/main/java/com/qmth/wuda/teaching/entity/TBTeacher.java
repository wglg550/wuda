package com.qmth.wuda.teaching.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 教师信息表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_b_teacher", description = "教师信息表")
public class TBTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "学校id")
    @TableField(value = "school_id")
    private Long schoolId;

    @ApiModelProperty(value = "学院id")
    @TableField(value = "college_id")
    private Long collegeId;

    @ApiModelProperty(value = "专业id")
    @TableField(value = "major_id")
    private Long majorId;

    @ApiModelProperty(value = "姓名")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "是否启用，0：停用，1：启用")
    @TableField(value = "enable")
    private Integer enable;

    @ApiModelProperty(value = "专业名称")
    @TableField(exist = false)
    private String majorName;

    public TBTeacher() {

    }

    public TBTeacher(Long schoolId, String name,String majorName) {
        this.schoolId = schoolId;
        this.name = name;
        this.majorName = majorName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
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

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
}
