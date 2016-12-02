package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.DatabaseOperations;
import com.phar.model.SalesReturn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Sam on 12/2/2016.
 */
public class SalesReturnController implements Initializable {
    @FXML
    private DatePicker tDate;

    @FXML
    private TextField cBill;

    @FXML
    private ComboBox<String> cProductList;

    @FXML
    private Label cProductId;

    @FXML
    private Label cExpDate;

    @FXML
    private Label cRate;

    @FXML
    private Label cBatch;

    @FXML
    private TextField cQuantityReceived;

    @FXML
    private Label cAmount;

    private ResultSet resultSet;
    private List<String> productList =  new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tDate.setValue(LocalDate.now());
    }


    @FXML
    void doSearch(ActionEvent event) {
        cBill.getText();

        resultSet = DatabaseOperations.simpleSelect("sales_info", "product_name", "sales_bill='" + cBill.getText()+ "'");
        try{
            while (resultSet.next()){
                productList.add(resultSet.getString("product_name"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        cProductList.getItems().addAll(productList);
        cProductList.valueProperty().addListener(((observable, oldValue, newValue) -> {
            resultSet = DatabaseOperations.simpleSelect("sales_info", "product_batch", "product_name='" + cProductList.getValue()+ "' AND sales_bill='" + cBill.getText() + "'");
            try{
                while (resultSet.next())
                cBatch.setText(resultSet.getString("product_batch"));
            }catch (SQLException e){
                e.printStackTrace();
            }
        }));
    }
}
