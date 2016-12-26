package com.phar.model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

/**
 * Created by Sam on 12/2/2016.
 */

public class SalesReturn extends RecursiveTreeObject<SalesReturn> {

    private String billNo;
    private String salesReturnDate;
    private String productName;
    private String productBatch;
    private int quantityReceived;
    private Double total;
    private Double rate;

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getSalesReturnDate() {
        return salesReturnDate;
    }

    public void setSalesReturnDate(String salesReturnDate) {
        this.salesReturnDate = salesReturnDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public int getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(int quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
