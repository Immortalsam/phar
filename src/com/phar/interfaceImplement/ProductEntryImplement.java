package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.ProductEntryInterface;
import com.phar.model.ProductEntry;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Sam on 11/15/2016.
 */
public class ProductEntryImplement implements ProductEntryInterface {

    static Connection conn;
    private ProductEntry product;

    public ProductEntryImplement() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addProduct(ProductEntry product) {
        this.product = product;
        String addQuery = "INSERT into new_purchase_entry (fiscal_year, product_id , supplier_id, product_name, product_batch, " +
                "product_expdate, product_cccharge, product_qufor, product_rate, product_quantity, product_mrp, product_todaydate, cash_or_credit, product_vat, product_billno) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(addQuery);
            preparedStatement.setString(1, product.getFisalYear());
            preparedStatement.setString(2, product.getProductId());
            preparedStatement.setString(3, product.getSupplierId());
            preparedStatement.setString(4, product.getProductName());
            preparedStatement.setString(5, product.getProductBatch());
            preparedStatement.setString(6, product.getProductExpDate());
            preparedStatement.setFloat(7, product.getProductCcCharge());
            preparedStatement.setInt(8, product.getProductQuFoR());
            preparedStatement.setFloat(9, product.getProductRate());
            preparedStatement.setInt(10, product.getProductQuantity());
            preparedStatement.setFloat(11, product.getProductMrp());
            preparedStatement.setString(12, product.getTodayDate());
            preparedStatement.setString(13, product.getProductCashCredit());
            preparedStatement.setString(14, product.getProductVat());
            preparedStatement.setString(15, product.getBillNo());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ObservableList<ProductEntry> listProduct() {
        return null;
    }
}
