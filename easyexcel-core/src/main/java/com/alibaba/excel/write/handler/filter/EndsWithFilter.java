package com.alibaba.excel.write.handler.filter;

/**
 * Description:
 * ends-with filter
 *
 * @author linfeng
 */
public class EndsWithFilter extends AbstractMatchFilter {

    @Override
    protected String filterName() {
        return "ends-with";
    }

    @Override
    protected boolean strMatch(String source, String match) {
        return source.endsWith(match);
    }
}
