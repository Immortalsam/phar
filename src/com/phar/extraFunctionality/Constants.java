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

}
