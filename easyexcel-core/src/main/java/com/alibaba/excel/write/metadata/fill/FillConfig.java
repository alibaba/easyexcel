package com.alibaba.excel.write.metadata.fill;

import com.alibaba.excel.enums.WriteDirectionEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Fill config
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FillConfig {
    private WriteDirectionEnum direction;
    /**
     * Create a new row each time you use the list parameter.The default create if necessary.
     * <p>
     * Warnning:If you use <code>forceNewRow</code> set true, will not be able to use asynchronous write file, simply
     * say the whole file will be stored in memory.
     */
    private Boolean forceNewRow;

    /**
     * Automatically inherit style
     *
     * default true.
     */
    private Boolean autoStyle;

    private boolean hasInit;

    public void init() {
        if (hasInit) {
            return;
        }
        if (direction == null) {
            direction = WriteDirectionEnum.VERTICAL;
        }
        if (forceNewRow == null) {
            forceNewRow = Boolean.FALSE;
        }
        if (autoStyle == null) {
            autoStyle = Boolean.TRUE;
        }
        hasInit = true;
    }
}
