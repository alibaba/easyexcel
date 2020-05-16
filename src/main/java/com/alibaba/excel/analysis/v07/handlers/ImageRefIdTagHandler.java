package com.alibaba.excel.analysis.v07.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.xml.sax.Attributes;

import com.alibaba.excel.constant.ExcelXmlConstants;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.metadata.ImageData;
import com.alibaba.excel.read.listener.ImageDataReadListener;

/**
 * Handle image reference ID "a:blip" tag
 *
 * @author Pengliang Zhao
 */
public class ImageRefIdTagHandler extends AbstractXlsxImageTagHandler {
    @Override
    public void startElement(ImageDataReadListener imageDataListener, PackagePart packagePart, String name,
        Attributes attributes) {
        PackageRelationship imagePackageRelationship = packagePart.getRelationship(attributes.getValue(ExcelXmlConstants.IMAGE_EMBED_TAG));
        try {
            PackagePart imagePackage = packagePart.getRelatedPart(imagePackageRelationship);
            InputStream in = imagePackage.getInputStream();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            List<ImageData> imageDataList = imageDataListener.getImageDataList();
            ImageData imageData = imageDataList.get(imageDataList.size() - 1);
            String imageType = imagePackage.getContentType();
            imageData.setFileSuffix(imageType.substring(imageType.indexOf("/") + 1));
            imageData.setImageValue(outStream.toByteArray());
            in.close();
            imageDataListener.processData();
        } catch (InvalidFormatException e) {
            throw new ExcelAnalysisException(e);
        } catch (IOException e) {
            throw new ExcelAnalysisException(e);
        }
    }

}
