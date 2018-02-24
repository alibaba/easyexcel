package com.alibaba.excel.metadata;

import java.util.List;

/**
 * Sheet参数
 *
 * @author jipengfei
 */
public class Sheet {

    /**
     * 表头行数
     */
    private int headLineMun;

    /**
     * sheet序号 从0开始
     */
    private int sheetNo;

    /**
     * 名称 可不填
     */
    private String sheetName;

    /**
     * 对用的表头模型
     */
    private Class<? extends BaseRowModel> clazz;

    /**
     * 对用的表头层级树,用于clazz不确定时候，动态生成表头
     */
    private List<List<String>> head;

    /**
     *
     */
    private TableStyle tableStyle;

    public Sheet(int sheetNo) {
        this.sheetNo = sheetNo;
    }

    public Sheet(int sheetNo, int headLineMun) {
        this.sheetNo = sheetNo;
        this.headLineMun = headLineMun;
    }

    public Sheet(int sheetNo, int headLineMun, Class<? extends BaseRowModel> clazz) {
        this.sheetNo = sheetNo;
        this.headLineMun = headLineMun;
        this.clazz = clazz;
    }

    public Sheet(int sheetNo, int headLineMun, Class<? extends BaseRowModel> clazz, String sheetName,
                 List<List<String>> head) {
        this.sheetNo = sheetNo;
        this.clazz = clazz;
        this.headLineMun = headLineMun;
        this.sheetName = sheetName;
        this.head = head;
    }

    public List<List<String>> getHead() {
        return head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public Class<? extends BaseRowModel> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends BaseRowModel> clazz) {
        this.clazz = clazz;
        if (headLineMun == 0) {
            this.headLineMun = 1;
        }
    }

    public int getHeadLineMun() {
        return headLineMun;
    }

    public void setHeadLineMun(int headLineMun) {
        this.headLineMun = headLineMun;
    }

    public int getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(int sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public TableStyle getTableStyle() {
        return tableStyle;
    }

    public void setTableStyle(TableStyle tableStyle) {
        this.tableStyle = tableStyle;
    }

    @Override
    public String toString() {
        return "Sheet{" +
            "headLineMun=" + headLineMun +
            ", sheetNo=" + sheetNo +
            ", sheetName='" + sheetName + '\'' +
            ", clazz=" + clazz +
            ", head=" + head +
            ", tableStyle=" + tableStyle +
            '}';
    }
}
