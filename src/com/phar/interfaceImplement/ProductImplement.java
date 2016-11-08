package com.phar.interfaceImplement;

import com.phar.database.DatabaseConnection;
import com.phar.interfaces.ProductInterface;
import com.phar.model.Product;

import javax.swing.text.DateFormatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Sam on 11/7/2016.
 */
public class ProductImplement implements ProductInterface {


    static Connection conn;

    public ProductImplement(){
        try{
            conn = DatabaseConnection.getConnection();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean addProduct(Product product) {

        String addquery = "INSERT into product_from_supplier (supplier_id, product_id , product_name, product_quantity, " +
                "product_composition, product_purchaseDate, product_mfd, product_expd, product_cost, product_sell, bill_no" +
                "product_batch, tax ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";


        try{
            PreparedStatement stat = conn.prepareStatement(addquery);
            stat.setString(1, product.getSellerID());
            stat.setInt(2, product.getProductId());
            stat.setString(3, product.getProductName());
            stat.setInt(4, product.getProductQuantity());
            stat.setString(5, product.getProductComposition());
            stat.setString(6, product.getProductPurchaseDate());
            stat.setString(7, product.getProductMfdDate());
            stat.setString(8, product.getProductExpDate());
            stat.setFloat(9, product.getProductCostPrice());
            stat.setFloat(10, product.getProductSellPrice());
            stat.setInt(11, product.getBillNo());
            stat.setInt(12, product.getProductBatchNo());
            stat.setInt(13, product.getPurchaseTax());

            stat.executeUpdate();
            return true;
            
        //Sql error
        }catch (SQLException e){
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
