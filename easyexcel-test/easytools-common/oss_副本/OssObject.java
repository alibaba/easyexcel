package com.alibaba.easytools.spring.oss;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.excption.SystemException;

import com.aliyun.oss.model.OSSObject;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * oss返回对象
 *
 * @author 是仪
 **/
@Data
public class OssObject implements Closeable {

    /**
     * 文件描述的文件名
     */
    private static final String NORMAL_FILENAME = "filename=";
    /**
     * "RFC 5987"，文件描述的文件名
     */
    private static final String FILENAME = "filename*=";
    /**
     * utf8的前缀
     */
    public static final String UTF8_PREFIX = "utf-8''";
    /**
     * 默认文价类型
     */
    private static final String DEFAULT_SUFFIX = "data";

    /**
     * 件key
     */
    private String key;

    /**
     * 原文件名 上传文件时候的文件名 如果为空 返回uuid
     */
    private String originalFileName;
    /**
     * 原文件类型的后缀 如果没有指定 默认.data
     */
    private String originalFileSuffix;
    /**
     * 对象实体
     */
    private InputStream objectContent;
    /**
     * 对象实体长度
     */
    private Long objectContentLength;
    /**
     * 对象实体类型
     */
    private String objectContentType;
    /**
     * 对象展示类型
     */
    private String objectContentDisposition;

    public static OssObject buildWithAliyunOssObject(OSSObject aliyunOssObject) {
        OssObject ossObject = new OssObject();
        if (aliyunOssObject == null) {
            return ossObject;
        }
        ossObject.setKey(aliyunOssObject.getKey());
        ossObject.setObjectContent(aliyunOssObject.getObjectContent());
        ossObject.setObjectContentLength(aliyunOssObject.getObjectMetadata().getContentLength());
        ossObject.setObjectContentType(aliyunOssObject.getObjectMetadata().getContentType());
        ossObject.setObjectContentDisposition(aliyunOssObject.getObjectMetadata().getContentDisposition());
        String contentDisposition = aliyunOssObject.getObjectMetadata().getContentDisposition();

        // 文件名
        String originalFileName = getFilename(contentDisposition);
        if (originalFileName != null) {
            ossObject.setOriginalFileName(originalFileName);

            // 从文件名解析文件后缀
            int fileSuffixIndex = StringUtils.lastIndexOf(originalFileName, ".");
            if (fileSuffixIndex != -1) {
                // 文件的后缀
                String serverFileSuffix = originalFileName.substring(fileSuffixIndex + 1);
                ossObject.setOriginalFileSuffix(serverFileSuffix);
            }
        }
        return ossObject;
    }

    /**
     * 从contentDisposition获取文件名
     *
     * @param contentDisposition
     * @return
     */
    private static String getFilename(String contentDisposition) {
        int originalFileNameIndex = StringUtils.indexOf(contentDisposition, FILENAME);
        if (originalFileNameIndex != -1) {
            // 获取的文件名全称
            String originalFileName = contentDisposition.substring(originalFileNameIndex + FILENAME.length());
            if (originalFileName.startsWith(OssObject.UTF8_PREFIX)) {
                originalFileName = originalFileName.substring(OssObject.UTF8_PREFIX.length());
                try {
                    originalFileName = URLDecoder.decode(originalFileName, StandardCharsets.UTF_8.name());
                } catch (UnsupportedEncodingException e) {
                    throw new SystemException(CommonErrorEnum.COMMON_SYSTEM_ERROR, "不支持的字符编码", e);
                }
            }
            return originalFileName;
        }

        originalFileNameIndex = StringUtils.indexOf(contentDisposition, NORMAL_FILENAME);
        if (originalFileNameIndex != -1) {
            String originalFileName = contentDisposition.substring(originalFileNameIndex + NORMAL_FILENAME.length());
            try {
                originalFileName = URLDecoder.decode(originalFileName, StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                throw new SystemException(CommonErrorEnum.COMMON_SYSTEM_ERROR, "不支持的字符编码", e);
            }
            return originalFileName;
        }

        return null;
    }

    public String getOriginalFileName(String defaultFileName) {
        if (StringUtils.isEmpty(originalFileName)) {
            return defaultFileName;
        }
        return originalFileName;
    }

    public String getOriginalFileName() {
        return getOriginalFileName(getKey());
    }

    public String getOriginalFileSuffix() {
        return originalFileSuffix;
    }

    @Override
    public void close() throws IOException {
        if (objectContent != null) {
            objectContent.close();
        }
    }
}
