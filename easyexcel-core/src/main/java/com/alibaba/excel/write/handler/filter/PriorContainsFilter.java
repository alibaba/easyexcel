package com.alibaba.excel.write.handler.filter;

/**
 * Description:
 * 优先包含
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/30 10:37
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
