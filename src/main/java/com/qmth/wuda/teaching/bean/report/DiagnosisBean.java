package com.qmth.wuda.teaching.bean.report;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/** 
* @Description: 诊断bean
* @Param:  
* @return:  
* @Author: wangliang
* @Date: 2020/12/7 
*/ 
public class DiagnosisBean implements Serializable {

    @ApiModelProperty(value = "是否通过")
    private boolean result = false;

    @ApiModelProperty(value = "诊断详情")
    private List<DiagnosisDetailBean> list;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<DiagnosisDetailBean> getList() {
        return list;
    }

    public void setList(List<DiagnosisDetailBean> list) {
        this.list = list;
    }
}
