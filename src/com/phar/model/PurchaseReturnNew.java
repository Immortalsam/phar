package com.phar.model;

/**
 * Created by Sam on 12/26/2016.
 */
public class PurchaseReturnNew {

    private Integer quantityReturned;
    private String supplierId;
    private Double rate;
    private Double ccCharge;
    private Double total;
    private String returnDate;
    private String productName;
    private String productBatch;

    public Integer getQuantityReturned() {
        return quantityReturned;
    }

    public void setQuantityReturned(Integer quantityReturned) {
        this.quantityReturned = quantityReturned;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getCcCharge() {
        return ccCharge;
    }

    public void setCcCharge(Double ccCharge) {
        this.ccCharge = ccCharge;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
