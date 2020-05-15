package com.alibaba.excel.read.listener;

import java.util.List;

import com.alibaba.excel.event.Listener;
import com.alibaba.excel.metadata.ImageData;

/**
 * A listener, keeps the image data you want to read. When a complete piece of image data was read, the processData()
 * method will be invoked. So if you want to save image data regularly, you can crate a new class which implements this
 * class and overwrite the processData() method.
 */
public class ImageDataReadListener implements Listener {
    private List<ImageData> imageDataList;
    private String relatedSheetName;
    private Integer relatedSheetNo;

    public void processData() {}

    public ImageDataReadListener(List<ImageData> imageDataList) {
        if (imageDataList == null) {
            throw new NullPointerException();
        }

        this.imageDataList = imageDataList;
    }

    public List<ImageData> getImageDataList() {
        return imageDataList;
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
