package com.alibaba.excel.analysis.v07.handlers;

import java.util.List;
import com.alibaba.excel.metadata.ImageData;
import com.alibaba.excel.read.listener.ImageDataReadListener;

/**
 * Handle image anchor "xdr:col" tag
 *
 * @author Pengliang Zhao
 */
public class ImageAnchorColTagHandler extends AbstractXlsxImageTagHandler {

    @Override
    public void characters(ImageDataReadListener imageDataListener, char[] ch, int start, int length) {
        List<ImageData> imageDataList = imageDataListener.getImageDataList();
        ImageData imageData = imageDataList.get(imageDataList.size() - 1);
        StringBuilder col = new StringBuilder();
        col.append(ch, start, length);

        if (imageData.isAnchorFromTag()) {
            imageData.getImageAnchor().setCol1(Integer.valueOf(col.toString()));
            return;
        }
        imageData.getImageAnchor().setCol2(Integer.valueOf(col.toString()));
    }
}
