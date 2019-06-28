package com.alibaba.excel.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jipengfei
 */
public class EachRowAnalysisFinishEvent implements AnalysisFinishEvent {
    private Object result;
    public EachRowAnalysisFinishEvent(Object content) {
        this.result = content;
    }

    public EachRowAnalysisFinishEvent(String[] content, int length) {
        if (content != null) {
            List<String> ls = new ArrayList<String>(length);
            for (int i = 0; i <= length; i++) {
                ls.add(content[i]);
            }
            result = ls;
        }
    }

    @Override
    public Object getAnalysisResult() {
        return result;
    }
}
