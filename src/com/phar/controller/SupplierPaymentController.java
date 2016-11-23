package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CFunctions;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.interfaceImplement.SupplierPaymentIntImplement;
import com.phar.model.SupplierPayment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringJoiner;

/**
 * Created by Sam on 11/22/2016.
 */
public class SupplierPaymentController implements Initializable {

    @FXML
    private DatePicker tDate;

    @FXML
    private ComboBox<String> sNameCBox;

    @FXML
    private TextField amtToBePaid;

    @FXML
    private TextField amtPaid;

    @FXML
    private TextField amtRemaining;

    @FXML
    private TextField sId;

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private List<String> supplierList = new ArrayList<String>();
    private float billTotal = 0;
    private float paidTotal = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //for the today's Date
        tDate.setValue(LocalDate.now());

        //For Supplier's Name
        new CustomComboBox<String>(sNameCBox);
        try{
            connection = DatabaseConnection.getConnection();
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

        String sQuery = "SELECT supplier_name from supplier";
        resultSet = CFunctions.executeQuery(preparedStatement, connection, sQuery, resultSet);
        try{
            while (resultSet.next()){
                supplierList.add(resultSet.getString("supplier_name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        //For supplier's Id
        sNameCBox.getItems().addAll(supplierList);
        sNameCBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            String idQuery = "SELECT supplier_id from supplier WHERE supplier_name= '" + sNameCBox.getValue() + "'";
            resultSet = CFunctions.executeQuery(preparedStatement, connection, idQuery, resultSet);
            try{
                while (resultSet.next()){
                    sId.setText(resultSet.getString("supplier_id"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }

           //Billed amount
            String querybill = "SELECT supplier_id, sum(total_amount) from bill group by supplier_id";
            resultSet = CFunctions.executeQuery(preparedStatement, connection, querybill, resultSet);
            try{
                while(resultSet.next()){
                    billTotal = resultSet.getFloat("sum(total_amount)");
                    System.out.println(billTotal);
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }
            //Paid Amount
            String queryPaid = "SELECT supplier_id, sum(amt_paid) from supplier_payment group by supplier_id";
            resultSet = CFunctions.executeQuery(preparedStatement, connection, queryPaid, resultSet);
            try{
                if(!resultSet.isBeforeFirst()){
                    paidTotal = 0;
                    System.out.println(paidTotal);
                    amtToBePaid.setText(String.valueOf(billTotal - paidTotal));
                }
                while (resultSet.next()){
                    paidTotal = resultSet.getFloat("sum(amt_paid)");
                    amtToBePaid.setText(String.valueOf(billTotal - paidTotal));
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }));
    }

    @FXML
    void onSaveClicked(ActionEvent event) {
        SupplierPayment supplierPayment = new SupplierPayment();
        supplierPayment.setSupplierId(sId.getText());
        supplierPayment.setAmtToBePaid(Float.valueOf(amtToBePaid.getText()));
        supplierPayment.setAmtPaid(Float.valueOf(amtPaid.getText()));
        supplierPayment.setPaymentDate(String.valueOf(tDate.getValue()));

        Float newValue = Float.valueOf(amtToBePaid.getText()) - Float.valueOf(amtPaid.getText());
        amtRemaining.setText(String.valueOf(newValue));

        SupplierPaymentIntImplement suppIntImp = new SupplierPaymentIntImplement();

        if(suppIntImp.addSupplierPayment(supplierPayment)){
            CustomAlert alert = new CustomAlert("Info", "Save Successful");
            alert.withoutHeader();
        }

    }
}
