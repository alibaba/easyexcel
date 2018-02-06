package com.alibaba.excel.metadata;

/**
 * @author jipengfei
 */
public class IndexValue {

    private String v_index;
    private String v_value;

    public IndexValue(String v_index, String v_value) {
        super();
        this.v_index = v_index;
        this.v_value = v_value;
    }

    public String getV_index() {
        return v_index;
    }

    public void setV_index(String v_index) {
        this.v_index = v_index;
    }

    public String getV_value() {
        return v_value;
    }

    public void setV_value(String v_value) {
        this.v_value = v_value;
    }

    @Override
    public String toString() {
        return "IndexValue [v_index=" + v_index + ", v_value=" + v_value + "]";
    }


}
