package com.alibaba.excel.write.handler.filter;

/**
 * Description:
 * starts-with
 *
 * @author linfeng
 */
public class PriorStartsWithFilter extends AbstractPriorMatchFilter {

    @Override
    protected String filterName() {
        return "starts-with";
    }

    @Override
    protected boolean strMatch(String source, String match) {
        return source.startsWith(match);
    }
}
