package com.alibaba.excel.write.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用来显示树形结构
 * childDataList 类型支持 WriteTreeData进行嵌套
 * @author Wish
 * @version 1.0 2022/5/14
 */
@Getter
@Setter
@EqualsAndHashCode
public class WriteTreeData<T, E> {
    T parentData;
    List<E> childDataList;

    public WriteTreeData(T parentData, List<E> childDataList) {
        this.parentData = parentData;
        this.childDataList = childDataList;
    }
}
