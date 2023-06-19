package com.alibaba.excel.write.handler.filter;

/**
 * Description:
 * equals
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/30 10:37
 */
public class EqualsFilter extends AbstractMatchFilter {

    @Override
    protected String filterName() {
        return "equals";
    }

    @Override
    protected boolean strMatch(String source, String match) {
        return source.equalsIgnoreCase(match);
    }
}
