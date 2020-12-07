package com.qmth.wuda.teaching.bean.report;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 模块bean
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/12/7
 */
public class ModuleBean implements Serializable {

    @ApiModelProperty(value = "模块名称")
    private List<ModuleDetailBean> dios;

    @ApiModelProperty(value = "说明")
    private String info;

    @ApiModelProperty(value = "备注")
    private String remark;

    public List<ModuleDetailBean> getDios() {
        return dios;
    }

    public void setDios(List<ModuleDetailBean> dios) {
        this.dios = dios;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
