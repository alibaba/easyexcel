package com.alibaba.excel.analysis.v07.handlers;

import java.util.List;

import com.alibaba.excel.metadata.ImageData;
import com.alibaba.excel.read.listener.ImageDataReadListener;

/**
 * Handle image anchor "xdr:colOff" tag
 */
public class ImageAnchorColOffTagHandler extends AbstractXlsxImageTagHandler {

    @Override
    public void characters(ImageDataReadListener imageDataListener, char[] ch, int start, int length) {
        List<ImageData> imageDataList = imageDataListener.getImageDataList();
        ImageData imageData = imageDataList.get(imageDataList.size() - 1);
        StringBuilder colOff = new StringBuilder();
        colOff.append(ch, start, length);

        if (imageData.isAnchorFromTag()) {
            imageData.getImageAnchor().setDx1(Integer.valueOf(colOff.toString()));
            return;
        }
        imageData.getImageAnchor().setDx2(Integer.valueOf(colOff.toString()));
    }
}
