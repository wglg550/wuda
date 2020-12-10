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
 * 考生信息表
 * </p>
 *
 * @author wangliang
 * @since 2020-12-01
 */
@ApiModel(value = "t_e_exam_student", description = "考生信息表")
public class TEExamStudent extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "考试批次id")
    @TableField(value = "exam_id")
    private Long examId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "学生id")
    @TableField(value = "student_id")
    private Long studentId;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "学院id")
    @TableField(value = "college_id")
    private Long collegeId;

    @ApiModelProperty(value = "科目名称")
    @TableField(value = "course_name")
    private String courseName;

    @ApiModelProperty(value = "科目编码")
    @TableField(value = "course_code")
    private String courseCode;

    @ApiModelProperty(value = "姓名")
    @TableField(value = "name")
    private String name;

    @ApiModelProperty(value = "学号")
    @TableField(value = "identity")
    private String identity;

    @ApiModelProperty(value = "准考证号")
    @TableField(value = "idcard_number")
    private String idcardNumber;

    @ApiModelProperty(value = "年级")
    @TableField(value = "grade")
    private String grade;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "专业id")
    @TableField(value = "major_id")
    private Long majorId;

    @ApiModelProperty(value = "是否缺考，0：否，1：是")
    @TableField(value = "miss")
    private Integer miss;

    @ApiModelProperty(value = "是否启用，0：停用，1：启用")
    @TableField(value = "enable")
    private Integer enable;

    @ApiModelProperty(value = "班级")
    @TableField(value = "class_no")
    private String classNo;

    public TEExamStudent() {

    }

    public TEExamStudent(Long examId, Long studentId, Long collegeId, Long majorId, String courseName, String courseCode, String name, String identity, String idcardNumber, Integer miss, String classNo) {
        setId(UidUtil.nextId());
        this.examId = examId;
        this.studentId = studentId;
        this.collegeId = collegeId;
        this.majorId = majorId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.name = name;
        this.identity = identity;
        this.idcardNumber = idcardNumber;
        this.miss = miss;
        this.classNo = classNo;
        this.enable = 1;
    }

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIdcardNumber() {
        return idcardNumber;
    }

    public void setIdcardNumber(String idcardNumber) {
        this.idcardNumber = idcardNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Integer getMiss() {
        return miss;
    }

    public void setMiss(Integer miss) {
        this.miss = miss;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }
}
