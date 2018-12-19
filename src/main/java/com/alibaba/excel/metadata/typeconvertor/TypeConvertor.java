package com.alibaba.excel.metadata.typeconvertor;

/**
 * \* Author: xueyunlong@didiglobal.com
 * \* Date: 2018-12-19
 * \* Time: 16:23
 * \* Description:S = source, T = target
 * \* you can implement this interface to expand
 * \
 */
public interface TypeConvertor<T> {

   T serialize(String s);

   String deserialize(T s);

}
