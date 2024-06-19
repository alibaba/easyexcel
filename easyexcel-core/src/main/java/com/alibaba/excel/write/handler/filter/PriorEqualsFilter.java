package com.alibaba.excel.write.handler.filter;

/**
 * Description:
 * prior-equals
 *
 * @author linfeng
 */
public class PriorEqualsFilter extends AbstractPriorMatchFilter {


    @Override
    protected String filterName() {
        return "prior-equals";
    }

    @Override
    protected boolean strMatch(String source, String match) {
        return source.equalsIgnoreCase(match);
    }
}
