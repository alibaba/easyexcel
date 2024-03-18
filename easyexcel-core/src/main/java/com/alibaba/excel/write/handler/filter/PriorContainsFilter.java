package com.alibaba.excel.write.handler.filter;

/**
 * Description:
 * prior-contains
 *
 * @author linfeng
 */
public class PriorContainsFilter extends AbstractPriorMatchFilter {


    @Override
    protected String filterName() {
        return "prior-contains";
    }

    @Override
    protected boolean strMatch(String source, String match) {
        return source.contains(match);
    }
}
