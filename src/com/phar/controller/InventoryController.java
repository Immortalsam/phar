package com.phar.controller;

import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CFunctions;
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

    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    @FXML
    private ComboBox<String> inventoryProductList;

    @FXML
    private TextField inventoryQty;

    @FXML
    private TextField inventoryBatch;

    @FXML
    private TextField inventoryMrp;

    @FXML
    private TextField inventoryRackNo;

    @FXML
    private Button addToInventory;

    @FXML
    private TextField inventoryProductExpDate;

    @FXML
    private Label qtyLbl;

    private Connection connection;
    private List<String> productList = new ArrayList<String>();
    private Double qtyLeft;
    private String productIDD;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT product_name FROM inventory";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(resultSet.getString("product_name"));
            }
            inventoryProductList.getItems().addAll(productList);
            inventoryProductList.valueProperty().addListener((observable, oldValue, newValue) -> {
                String otherDetailsQuery = "SELECT product_id,expire_date,quantity,batch,mRP FROM inventory WHERE product_name='" + inventoryProductList.getValue() + "'";
                System.out.println(otherDetailsQuery);
                resultSet = CFunctions.executeQuery(preparedStatement, connection, otherDetailsQuery, resultSet);
                try {
                    while (resultSet.next()) {
                        productIDD = resultSet.getString("product_id");
                        inventoryProductExpDate.setText(resultSet.getString("expire_date"));
                        inventoryBatch.setText(resultSet.getString("batch"));
                        qtyLbl.setText("Quantity Left : " + resultSet.getString("quantity"));
                        qtyLeft = Double.valueOf(resultSet.getString("quantity"));
                        //inventoryQty.setText(resultSet.getString("quantity"));
                        inventoryMrp.setText(resultSet.getString("mRP"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addToInventoryClick(ActionEvent event) {
        Sales sales = new Sales();

        sales.setProductID(productIDD);
        sales.setProductName(inventoryProductList.getValue());
        sales.setProductBatch(inventoryBatch.getText());
        sales.setProductQuantity(Double.valueOf(inventoryQty.getText()));
        sales.setExpireDate(inventoryProductExpDate.getText());
        sales.setmRp(Double.valueOf(inventoryMrp.getText()));
        sales.setRackNumber(inventoryRackNo.getText());

        Double newQuantity = qtyLeft - Double.valueOf(inventoryQty.getText());
        String sql1 = "UPDATE inventory SET quantity='" + newQuantity + "' WHERE product_name = '" + inventoryProductList.getValue() + "'";
        System.out.println(sql1);
        try {
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sql1 = "INSERT INTO store VALUES (?,?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, sales.getProductID());
            preparedStatement.setString(3, sales.getProductName());
            preparedStatement.setDouble(4, sales.getProductQuantity());
            preparedStatement.setString(5, sales.getProductBatch());
            preparedStatement.setDouble(6, sales.getmRp());
            preparedStatement.setString(7, sales.getExpireDate());
            preparedStatement.setString(8, sales.getRackNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}


