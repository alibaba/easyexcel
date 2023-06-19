package com.alibaba.excel.write.handler.filter;

/**
 * Description:
 * starts-with
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/30 10:37
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
