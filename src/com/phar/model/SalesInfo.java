package com.phar.model;

/**
 * Created by Sam on 11/25/2016.
 */

public class SalesInfo {

    private String salesBill;
    private String salesParty;
    private String customerAddress;
    private String prescribedBy;
    private String productName;
    private int productQuantity;
    private String salesDate;
    private Double salesAmount;

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

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }

    public Double getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Double salesAmount) {
        this.salesAmount = salesAmount;
    }
}
