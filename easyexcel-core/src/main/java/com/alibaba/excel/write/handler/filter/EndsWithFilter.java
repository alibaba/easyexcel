package com.alibaba.excel.write.handler.filter;

/**
 * Description:
 * ends-with
 *
 * @author linfeng
 * @version 1.0.0
 * @since 2023/5/30 10:37
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
