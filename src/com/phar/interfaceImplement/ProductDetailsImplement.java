package com.phar.interfaceImplement;

import com.phar.interfaces.ProductDetailInterface;
import com.phar.model.ProductDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Sam on 11/16/2016.
 */
public class ProductDetailsImplement implements ProductDetailInterface {

    private Connection connection;

    @Override
    public ObservableList<ProductDetails> listSupplier(Connection connection) {
        this.connection = connection;
        ObservableList<ProductDetails> productDetails = FXCollections.observableArrayList();

        String query = "SELECT * from new_product_entry";
        try (Statement stat = this.connection.createStatement()) {
            ResultSet res = stat.executeQuery(query);

            while (res.next()) {
                ProductDetails pd = new ProductDetails();
                pd.setProductId(res.getString("product_id"));
                pd.setProductName(res.getString("product_name"));
                pd.setCompositionName(res.getString("composition_name"));
                pd.setCompanyName(res.getString("company_name"));
                pd.setMedicineGroup(res.getString("medicine_group"));
                pd.setMedicineCategory(res.getString("medicine_category"));
                pd.setNoOfPackPerUnit(res.getInt("no_of_units_per_pack"));
                pd.setVat(res.getBoolean("vat"));

                productDetails.add(pd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productDetails;
    }
}
