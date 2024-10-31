package com.alibaba.excel.write.handler.filter;

import java.util.regex.Pattern;

/**
 * Description:
 * prior-pattern
 *
 * @author linfeng
 */
public class PriorPatternFilter extends AbstractPriorMatchFilter {
    @Override
    protected String filterName() {
        return "prior-pattern";
    }

    @Override
    protected boolean strMatch(String source, String match) {
        return Pattern.matches(match, source);
    }
}
