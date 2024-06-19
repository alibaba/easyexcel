package com.alibaba.excel.write.handler.filter;

/**
 * Description:
 * prior-ends-with
 * @author linfeng
 */
public class PriorEndsWithFilter extends AbstractPriorMatchFilter {

    @Override
    protected String filterName() {
        return "prior-ends-with";
    }

    @Override
    protected boolean strMatch(String source, String match) {
        return source.endsWith(match);
    }
}
