package com.qmth.wuda.teaching.bean.report.course;

import com.qmth.wuda.teaching.bean.report.SynthesisBean;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/** 
* @Description:  
* @Param:  
* @return:  
* @Author: wangliang
* @Date: 2020/12/28 
*/ 
public class ExamInfoBean implements Serializable {

    @ApiModelProperty(value = "考试代码")
    private String examCode;

    @ApiModelProperty(value = "考试名称")
    private String examName;

    @ApiModelProperty(value = "考试时间")
    private String createTime;

    @ApiModelProperty(value = "科目信息")
    private List<CourseInfoBean> courseInfo;

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<CourseInfoBean> getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(List<CourseInfoBean> courseInfo) {
        this.courseInfo = courseInfo;
    }
}
