package com.alibaba.excel.write.metadata.fill;

import lombok.*;

/**
 * @author youlingdada
 * @version 1.0
 * createDate 2023/12/16 16:06
 */
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FillIndex {
    /**
     * last row index
     */
    private Integer lastRowIndex;
    /**
     * last column index
     */
    private Integer lastColumnIndex;

    /**
     * Whether the original cell is used
     */
    private Boolean isOriginalCell;
}
