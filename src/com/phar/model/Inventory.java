package com.phar.model;

/**
 * Created by somecuitears on 11/18/16.
 */
public class Inventory {

    private String productName;
    private String productID;
    private Double quantity;
    private String batch;
    private Double mRP;

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    private String expireDate;

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Double getmRP() {
        return mRP;
    }

    public void setmRP(Double mRP) {
        this.mRP = mRP;
    }
}
