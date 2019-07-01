package com.alibaba.excel.converters;

import java.util.Collection;

public interface ConverterRegistryCenter {
    void register(Converter converter);
    Collection<Converter> getConverters();
}
