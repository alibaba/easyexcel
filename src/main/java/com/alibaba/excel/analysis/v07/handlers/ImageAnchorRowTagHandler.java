package com.alibaba.excel.analysis.v07.handlers;

import java.util.List;

import com.alibaba.excel.metadata.ImageData;
import com.alibaba.excel.read.listener.ImageDataReadListener;

/**
 * Handle image anchor "xdr:row" tag
 */
public class ImageAnchorRowTagHandler extends AbstractXlsxImageTagHandler {

    @Override
    public void characters(ImageDataReadListener imageDataListener, char[] ch, int start, int length) {
        List<ImageData> imageDataList = imageDataListener.getImageDataList();
        ImageData imageData = imageDataList.get(imageDataList.size() - 1);
        StringBuilder row = new StringBuilder();
        row.append(ch, start, length);

        if (imageData.isAnchorFromTag()) {
            imageData.getImageAnchor().setRow1(Integer.valueOf(row.toString()));
            return;
        }
        imageData.getImageAnchor().setRow2(Integer.valueOf(row.toString()));
    }
}
