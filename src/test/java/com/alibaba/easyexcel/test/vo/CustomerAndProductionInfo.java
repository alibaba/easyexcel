package com.alibaba.easyexcel.test.vo;


import com.alibaba.excel.annotation.ExcelList;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.enums.DynamicDirectionEnum;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

/**
 * 测试实体类
 * @author dff on 2020-08-04
 */
public class CustomerAndProductionInfo {

    @ExcelProperty({"客户姓名", "第一列"})
    private String customerName;
    @ExcelProperty({"客户姓名", "第二列"})
    private String projectPartition;
    @ExcelProperty({"客户姓名", "第三列"})
    private String houseRoomNo;

    @ExcelProperty({"合并列1", "项目地址"})
    private String projectAddress;

    @ExcelProperty({"合并列1", "组合名称"})
    private String composeName;

    @ExcelProperty({"合并列1", "结构"})
    private String composeStruct;

    @ExcelProperty({"合并列1", "物料编号"})
    private Long skuId;

    @ExcelProperty({"合并列1", "物料名称"})
    private String skuName;
    @ExcelProperty({"合并列1", "商品图片"})
    private URL imgUrl;

    @ExcelProperty({"合并列1", "厂家型号"})
    private String manufacturerModel;

    @ExcelProperty({"合并列1", "尺寸"})
    private String size;

    @ExcelProperty({"合并列2", "规格属性"})
    private String specifications;

    @ExcelProperty({"合并列2", "用量"})
    private BigDecimal quantities;

    @ExcelProperty({"合并列2", "采购单价"})
    private String purchasePrice;

    @ExcelProperty({"合并列3", "采购数量"})
    private String num;

    @ExcelProperty({"合并列4", "采购总价"})
    private String purchaseTotalPrice;

    @ExcelProperty({"合并列4", "采购单位"})
    private String purchaseUnitTypeName;

    @ExcelProperty({"合并列4", "包装规格"})
    private String packingSpecification;

    @ExcelProperty({"合并列4", "空间"})
    private String roomName;

    @ExcelProperty({"合并列4", "状态"})
    private String statusName;

    @ExcelProperty({"合并列4", "备注"})
    private String remark;

    @ExcelProperty({"合并列4", "现场尺寸"})
    private String onSiteSize;

    @ExcelProperty({"合并列5", "内构图"})
    private URL structPicture;

    @ExcelProperty({"合并列5", "复尺图"})
    private URL reSizePicture;

    @ExcelProperty({"合并列5", "临时1"})
    @ExcelList(direction = DynamicDirectionEnum.ORIENTATION)
    private java.util.List tmpList;

    @ExcelProperty({"合并列5", "临时2"})
    @ExcelList
    private List secList;

    public List getSecList() {
        return secList;
    }

    public void setSecList(List secList) {
        this.secList = secList;
    }

    public java.util.List getTmpList() {
        return tmpList;
    }

    public void setTmpList(java.util.List tmpList) {
        this.tmpList = tmpList;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProjectPartition() {
        return projectPartition;
    }

    public void setProjectPartition(String projectPartition) {
        this.projectPartition = projectPartition;
    }

    public String getHouseRoomNo() {
        return houseRoomNo;
    }

    public void setHouseRoomNo(String houseRoomNo) {
        this.houseRoomNo = houseRoomNo;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getComposeName() {
        return composeName;
    }

    public void setComposeName(String composeName) {
        this.composeName = composeName;
    }

    public String getComposeStruct() {
        return composeStruct;
    }

    public void setComposeStruct(String composeStruct) {
        this.composeStruct = composeStruct;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public URL getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(URL imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getManufacturerModel() {
        return manufacturerModel;
    }

    public void setManufacturerModel(String manufacturerModel) {
        this.manufacturerModel = manufacturerModel;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public BigDecimal getQuantities() {
        return quantities;
    }

    public void setQuantities(BigDecimal quantities) {
        this.quantities = quantities;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPurchaseTotalPrice() {
        return purchaseTotalPrice;
    }

    public void setPurchaseTotalPrice(String purchaseTotalPrice) {
        this.purchaseTotalPrice = purchaseTotalPrice;
    }

    public String getPurchaseUnitTypeName() {
        return purchaseUnitTypeName;
    }

    public void setPurchaseUnitTypeName(String purchaseUnitTypeName) {
        this.purchaseUnitTypeName = purchaseUnitTypeName;
    }

    public String getPackingSpecification() {
        return packingSpecification;
    }

    public void setPackingSpecification(String packingSpecification) {
        this.packingSpecification = packingSpecification;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOnSiteSize() {
        return onSiteSize;
    }

    public void setOnSiteSize(String onSiteSize) {
        this.onSiteSize = onSiteSize;
    }

    public URL getStructPicture() {
        return structPicture;
    }

    public void setStructPicture(URL structPicture) {
        this.structPicture = structPicture;
    }

    public URL getReSizePicture() {
        return reSizePicture;
    }

    public void setReSizePicture(URL reSizePicture) {
        this.reSizePicture = reSizePicture;
    }
}
