package com.alibaba.excel.write.metadata.fill;

import com.alibaba.excel.enums.WriteDirectionEnum;

/**
 * Fill config
 *
 * @author Jiaju Zhuang
 **/
public class FillConfig {
    private WriteDirectionEnum direction;
    /**
     * Create a new row each time you use the list parameter.The default create if necessary.
     * <p>
     * Warnning:If you use <code>forceNewRow</code> set true, will not be able to use asynchronous write file, simply
     * say the whole file will be stored in memory.
     */
    private Boolean forceNewRow;
    private boolean hasInit;

    public WriteDirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(WriteDirectionEnum direction) {
        this.direction = direction;
    }

    public Boolean getForceNewRow() {
        return forceNewRow;
    }

    public void setForceNewRow(Boolean forceNewRow) {
        this.forceNewRow = forceNewRow;
    }

    public void init() {
        if (hasInit) {
            return;
        }
        if (direction == null) {
            direction = WriteDirectionEnum.VERTICAL;
        }
        if (forceNewRow == null) {
            forceNewRow = Boolean.FALSE;
        }
        hasInit = true;
    }

    public static FillConfigBuilder builder() {
        return new FillConfigBuilder();
    }

    public static class FillConfigBuilder {
        private FillConfig fillConfig;

        FillConfigBuilder() {
            this.fillConfig = new FillConfig();
        }

        public FillConfigBuilder direction(WriteDirectionEnum direction) {
            fillConfig.setDirection(direction);
            return this;
        }

        public FillConfigBuilder forceNewRow(Boolean forceNewRow) {
            fillConfig.setForceNewRow(forceNewRow);
            return this;
        }

        public FillConfig build() {
            return build(true);
        }

        public FillConfig build(boolean autoInit) {
            if (autoInit) {
                fillConfig.init();
            }
            return fillConfig;
        }

    }
}
