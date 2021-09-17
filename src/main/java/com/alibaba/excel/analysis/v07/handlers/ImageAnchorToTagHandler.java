package com.alibaba.excel.analysis.v07.handlers;

import java.util.List;

import org.apache.poi.openxml4j.opc.PackagePart;
import org.xml.sax.Attributes;

import com.alibaba.excel.metadata.ImageData;
import com.alibaba.excel.read.listener.ImageDataReadListener;

/**
 * Handle image anchor "xdr:to" tag
 *
 * @author Pengliang Zhao
 */
public class ImageAnchorToTagHandler extends AbstractXlsxImageTagHandler {

    @Override
    public void startElement(ImageDataReadListener imageDataListener, PackagePart packagePart, String name,
        Attributes attributes) {
        List<ImageData> imageDataList = imageDataListener.getImageDataList();
        ImageData imageData = imageDataList.get(imageDataList.size() - 1);
        imageData.setAnchorFromTag(false);
    }

}
