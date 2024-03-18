package com.alibaba.easyexcel.test.demo.write;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.handler.context.SheetWriteHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;


/**
 * 自定义拦截器，实现对特定列增加数据验证
 *
 * @author Xin Yan
 */
@Slf4j
public class DataValidationSheetWriteHandler implements SheetWriteHandler {

    @Override
    public void afterSheetCreate(SheetWriteHandlerContext context) {
        log.info("第{}个Sheet写入成功。", context.getWriteSheetHolder().getSheetNo());

        // 设置验证生效的范围，四个参数分别为：起始行，终止行，起始列，终止列
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 10, 1, 1);
        DataValidationHelper helper = context.getWriteSheetHolder().getSheet().getDataValidationHelper();
        // 设置验证方式
        DataValidationConstraint constraint = helper.createDateConstraint(DataValidationConstraint.OperatorType.BETWEEN,
            "Date(1900, 1, 1)", "Date(2999, 12, 31)", "yyyy-MM-dd");

        // 创建验证对象
        DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
        // 错误提示信息
        dataValidation.createErrorBox("提示","请输入正确格式日期[yyyy-MM-dd]");
        dataValidation.setShowErrorBox(true);
        context.getWriteSheetHolder().getSheet().addValidationData(dataValidation);
    }
}
