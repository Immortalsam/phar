package com.phar.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.DatabaseOperations;
import com.phar.model.NewSales;
import com.phar.model.SalesReturn;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.System.*;

/**
 * Created by Sam on 12/2/2016.
 */
public class SalesReturnController implements Initializable {

    @FXML
    private JFXDatePicker tDate;

    @FXML
    private TextField cBill;

    @FXML
    private ComboBox<String> cProductList;

    @FXML
    private Label cRate;

    @FXML
    private ComboBox<String> pBatch;

    @FXML
    private TextField cQuantityReceived;

    @FXML
    private Label cAmount;

    @FXML
    private Label quantitySold;

    @FXML
    private JFXTreeTableColumn<SalesReturn, String> tName;

    @FXML
    private JFXTreeTableColumn<SalesReturn, String> tBatch;

    @FXML
    private JFXTreeTableColumn<SalesReturn, Double> tRate;

    @FXML
    private JFXTreeTableColumn<SalesReturn, Integer> tQuantityReceived;

    @FXML
    private JFXTreeTableColumn<SalesReturn, Double> tAmount;

    @FXML
    private JFXTreeTableView<SalesReturn> tTable;

    private Integer quanS;
    private Double rate1;

    private ResultSet resultSet;
    private List<String> productList = new ArrayList<>();
    private List<String> batchList = new ArrayList<>();
    private ObservableList<SalesReturn> salesReturnList = FXCollections.observableArrayList();

    private int updatedQuantity;
    private int acquiredQuantityFromStore;

    private Connection connection;

    public SalesReturnController() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doSearch(ActionEvent event) {
        printBill();
        cBill.getText();
        resultSet = DatabaseOperations.simpleSelect("sales_info", " DISTINCT product_name", "sales_bill='" + cBill.getText() + "'");
        try {
            while (resultSet.next()) {
                productList.add(resultSet.getString("product_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        cProductList.getItems().clear();
        cProductList.getItems().addAll(productList);
        cProductList.setValue(productList.get(0));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tDate.setValue(LocalDate.now());

        cQuantityReceived.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (cQuantityReceived.getText() == null || cQuantityReceived.getText().trim().isEmpty()) {
                cAmount.setText("");
            } else {
                quanS = Integer.valueOf(cQuantityReceived.getText());
                rate1 = Double.valueOf(cRate.getText());
                Double newV = quanS * rate1;
                cAmount.setText(String.valueOf(newV));
                if(quanS > Integer.valueOf(quantitySold.getText())){
                    cQuantityReceived.setText(quantitySold.getText());
                }
            }
        }
        ));

        cProductList.valueProperty().addListener(((observable, oldValue, newValue) -> {
            batchList.clear();
            resultSet = DatabaseOperations.simpleSelect("sales_info", "product_batch", "product_name='" + cProductList.getValue() + "' AND sales_bill='" + cBill.getText() + "'");
            try {
                while (resultSet.next()) {
                    batchList.add(resultSet.getString("product_batch"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            pBatch.getItems().clear();
            pBatch.getItems().addAll(batchList);
        }));

        pBatch.valueProperty().addListener(((observable1, oldValue1, newValue1) -> {
            resultSet = DatabaseOperations.simpleSelect("sales_info", "product_quantity,sales_amount", "product_batch ='" + pBatch.getValue() + "' AND sales_bill='" + cBill.getText() + "' AND product_name='" + cProductList.getValue() + "'");
            try {
                while (resultSet.next()) {
                    double sales_amount = Double.valueOf(resultSet.getString("sales_amount"));
                    int quantity = Integer.valueOf(resultSet.getString("product_quantity"));
                    Double rate = sales_amount/quantity;
                    cRate.setText(rate.toString());
                    quantitySold.setText(String.valueOf(quantity));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
    }

    @FXML
    void onClickAddButton(ActionEvent event) {
        SalesReturn sr = new SalesReturn();

        sr.setProductName(cProductList.getValue());
        sr.setProductBatch(pBatch.getValue());
        sr.setRate(Double.valueOf(cRate.getText()));
        sr.setQuantityReceived(Integer.valueOf(cQuantityReceived.getText()));
        sr.setTotal(Double.valueOf(cAmount.getText()));

        salesReturnList.add(sr);

        tName.setCellValueFactory(new TreeItemPropertyValueFactory<SalesReturn, String>("productName"));
        tBatch.setCellValueFactory(new TreeItemPropertyValueFactory<SalesReturn, String>("productBatch"));
        tRate.setCellValueFactory(new TreeItemPropertyValueFactory<SalesReturn, Double>("rate"));
        tAmount.setCellValueFactory(new TreeItemPropertyValueFactory<SalesReturn, Double>("total"));
        tQuantityReceived.setCellValueFactory(new TreeItemPropertyValueFactory<SalesReturn, Integer>("quantityReceived"));


    }

    @FXML
    void onClickOkButton(ActionEvent event) {
//        SalesReturn sr = new SalesReturn();
//        sr.setBillNo(cBill.getText());
//        sr.setSalesReturnDate(tDate.getValue().toString());
//        sr.setProductName(cProductList.getValue());
//        sr.setProductBatch(pBatch.getValue());
//        sr.setQuantityReceived(Integer.valueOf(cQuantityReceived.getText()));
//        sr.setTotal(Double.valueOf(cAmount.getText()));
//
//        String query = "INSERT into sales_return(bill_no,sales_return_date,product_name,product_batch,quantity_received,total_amount) VALUES (?,?,?,?,?,?)";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1, sr.getBillNo());
//            preparedStatement.setString(2, sr.getSalesReturnDate());
//            preparedStatement.setString(3, sr.getProductName());
//            preparedStatement.setString(4, sr.getProductBatch());
//            preparedStatement.setInt(5, sr.getQuantityReceived());
//            preparedStatement.setDouble(6, sr.getTotal());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        resultSet = DatabaseOperations.simpleSelect("store", "quantity", "product_name='" + cProductList.getValue() + "'AND batch='" + pBatch.getValue() + "'");
//        try {
//            while (resultSet.next()) {
//                acquiredQuantityFromStore = Integer.valueOf(resultSet.getString("quantity"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        updatedQuantity = quanS + acquiredQuantityFromStore;
//        String upadteQuery = "UPDATE store SET quantity='" + updatedQuantity + "'WHERE product_name='" + cProductList.getValue() + "'AND batch='" + pBatch.getValue() + "'";
//        try {
//            DatabaseOperations.simpleUpdate(upadteQuery);
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    public String printBill(){

    return "Something";
    }
}
