package com.alibaba.easyexcel.test.demo.write;

// enum for enum type
public enum NoticeTypeEnum {
    //通知类型1：月结收费 2：欠费催缴 3：欠费断能
    MONTH_SETTLE(1, "月结收费"),
    ARREARS_REMINDER(2, "欠费催缴"),
    ARREARS_OFF(3, "欠费断能");
    // field in enum
    private int code;
    // field in enum
    private String desc;

    NoticeTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public String getDesc() {
        return this.desc;
    }
}
