package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.ProductInterface;
import com.phar.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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
    public boolean addProduct(Product product) {
        this.product = product;
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
    public ObservableList<Product> listProduct() {
        ObservableList<Product> productData = FXCollections.observableArrayList();

        String query = "SELECT * from product_from_supplier";

        try(Statement stat = conn.createStatement()){
            ResultSet res = stat.executeQuery(query);

            while(res.next()){
                Product product = new Product();
                product.setSellerID(res.getString("supplier_id"));
                product.setProductId(res.getInt("product_id"));
                product.setProductName(res.getString("product_name"));
                product.setProductQuantity(res.getInt("product_quantity"));
                product.setProductComposition(res.getString("product_composition"));
                product.setProductPurchaseDate(res.getString("product_purchaseDate"));
                product.setProductMfdDate(res.getString("product_mfd"));
                product.setProductExpDate(res.getString("product_expd"));
                product.setProductCostPrice(res.getFloat("product_cost"));
                product.setProductSellPrice(res.getFloat("product_sell"));
                product.setBillNo(res.getInt("bill_no"));
                product.setProductBatchNo(res.getInt("product_batch"));
                product.setPurchaseTax(res.getInt("tax"));

                productData.add(product);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return productData;
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
