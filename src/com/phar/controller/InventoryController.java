package com.phar.controller;

import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.DatabaseOperations;
import com.phar.model.Sales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by somecuitears on 11/18/16.
 */
public class InventoryController implements Initializable {

    @FXML
    private ComboBox<String> inventoryProductList;

    @FXML
    private TextField inventoryQty, inventoryBatch, inventoryMrp, inventoryRackNo, inventoryProductExpDate;

    @FXML
    private Button addToInventory;

    @FXML
    private Label qtyLbl;

    private Connection connection;
    private ResultSet resultSet;
    private List<String> productList = new ArrayList<String>();
    private Double qtyLeft;
    private String productIDD;

    //    private PreparedStatement preparedStatement;
    private String query;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            resultSet = DatabaseOperations.simpleSelect("inventory", "product_name", "null");
            while (resultSet.next()) {
                productList.add(resultSet.getString("product_name"));
            }
            inventoryProductList.getItems().addAll(productList);
            inventoryProductList.valueProperty().addListener((observable, oldValue, newValue) -> {
                resultSet = DatabaseOperations.simpleSelect("inventory", "product_id,expire_date,quantity,batch,mRP", "product_name='" + inventoryProductList.getValue() + "'");
                try {
                    while (resultSet.next()) {
                        productIDD = resultSet.getString("product_id");
                        inventoryProductExpDate.setText(resultSet.getString("expire_date"));
                        inventoryBatch.setText(resultSet.getString("batch"));
                        qtyLbl.setText("Quantity Left : " + resultSet.getString("quantity"));
                        qtyLeft = Double.valueOf(resultSet.getString("quantity"));
                        inventoryMrp.setText(resultSet.getString("mRP"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addToInventoryClick(ActionEvent event) {
        Sales sales = new Sales();

        sales.setProductID(productIDD);
        sales.setProductName(inventoryProductList.getValue());
        sales.setProductBatch(inventoryBatch.getText());
        sales.setProductQuantity(Integer.valueOf(inventoryQty.getText()));
        sales.setExpireDate(inventoryProductExpDate.getText());
        sales.setmRp(Double.valueOf(inventoryMrp.getText()));
        sales.setRackNumber(inventoryRackNo.getText());

        Double newQuantity = qtyLeft - Double.valueOf(inventoryQty.getText());
        query = "UPDATE inventory SET quantity='" + newQuantity + "' WHERE product_name = '" + inventoryProductList.getValue() + "'";
        System.out.println(query);
        try {
            connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            query = "INSERT INTO store VALUES (?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, sales.getProductID());
            preparedStatement.setString(3, sales.getProductName());
            preparedStatement.setDouble(4, sales.getProductQuantity());
            preparedStatement.setString(5, sales.getProductBatch());
            preparedStatement.setDouble(6, sales.getmRp());
            preparedStatement.setString(7, sales.getExpireDate());
            preparedStatement.setString(8, sales.getRackNumber());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}


