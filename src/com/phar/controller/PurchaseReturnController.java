package com.phar.controller;

import com.phar.extraFunctionality.CustomComboBox;
import com.phar.extraFunctionality.DatabaseOperations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Sam on 11/30/2016.
 */
public class PurchaseReturnController implements Initializable {

    @FXML
    private ComboBox<String> searchSupplierName;

    @FXML
    private ComboBox<String> pName;

    @FXML
    private TextField pId;

    @FXML
    private TextField pBatch;

    @FXML
    private TextField pExpiry;

    @FXML
    private TextField pRate;

    @FXML
    private TextField pQuantity;

    @FXML
    private TextField pCCCharge;

    @FXML
    private TextField pTotal;

    @FXML
    private TableView<?> ptTabelView;

    @FXML
    private TableColumn<?, ?> ptName;

    @FXML
    private TableColumn<?, ?> ptId;

    @FXML
    private TableColumn<?, ?> ptBatch;

    @FXML
    private TableColumn<?, ?> ptExpiry;

    @FXML
    private TableColumn<?, ?> ptRate;

    @FXML
    private TableColumn<?, ?> ptQuantity;

    @FXML
    private TableColumn<?, ?> ptCcharge;

    @FXML
    private TableColumn<?, ?> ptTotal;

    @FXML
    private Label supplierId;

    private ResultSet resultSet, rs1;
    private List<String> supplierList = new ArrayList<String>();
    private List<String> productList = new ArrayList<String>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //For supplier Search


        new CustomComboBox<>(searchSupplierName);
        new CustomComboBox<>(pName);
        resultSet = DatabaseOperations.simpleSelect("supplier", "supplier_name", "null");
        try {
            while (resultSet.next()) {
                supplierList.add(resultSet.getString("supplier_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        searchSupplierName.getItems().addAll(supplierList);
        searchSupplierName.valueProperty().addListener(((observable, oldValue, newValue) -> {
            pName.getItems().clear();
            productList.clear();
            resultSet = DatabaseOperations.simpleSelect("supplier", "supplier_id", "supplier_name='" + searchSupplierName.getValue() + "'");
            try {
                while (resultSet.next()) {
                    supplierId.setText(resultSet.getString("supplier_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs1 = DatabaseOperations.simpleSelect("new_purchase_entry", "product_name", "supplier_id='" + supplierId.getText() + "'");
            try {
                while (rs1.next()) {
                    productList.add(rs1.getString("product_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            pName.getItems().addAll(productList);
            pName.valueProperty().addListener((((observable1, oldValue1, newValue1) -> {
                rs1 = DatabaseOperations.simpleSelect("new_purchase_entry", "product_id,product_batch,product_expdate,product_rate,product_quantity,product_cccharge","product_name='" + pName.getValue() + "'");
                try{
                    while (rs1.next()){
                        pId.setText(rs1.getString("product_id"));
                        pBatch.setText(rs1.getString("product_batch"));
                        pExpiry.setText(rs1.getString("product_expdate"));
                        pRate.setText(String.valueOf(rs1.getFloat("product_rate")));
                        pQuantity.setText(String.valueOf(rs1.getInt("product_rate")));
                        pCCCharge.setText(String.valueOf(rs1.getFloat("product_cccharge")));
                    }
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            })));
        }));

    }
}
