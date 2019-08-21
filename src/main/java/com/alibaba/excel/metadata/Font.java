package com.alibaba.excel.metadata;

import java.io.Serializable;

/**
 *
 * @author jipengfei
 */
public class Font implements Serializable {
	private static final long serialVersionUID = 3402851460487820047L;

	/**
	 */
	private String fontName;

	/**
	 */
	private short fontHeightInPoints;

	/**
	 */
	private boolean bold;

	public String getFontName() {
		return fontName;
	}

	public void setFontName(String fontName) {
		this.fontName = fontName;
	}

	public short getFontHeightInPoints() {
		return fontHeightInPoints;
	}

	public void setFontHeightInPoints(short fontHeightInPoints) {
		this.fontHeightInPoints = fontHeightInPoints;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}
}
