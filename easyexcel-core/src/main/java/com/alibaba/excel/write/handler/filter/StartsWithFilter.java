package com.alibaba.excel.write.handler.filter;

/**
 * Description:
 * starts-with
 *
 * @author linfeng
 */
public class StartsWithFilter extends AbstractMatchFilter {

    @Override
    protected String filterName() {
        return "starts-with";
    }

    @Override
    protected boolean strMatch(String source, String match) {
        return source.startsWith(match);
    }
}
