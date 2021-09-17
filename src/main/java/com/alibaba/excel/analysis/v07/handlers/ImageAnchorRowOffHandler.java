package com.alibaba.excel.analysis.v07.handlers;

import java.util.List;

import com.alibaba.excel.metadata.ImageData;
import com.alibaba.excel.read.listener.ImageDataReadListener;

/**
 * Handle image anchor "xdr:rowOff" tag
 *
 * @author Pengliang Zhao
 */
public class ImageAnchorRowOffHandler extends AbstractXlsxImageTagHandler {

    @Override
    public void characters(ImageDataReadListener imageDataListener, char[] ch, int start, int length) {
        List<ImageData> imageDataList = imageDataListener.getImageDataList();
        ImageData imageData = imageDataList.get(imageDataList.size() - 1);
        StringBuilder rowOff = new StringBuilder();
        rowOff.append(ch, start, length);
        if (imageData.isAnchorFromTag()) {
            imageData.getImageAnchor().setDy1(Integer.valueOf(rowOff.toString()));
            return;
        }
        imageData.getImageAnchor().setDy2(Integer.valueOf(rowOff.toString()));
    }
}
