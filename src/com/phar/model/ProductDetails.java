package com.phar.model;

/**
 * Created by Sam on 11/16/2016.
 */
public class ProductDetails {

    private String productId;
    private String productName;
    private String CompositionName;
    private String CompanyName;
    private String medicineGroup;
    private String medicineCategory;
    private String productDescription;
    private int noOfPackPerUnit;
    private boolean vat;

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompositionName() {
        return CompositionName;
    }

    public void setCompositionName(String compositionName) {
        CompositionName = compositionName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getMedicineGroup() {
        return medicineGroup;
    }

    public void setMedicineGroup(String medicineGroup) {
        this.medicineGroup = medicineGroup;
    }

    public String getMedicineCategory() {
        return medicineCategory;
    }

    public void setMedicineCategory(String medicineCategory) {
        this.medicineCategory = medicineCategory;
    }

    public int getNoOfPackPerUnit() {
        return noOfPackPerUnit;
    }

    public void setNoOfPackPerUnit(int noOfPackPerUnit) {
        this.noOfPackPerUnit = noOfPackPerUnit;
    }

    public boolean isVat() {
        return vat;
    }

    public void setVat(boolean vat) {
        this.vat = vat;
    }
}
