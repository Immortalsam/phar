package com.phar.model;

import java.util.Date;

/**
 * Created by Sam on 11/7/2016.
 */
public class Product {

    private int productId;
    private String sellerID;
    private String productName;
    private int productQuantity;
    private String productComposition;
    private String productPurchaseDate;
    private String productMfdDate;
    private String productExpDate;
    private float productCostPrice;
    private float productSellPrice;
    private int productBatchNo;
    private int purchaseTax;
    private int billNo;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
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

    public String getProductComposition() {
        return productComposition;
    }

    public void setProductComposition(String productComposition) {
        this.productComposition = productComposition;
    }

    public String getProductPurchaseDate() {
        return productPurchaseDate;
    }

    public void setProductPurchaseDate(String productPurchaseDate) {
        this.productPurchaseDate = productPurchaseDate;
    }

    public String getProductMfdDate() {
        return productMfdDate;
    }

    public void setProductMfdDate(String productMfdDate) {
        this.productMfdDate = productMfdDate;
    }

    public String getProductExpDate() {
        return productExpDate;
    }

    public void setProductExpDate(String productExpDate) {
        this.productExpDate = productExpDate;
    }

    public float getProductCostPrice() {
        return productCostPrice;
    }

    public void setProductCostPrice(float productCostPrice) {
        this.productCostPrice = productCostPrice;
    }

    public float getProductSellPrice() {
        return productSellPrice;
    }

    public void setProductSellPrice(float productSellPrice) {
        this.productSellPrice = productSellPrice;
    }

    public int getProductBatchNo() {
        return productBatchNo;
    }

    public void setProductBatchNo(int productBatchNo) {
        this.productBatchNo = productBatchNo;
    }

    public int getPurchaseTax() {
        return purchaseTax;
    }

    public void setPurchaseTax(int purchaseTax) {
        this.purchaseTax = purchaseTax;
    }

    public int getBillNo() {
        return billNo;
    }

    public void setBillNo(int billNo) {
        this.billNo = billNo;
    }
}
