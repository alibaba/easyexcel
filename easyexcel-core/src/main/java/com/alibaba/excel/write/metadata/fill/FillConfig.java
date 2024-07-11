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

    @Builder.Default
    private WriteDirectionEnum direction = WriteDirectionEnum.VERTICAL;
    
    /**
     * Create a new row each time you use the list parameter.The default create if necessary.
     * <p>
     * Warnning:If you use <code>forceNewRow</code> set true, will not be able to use asynchronous write file, simply
     * say the whole file will be stored in memory.
     */
    @Builder.Default
    private Boolean forceNewRow = Boolean.FALSE;

    /**
     * Automatically inherit style
     *
     * default true.
     */
    @Builder.Default
    private Boolean autoStyle = Boolean.TRUE;

}
