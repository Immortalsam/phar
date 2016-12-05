package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.DatabaseOperations;
import com.phar.model.Sales;
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
import java.sql.PreparedStatement;
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
    private Label cRate;

    @FXML
    private ComboBox<String> pBatch;

    @FXML
    private TextField cQuantityReceived;

    @FXML
    private Label cAmount;

    @FXML
    private Label sDiscount;

    @FXML
    private Label quantitySold;

    private Integer quanS;
    private Double rate1;

    private Double totalAmount;
    private Integer quantity;
    private Double discount;
    private Double rate;

    private ResultSet resultSet;
    private List<String> productList =  new ArrayList<>();
    private List<String> batchList =  new ArrayList<>();

    private int updatedQuantity;
    private int acquiredQuantityFromStore;

    private Connection connection;

    public SalesReturnController(){
        try{
            connection = DatabaseConnection.getConnection();
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    void doSearch(ActionEvent event) {
        cBill.getText();

        resultSet = DatabaseOperations.simpleSelect("sales_info", " DISTINCT product_name", "sales_bill='" + cBill.getText()+ "'");
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
                while (resultSet.next()) {
                    batchList.add(resultSet.getString("product_batch"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            pBatch.getItems().addAll(batchList);
            pBatch.valueProperty().addListener(((observable1, oldValue1, newValue1) -> {
                resultSet = DatabaseOperations.simpleSelect("sales_info", "product_quantity,sales_amount,discount", "product_batch ='" + pBatch.getValue() + "' AND sales_bill='" + cBill.getText() + "' AND product_name='" + cProductList.getValue()+ "'");
                try{
                    while (resultSet.next()){

                        totalAmount = Double.valueOf(resultSet.getString("sales_amount"));
                        quantity = Integer.valueOf(resultSet.getString("product_quantity"));
                        discount = Double.valueOf(resultSet.getString("discount"));

                        sDiscount.setText(resultSet.getString("discount"));

                        rate = totalAmount / ((quantity)*(1- (discount/100)));
                        System.out.println(rate);
                        cRate.setText(String.valueOf(rate));
                        quantitySold.setText(resultSet.getString("product_quantity"));
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }));
        }));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tDate.setValue(LocalDate.now());

        cQuantityReceived.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (cQuantityReceived.getText() == null || cQuantityReceived.getText().trim().isEmpty()) {
                cAmount.setText("");
            }else
            {
            quanS = Integer.valueOf(cQuantityReceived.getText());
            rate1 = Double.valueOf(cRate.getText());
            Double newV = (quanS * rate1) - ((discount / 100)*(quanS * rate1)) ;
            cAmount.setText(String.valueOf(newV));
            }
        }
        ));
    }

    @FXML
    void onClickOkButton(ActionEvent event) {
        SalesReturn sr =  new SalesReturn();
        sr.setBillNo(cBill.getText());
        sr.setSalesReturnDate(tDate.getValue().toString());
        sr.setProductName(cProductList.getValue());
        sr.setProductBatch(pBatch.getValue());
        sr.setQuantityReceived(Integer.valueOf(cQuantityReceived.getText()));
        sr.setTotal(Double.valueOf(cAmount.getText()));

        String query = "INSERT into sales_return(bill_no,sales_return_date,product_name,product_batch,quantity_received,total_amount) VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,sr.getBillNo());
            preparedStatement.setString(2,sr.getSalesReturnDate());
            preparedStatement.setString(3,sr.getProductName());
            preparedStatement.setString(4, sr.getProductBatch());
            preparedStatement.setInt(5,sr.getQuantityReceived());
            preparedStatement.setDouble(6, sr.getTotal());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        resultSet = DatabaseOperations.simpleSelect("store","quantity","product_name='" + cProductList.getValue() + "'AND batch='" + pBatch.getValue()+ "'");
        try{
            while (resultSet.next()){
                acquiredQuantityFromStore = Integer.valueOf(resultSet.getString("quantity"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        updatedQuantity = quanS + acquiredQuantityFromStore;
        String upadteQuery = "UPDATE store SET quantity='" + updatedQuantity+ "'WHERE product_name='" +cProductList.getValue()+ "'AND batch='" + pBatch.getValue()+ "'";
        try{
            DatabaseOperations.simpleUpdate(upadteQuery);
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
