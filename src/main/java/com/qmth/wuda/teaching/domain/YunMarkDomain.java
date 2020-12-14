package com.qmth.wuda.teaching.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 系统配置
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2020/4/10
 */
public class YunMarkDomain implements Serializable {

    private static final long serialVersionUID = 7510626406622200443L;

    private String url;

    private String studentScoreApi;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStudentScoreApi() {
        return studentScoreApi;
    }

    public void setStudentScoreApi(String studentScoreApi) {
        this.studentScoreApi = studentScoreApi;
    }
}
