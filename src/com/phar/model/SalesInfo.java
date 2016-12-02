package com.phar.model;

/**
 * Created by Sam on 11/25/2016.
 */

public class SalesInfo {


    private String customerId;
    private String salesBill;
    private String salesParty;
    private String customerAddress;
    private String prescribedBy;
    private String productName;
    private int productQuantity;
    private String salesDate;
    private String batch;
    private float salesAmount;

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getSalesBill() {
        return salesBill;
    }

    public void setSalesBill(String salesBill) {
        this.salesBill = salesBill;
    }

    public String getSalesParty() {
        return salesParty;
    }

    public void setSalesParty(String salesParty) {
        this.salesParty = salesParty;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getPrescribedBy() {
        return prescribedBy;
    }

    public void setPrescribedBy(String prescribedBy) {
        this.prescribedBy = prescribedBy;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }

    public float getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(float salesAmount) {
        this.salesAmount = salesAmount;
    }
}
