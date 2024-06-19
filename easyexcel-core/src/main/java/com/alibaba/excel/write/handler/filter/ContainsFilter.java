package com.alibaba.excel.write.handler.filter;

/**
 * Description:
 * Contains Filter
 *
 * @author linfeng
 */
public class ContainsFilter extends AbstractMatchFilter {

    @Override
    protected String filterName() {
        return "contains";
    }

    @Override
    protected boolean strMatch(String source, String match) {
        return source.contains(match);
    }
}
