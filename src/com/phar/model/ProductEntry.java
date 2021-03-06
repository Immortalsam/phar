package com.phar.model;

/**
 * Created by Sam on 11/15/2016.
 */

public class ProductEntry {

    private String fisalYear;
    private String productId;
    private String supplierId;
    private String productName;
    private String productBatch;
    private String productExpDate;
    private float productCcCharge;
    private int productQuFoR;
    private float productRate;
    private int productQuantity;
    private float productAmount;
    private float productMrp;
    private String todayDate;
    private String billNo;
    private String productVat;
    private String productCashCredit;

    private String expYear;
    private String expMonth;
    private String expCombined;

    public String getExpCombined() {
        return expCombined;
    }

    public void setExpCombined(String expCombined) {
        this.expCombined = expCombined;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public float getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(float productAmount) {
        this.productAmount = productAmount;
    }

    public String getProductId() {
        return productId;

    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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

    public String getProductExpDate() {
        return productExpDate;
    }

    public void setProductExpDate(String productExpDate) {
        this.productExpDate = productExpDate;
    }

    public float getProductCcCharge() {
        return productCcCharge;
    }

    public void setProductCcCharge(float productCcCharge) {
        this.productCcCharge = productCcCharge;
    }

    public int getProductQuFoR() {
        return productQuFoR;
    }

    public void setProductQuFoR(int productQuFoR) {
        this.productQuFoR = productQuFoR;
    }

    public float getProductRate() {
        return productRate;
    }

    public void setProductRate(float productRate) {
        this.productRate = productRate;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public float getProductMrp() {
        return productMrp;
    }

    public void setProductMrp(float productMrp) {
        this.productMrp = productMrp;
    }

    public String getProductVat() {
        return productVat;
    }

    public void setProductVat(String productVat) {
        this.productVat = productVat;
    }

    public String getProductCashCredit() {
        return productCashCredit;
    }

    public void setProductCashCredit(String productCashCredit) {
        this.productCashCredit = productCashCredit;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getFisalYear() {
        return fisalYear;
    }

    public void setFisalYear(String fisalYear) {
        this.fisalYear = fisalYear;
    }
}
