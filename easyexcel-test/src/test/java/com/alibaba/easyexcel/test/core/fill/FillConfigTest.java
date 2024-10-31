package com.alibaba.easyexcel.test.core.fill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.fill.FillConfig;;

public class FillConfigTest {

    @Test
    void creationByNewTest() {
      // Non-arguments constructor
      FillConfig config = new FillConfig();
      assertEquals(config.getDirection(), WriteDirectionEnum.VERTICAL);
      assertFalse(config.getForceNewRow());
      assertTrue(config.getAutoStyle());

      // Full-arguments constructor
      config = new FillConfig(WriteDirectionEnum.HORIZONTAL, Boolean.TRUE, Boolean.FALSE);
      assertEquals(config.getDirection(), WriteDirectionEnum.HORIZONTAL);
      assertTrue(config.getForceNewRow());
      assertFalse(config.getAutoStyle());
    }

    @Test
    void creationByBuilderTest() {
      // default builder
      FillConfig config = FillConfig.builder().build();
      assertEquals(config.getDirection(), WriteDirectionEnum.VERTICAL);
      assertFalse(config.getForceNewRow());
      assertTrue(config.getAutoStyle());

      // builder with some actions
      config = FillConfig.builder()
        .direction(WriteDirectionEnum.HORIZONTAL)
        .autoStyle(Boolean.FALSE)
        .forceNewRow(Boolean.TRUE)
        .build();
      assertEquals(config.getDirection(), WriteDirectionEnum.HORIZONTAL);
      assertTrue(config.getForceNewRow());
      assertFalse(config.getAutoStyle());
    }
}
