package com.alibaba.excel.write.handler.filter;

/**
 * Description:
 * equals Filter
 *
 * @author linfeng
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
