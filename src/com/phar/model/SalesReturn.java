package com.phar.model;

/**
 * Created by Sam on 12/2/2016.
 */

public class SalesReturn {

    private String billNo;
    private String comBill;
    private String productName;
    private String productId;
    private String batch;
    private String expDate;
    private float rate;
    private int quantityReceived;
    private float total;

    public String getBillNo() {
        return billNo;
    }

    public String getComBill() {
        return comBill;
    }

    public void setComBill(String comBill) {
        this.comBill = comBill;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public int getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(int quantityReceived) {
        this.quantityReceived = quantityReceived;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
