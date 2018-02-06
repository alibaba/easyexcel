package com.alibaba.excel.event;

/**
 * @author jipengfei
 */
public class OneRowAnalysisFinishEvent {

    public OneRowAnalysisFinishEvent(Object data) {
        this.data = data;
    }

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
