package com.qmth.wuda.teaching.bean.report;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description: 诊断详细bean
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/7
 */
public class DiagnosisDetailBean implements Serializable {

    @ApiModelProperty(value = "结果")
    private String result;

    @ApiModelProperty(value = "建议")
    private String advice;

    @ApiModelProperty(value = "模块名称")
    private String name;

    @ApiModelProperty(value = "模块")
    private ModuleBean modules;

    @ApiModelProperty(value = "详情")
    private DimensionBean detail;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModuleBean getModules() {
        return modules;
    }

    public void setModules(ModuleBean modules) {
        this.modules = modules;
    }

    public DimensionBean getDetail() {
        return detail;
    }

    public void setDetail(DimensionBean detail) {
        this.detail = detail;
    }
}
