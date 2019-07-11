package com.alibaba.excel.converters;

import java.util.Collection;
import java.util.Map;

public interface ConverterRegistryCenter {
    void register(Converter converter);
    Map<ConverterKey, Converter> getConverters();
}
