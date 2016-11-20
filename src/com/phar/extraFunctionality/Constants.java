package com.phar.extraFunctionality;

/**
 * Created by somecuitears on 11/15/16.
 */
public class Constants {
    static final public String[] productCategoryList = {"General", "Essential", "Narcotic", "Cosmetic"};
    static final public String[] yesNo = {"Yes", "No"};

    static final public String supplierIDGenQuery = "SELECT value FROM idGen WHERE ofID = 'supplierIdGen'";
    static final public String supplierIDUpdateQuery = "UPDATE idGen SET value ='";
    static final public String getSupplierIDUpdateQueryBack = "' WHERE ofID = 'supplierIdGen'";

    static final public String productIDGenQuery = "SELECT value FROM idGen WHERE ofID = 'productIdGen'";
    static final public String productIDUpdateQuery = "UPDATE idGen SET value ='";
    static final public String getProductIDUpdateQueryBack = "' WHERE ofID = 'productIdGen'";

    static final public String insertToNewProductEntry = "INSERT INTO new_product_entry VALUES (?,?,?,?,?,?,?,?)";
    static final public String insertToProductFromSupplier = "INSERT INTO product_from_supplier VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

    static final public String selectCompanyNameFromNewProductEntry = "SELECT company_name FROM new_product_entry";
    static final public String selectSupplierFromSupplier = "SELECT supplier_name FROM supplier";
    static final public String selectSupplierIDFromSupplierWhereSupplierName = "SELECT supplier_id FROM supplier WHERE supplier_name= '";

}
