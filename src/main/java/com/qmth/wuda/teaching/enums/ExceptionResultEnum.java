package com.qmth.wuda.teaching.enums;

/**
 * @Description: 自定义异常消息枚举
 * @Param:
 * @return:
 * @Author: wangliang
 * @Date: 2019/10/11
 */
public enum ExceptionResultEnum {
    /**
     * 系统预置
     */
    ERROR(500, 500, "失败"),

    INVOCATIONTARGET_ERROR(500, 501, "反射异常:"),

    SERVICE_NOT_FOUND(500, 502, "服务器错误"),

    SQL_ERROR(500, 503, "sql异常:"),

    UNKONW_ERROR(500, 504, "未知错误:请联系管理员"),

    EXCEPTION_ERROR(500, 505, "系统异常:请联系管理员"),

    CONTEXT_HOLDER_ERROR(500, 506, "applicaitonContext属性为null,请检查是否注入了SpringContextHolder!"),

    ATTACHMENT_ERROR(500, 507,"上传文件失败"),


    SUCCESS(200, 200, "成功"),


    EXCEL_NO(400, 400001, "excel没有数据"),

    ATTACHMENT_TYPE_IS_NULL(400, 400002, "请上传文件类型"),

    ATTACHMENT_IS_NULL(400, 400003, "请上传附件"),

    AUTHORIZATION_INVALID(401, 401001, "authorization无效"),

    YUN_API_INVALID(401, 401002, "云阅卷鉴权失败");

    private int statusCode;
    private int code;
    private String message;

    ExceptionResultEnum(int code, int statusCode, String message) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}