package com.phar.model;

/**
 * Created by somecuitears on 11/18/16.
 */
public class Sales {
    private Integer storeID;
    private String productID;
    private String productName;
    private Double productQuntity;
    private String productBatch;
    private Double mRP;
    private String expireDate;
    private String rackNumber;

    public Integer getStoreID() {
        return storeID;
    }

    public void setStoreID(Integer storeID) {
        this.storeID = storeID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductQuntity() {
        return productQuntity;
    }

    public void setProductQuntity(Double productQuntity) {
        this.productQuntity = productQuntity;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public Double getmRP() {
        return mRP;
    }

    public void setmRP(Double mRP) {
        this.mRP = mRP;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getRackNumber() {
        return rackNumber;
    }

    public void setRackNumber(String rackNumber) {
        this.rackNumber = rackNumber;
    }
}
