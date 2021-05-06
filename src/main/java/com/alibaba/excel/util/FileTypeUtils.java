package com.alibaba.excel.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.excel.metadata.data.ImageData.ImageType;

/**
 * file type utils
 *
 * @author Jiaju Zhuang
 */
public class FileTypeUtils {

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
        'f'};
    private static final int IMAGE_TYPE_MARK_LENGTH = 28;

    private static final Map<String, ImageType> FILE_TYPE_MAP;

    /**
     * Default image type
     */
    public static ImageType defaultImageType = ImageType.PICTURE_TYPE_PNG;

    static {
        FILE_TYPE_MAP = new HashMap<>();
        FILE_TYPE_MAP.put("ffd8ff", ImageType.PICTURE_TYPE_JPEG);
        FILE_TYPE_MAP.put("89504e47", ImageType.PICTURE_TYPE_PNG);
    }

    public static int getImageTypeFormat(byte[] image) {
        ImageType imageType = getImageType(image);
        if (imageType != null) {
            return imageType.getValue();
        }
        return defaultImageType.getValue();
    }

    public static ImageType getImageType(byte[] image) {
        if (image == null || image.length <= IMAGE_TYPE_MARK_LENGTH) {
            return null;
        }
        byte[] typeMarkByte = new byte[IMAGE_TYPE_MARK_LENGTH];
        System.arraycopy(image, 0, typeMarkByte, 0, IMAGE_TYPE_MARK_LENGTH);
        return FILE_TYPE_MAP.get(encodeHexStr(typeMarkByte));
    }

    private static String encodeHexStr(byte[] data) {
        final int len = data.length;
        final char[] out = new char[len << 1];
        // two characters from the hex value.
        for (int i = 0, j = 0; i < len; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return new String(out);
    }
}
