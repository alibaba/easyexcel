package com.alibaba.excel.metadata;

import org.apache.poi.xssf.usermodel.XSSFClientAnchor;

/**
 * Image data
 */
public class ImageData {

    /**
     * Suffix of an image file
     */
    private String fileSuffix;

    private byte[] imageValue;

    /**
     * An image's position message in a worksheet
     */
    private XSSFClientAnchor imageAnchor = new XSSFClientAnchor();

    private boolean isAnchorFromTag = true;

    private String relatedSheetName;

    private Integer relatedSheetNo;

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public byte[] getImageValue() {
        return imageValue;
    }

    public void setImageValue(byte[] imageValue) {
        this.imageValue = imageValue;
    }

    public XSSFClientAnchor getImageAnchor() {
        return imageAnchor;
    }

    public void setImageAnchor(XSSFClientAnchor imageAnchor) {
        this.imageAnchor = imageAnchor;
    }

    public boolean isAnchorFromTag() {
        return isAnchorFromTag;
    }

    public void setAnchorFromTag(boolean anchorFromTag) {
        isAnchorFromTag = anchorFromTag;
    }

    public String getRelatedSheetName() {
        return relatedSheetName;
    }

    public void setRelatedSheetName(String relatedSheetName) {
        this.relatedSheetName = relatedSheetName;
    }

    public Integer getRelatedSheetNo() {
        return relatedSheetNo;
    }

    public void setRelatedSheetNo(Integer relatedSheetNo) {
        this.relatedSheetNo = relatedSheetNo;
    }

}
