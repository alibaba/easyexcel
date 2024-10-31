package com.alibaba.excel.write.handler.filter;

import java.util.regex.Pattern;

/**
 * Description:
 * pattern
 *
 * @author linfeng
 */
public class PatternFilter extends AbstractMatchFilter {

    @Override
    protected String filterName() {
        return "pattern";
    }

    @Override
    protected boolean strMatch(String source, String match) {
        return Pattern.matches(match, source);
    }
}
