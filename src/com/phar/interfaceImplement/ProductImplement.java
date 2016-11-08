package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.ProductInterface;
import com.phar.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Sam on 11/7/2016.
 */
public class ProductImplement implements ProductInterface {


    static Connection conn;
    private Product product;

    public ProductImplement() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean addProduct(Product productt) {
        this.product = productt;
        String addQuery = "INSERT into product_from_supplier (supplier_id, product_id , product_name, product_quantity, " +
                "product_composition, product_purchaseDate, product_mfd, product_expd, product_cost, product_sell, bill_no," +
                "product_batch, tax) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        System.out.println("Values Recieved");
        System.out.println(product.getSellerID() + " : " + product.getProductId() + " : " + product.getBillNo() + " : " + product.getProductName()
                + " : " + product.getProductQuantity() + " : " + product.getProductComposition() + " : " + product.getProductBatchNo() + " : " + product.getProductMfdDate()
                + " : " + product.getProductExpDate() + " : " + product.getProductCostPrice() + " : " + product.getProductSellPrice()
                + " : " + product.getProductPurchaseDate() + " : " + product.getPurchaseTax());
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(addQuery);
            preparedStatement.setString(1, product.getSellerID().toString());
            preparedStatement.setInt(2, product.getProductId());
            preparedStatement.setString(3, product.getProductName());
            preparedStatement.setInt(4, product.getProductQuantity());
            preparedStatement.setString(5, product.getProductComposition());
            preparedStatement.setString(6, product.getProductPurchaseDate());
            preparedStatement.setString(7, product.getProductMfdDate());
            preparedStatement.setString(8, product.getProductExpDate());
            preparedStatement.setFloat(9, product.getProductCostPrice());
            preparedStatement.setFloat(10, product.getProductSellPrice());
            preparedStatement.setInt(11, product.getBillNo());
            preparedStatement.setInt(12, product.getProductBatchNo());
            preparedStatement.setInt(13, product.getPurchaseTax());

            preparedStatement.executeUpdate();
            return true;

            //Sql error
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateProduct() {
        return false;

    }

    @Override
    public boolean deleteProduct() {
        return false;
    }

    @Override
    public boolean alertWhenAboutToFinish() {
        return false;
    }
}
