package com.alibaba.excel.analysis.v07.handlers;

import java.util.List;
import com.alibaba.excel.metadata.ImageData;
import com.alibaba.excel.read.listener.ImageDataReadListener;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.xml.sax.Attributes;

/**
 * Handle image anchor "xdr:from" tag
 *
 * @author Pengliang Zhao
 */
public class ImageAnchorFromTagHandler extends AbstractXlsxImageTagHandler {

    @Override
    public void startElement(ImageDataReadListener imageDataListener, PackagePart packagePart, String name,
        Attributes attributes) {
        ImageData imageData = new ImageData();
        imageData.setRelatedSheetName(imageDataListener.getRelatedSheetName());
        imageData.setRelatedSheetNo(imageDataListener.getRelatedSheetNo());
        imageData.setAnchorFromTag(true);
        List<ImageData> imageDataList = imageDataListener.getImageDataList();
        imageDataList.add(imageData);
    }

}
