package com.phar.model;

/**
 * Created by Sam on 11/29/2016.
 */
public class CustomerBill {
    private String customerId;
    private String salesDate;
    private String customerBillNo;
    private Float totalAmount;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
    }

    public String getCustomerBillNo() {
        return customerBillNo;
    }

    public void setCustomerBillNo(String customerBillNo) {
        this.customerBillNo = customerBillNo;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }
}
