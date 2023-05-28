package com.alibaba.excel.write.handler.filter;

import com.alibaba.excel.exception.ExcelRuntimeException;
import com.alibaba.excel.write.handler.PipeFilter;

import java.util.List;

/**
 * Description:
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/28 15:52
 */
public class ImagePriorityFilter extends PipeFilter<Object, Object> {

    @Override
    public Object apply(Object value) {

        if (value instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> images = (List<String>) value;
            for (String imageName : params()) {
                for (String image : images) {
                    if (image.endsWith(imageName)) {
                        return image;
                    }
                }
            }
            return null;
        } else {
            throw new ExcelRuntimeException("image priority filter input object is not collection");
        }
    }
}
