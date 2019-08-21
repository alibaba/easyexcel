package com.alibaba.excel.metadata;

import java.io.Serializable;

/**
 * @author jipengfei
 */
public class CellRange implements Serializable {
	private static final long serialVersionUID = 463002544915617383L;

	private int firstRow;
	private int lastRow;
	private int firstCol;
	private int lastCol;

	public CellRange(int firstRow, int lastRow, int firstCol, int lastCol) {
		this.firstRow = firstRow;
		this.lastRow = lastRow;
		this.firstCol = firstCol;
		this.lastCol = lastCol;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public int getLastRow() {
		return lastRow;
	}

	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}

	public int getFirstCol() {
		return firstCol;
	}

	public void setFirstCol(int firstCol) {
		this.firstCol = firstCol;
	}

	public int getLastCol() {
		return lastCol;
	}

	public void setLastCol(int lastCol) {
		this.lastCol = lastCol;
	}
}
