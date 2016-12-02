package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.extraFunctionality.DatabaseOperations;
import com.phar.interfaceImplement.SupplierPaymentIntImplement;
import com.phar.model.SupplierPayment;
import com.phar.model.Transactions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

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
 * Created by Sam on 11/22/2016.
 */
public class SupplierPaymentController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private DatePicker tDate;

    @FXML
    private ComboBox<String> sNameCBox;

    @FXML
    private TextField amtToBePaid, amtPaid, amtRemaining, sId;

    //For table data
    @FXML
    private TableView<Transactions> tableTrans;

    @FXML
    private TableColumn<Transactions, String> tableDate;

    @FXML
    private TableColumn<Transactions, String> tDescription;

    @FXML
    private TableColumn<Transactions, String> tdrAmt;

    @FXML
    private TableColumn<Transactions, String> tCrAmt;

    private Connection connection;
    private ResultSet resultSet, rs, rs1;
    private PreparedStatement preparedStatement;
    private List<String> supplierList = new ArrayList<String>();
    private ObservableList<Transactions> supplierPurchaseList = FXCollections.observableArrayList();
    private ObservableList<Transactions> supplierPaidList = FXCollections.observableArrayList();
    private ObservableList<Transactions> newList = FXCollections.observableArrayList();
    private float billTotal = 0;
    private float paidTotal = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //for the today's Date
        tDate.setValue(LocalDate.now());
        //For Supplier's Name
        new CustomComboBox<String>(sNameCBox);

        String sQuery = "SELECT supplier_name from supplier";
//        resultSet = CFunctions.executeQuery(preparedStatement, connection, sQuery, resultSet);
        resultSet = DatabaseOperations.simpleSelect("supplier", "supplier_name", "null");
        try {
            while (resultSet.next()) {
                supplierList.add(resultSet.getString("supplier_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sNameCBox.getItems().addAll(supplierList);
        onLoad();
    }

    @FXML
    void onSaveClicked(ActionEvent event) {
        if (!amtPaid.getText().isEmpty()) {
            SupplierPayment supplierPayment = new SupplierPayment();
            supplierPayment.setSupplierId(sId.getText());
            supplierPayment.setAmtToBePaid(Float.valueOf(amtToBePaid.getText()));
            supplierPayment.setAmtPaid(Float.valueOf(amtPaid.getText()));
            supplierPayment.setPaymentDate(String.valueOf(tDate.getValue()));

            Float newValue = Float.valueOf(amtToBePaid.getText()) - Float.valueOf(amtPaid.getText());
            amtRemaining.setText(String.valueOf(newValue));

            SupplierPaymentIntImplement suppIntImp = new SupplierPaymentIntImplement();

            if (suppIntImp.addSupplierPayment(supplierPayment)) {
                CustomAlert alert = new CustomAlert("Info", "Save Successful");
                alert.withoutHeader();
            }
            amtPaid.setText("");
            amtToBePaid.setText(amtRemaining.getText());
            onLoad();
            listTableView();
        } else {
            CustomAlert alert = new CustomAlert("Amount Field Empty", "Enter The amount ");
            alert.withoutHeader();
        }
    }

    private void onLoad() {
        sNameCBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            listTableView();
            //Billed amount
            resultSet = DatabaseOperations.simpleSelect("bill", "supplier_id, SUM(total_amount)", "supplier_id='" + sId.getText() + "'");
            try {
                while (resultSet.next()) {
                    billTotal = resultSet.getFloat("sum(total_amount)");
                    System.out.println(billTotal);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //Paid Amount
            resultSet = DatabaseOperations.simpleSelect("supplier_payment", "supplier_id, SUM(amt_paid)", "supplier_id = '" + sId.getText() + "'");
            try {
                if (!resultSet.isBeforeFirst()) {
                    paidTotal = 0;
                    System.out.println(paidTotal);
                    amtToBePaid.setText(String.valueOf(billTotal - paidTotal));
                }
                while (resultSet.next()) {
                    paidTotal = resultSet.getFloat("sum(amt_paid)");
                    amtToBePaid.setText(String.valueOf(billTotal - paidTotal));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void listTableView() {
        supplierPurchaseList.clear();
        tableTrans.getItems().clear();
        resultSet = DatabaseOperations.simpleSelect("supplier", "supplier_id", "supplier_name= '" + sNameCBox.getValue() + "'");
        try {
            while (resultSet.next()) {
                sId.setText(resultSet.getString("supplier_id"));
                rs = DatabaseOperations.simpleSelect("bill", "supplier_id, purchase_date, bill_no, total_amount, NULL AS payment_id, NULL AS payment_date, NULL AS amt_paid", "supplier_id = '" + sId.getText() + "'UNION SELECT supplier_id, NULL, NULL, NULL, payment_id, payment_date, amt_paid FROM supplier_payment WHERE supplier_id = '" + sId.getText() + "'");
                while (rs.next()) {
                    Transactions t = new Transactions();
                    if (rs.getString("purchase_date") == null) {
                        t.setDate(rs.getString("payment_date"));
                    } else {
                        t.setDate(rs.getString("purchase_date"));
                    }
                    if (rs.getFloat("total_amount") == 0) {
                        t.setCr("-");
                    } else {
                        t.setCr(String.valueOf(rs.getFloat("total_amount")));
                    }
                    if (rs.getString("bill_no") == null) {
                        t.setDescription("Payment");
                    } else {
                        t.setDescription(rs.getString("bill_no"));
                    }
                    if (rs.getFloat("amt_paid") == 0) {
                        t.setDr("-");
                    } else {
                        t.setDr(String.valueOf(rs.getFloat("amt_paid")));
                    }

                    supplierPurchaseList.add(t);
                }

                tDescription.setCellValueFactory(new PropertyValueFactory<Transactions, String>("description"));
                tCrAmt.setCellValueFactory(new PropertyValueFactory<Transactions, String>("cr"));
                tdrAmt.setCellValueFactory(new PropertyValueFactory<Transactions, String>("dr"));
                tableDate.setCellValueFactory(new PropertyValueFactory<Transactions, String>("date"));
            }
            tableTrans.setItems(supplierPurchaseList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
