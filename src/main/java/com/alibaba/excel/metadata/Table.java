package com.alibaba.excel.metadata;

import java.io.Serializable;
import java.util.List;

/**
 * @author jipengfei
 */
public class Table implements Serializable {
	private static final long serialVersionUID = -2139828468215892739L;

	/**
	 */
	private Class<? extends BaseRowModel> clazz;

	/**
	 */
	private List<List<String>> head;

	/**
	 */
	private int tableNo;

	/**
	 */
	private TableStyle tableStyle;

	public TableStyle getTableStyle() {
		return tableStyle;
	}

	public void setTableStyle(TableStyle tableStyle) {
		this.tableStyle = tableStyle;
	}

	public Table(Integer tableNo) {
		this.tableNo = tableNo;
	}

	public Class<? extends BaseRowModel> getClazz() {
		return clazz;
	}

	public void setClazz(Class<? extends BaseRowModel> clazz) {
		this.clazz = clazz;
	}

	public List<List<String>> getHead() {
		return head;
	}

	public void setHead(List<List<String>> head) {
		this.head = head;
	}

	public int getTableNo() {
		return tableNo;
	}

	public void setTableNo(int tableNo) {
		this.tableNo = tableNo;
	}
}
