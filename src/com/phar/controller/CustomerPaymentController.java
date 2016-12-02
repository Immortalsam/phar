package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CFunctions;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.interfaceImplement.CustomerInterfaceImplement;
import com.phar.model.CustomerPayment;
import com.phar.model.CustomerTransactions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
 * Created by somecuitears on 11/23/16.
 */
public class CustomerPaymentController implements Initializable {

    private Connection connection;
    private ResultSet resultSet, rs;
    private PreparedStatement preparedStatement;
    private List<String> customerList = new ArrayList<String>();
    private ObservableList<CustomerTransactions> combinedList = FXCollections.observableArrayList();
    private float salesTotal = 0;
    private float paidTotal = 0;

    @FXML
    private DatePicker paidDatePicker;

    @FXML
    private ComboBox<String> customerNameCBox;

    @FXML
    private TextField receivableTxtField;

    @FXML
    private TextField receivedTxtField;

    @FXML
    private Button okBtn;

    @FXML
    private Label displayRemaining;

    @FXML
    private TableView<CustomerTransactions> ctTable;

    @FXML
    private TableColumn<CustomerTransactions, String> ctDate;

    @FXML
    private TableColumn<CustomerTransactions, String> ctDescription;

    @FXML
    private TableColumn<CustomerTransactions, String> ctPayment;

    @FXML
    private TableColumn<CustomerTransactions, String> ctSales;

    @FXML
    private Label customerId;

    @FXML
    private Label reamainingLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paidDatePicker.setValue(LocalDate.now());

        new CustomComboBox<String>(customerNameCBox);
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String query = "SELECT customer_name from customer_info";
        resultSet = CFunctions.executeQuery(preparedStatement, connection, query, resultSet);
        try {
            while (resultSet.next()) {
                customerList.add(resultSet.getString("customer_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        customerNameCBox.getItems().addAll(customerList);

        //For Seller's Id
        customerNameCBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            String idQuery = "SELECT customer_id from customer_info WHERE customer_name= '" + customerNameCBox.getValue() + "'";
            resultSet = CFunctions.executeQuery(preparedStatement, connection, idQuery, resultSet);
            try {
                while (resultSet.next()) {
                    customerId.setText(resultSet.getString("customer_id"));

                    String sQuery = "SELECT customer_id, sales_date, customer_billno, total_amount, NULL AS payment_date, NULL AS amount_paid FROM customer_bill WHERE customer_id ='" + customerId.getText() + "'UNION SELECT customer_id, NULL, NULL, NULL, payment_date, amount_paid FROM customer_payment WHERE customer_id='" + customerId.getText()+ "'";
                    rs = CFunctions.executeQuery(preparedStatement, connection, sQuery, rs);
                    while (rs.next()) {
                        CustomerTransactions ct = new CustomerTransactions();
                        if (rs.getString("sales_date") == null) {
                            ct.setDate(rs.getString("payment_date"));
                        } else {
                            ct.setDate(rs.getString("sales_date"));
                        }

                        ct.setCr(String.valueOf(rs.getFloat("total_amount")));
                        ct.setDr(String.valueOf(rs.getFloat("amount_paid")));
                        if (rs.getString("customer_billno") == null) {
                            ct.setDescription("Payment");
                        } else {
                            ct.setDescription(rs.getString("customer_billno"));
                        }
                        if (rs.getString("customer_billno") == null) {
                            ct.setDescription("Payment");
                        } else {
                            ct.setDescription(rs.getString("customer_billno"));
                        }
                        combinedList.add(ct);
                    }
                    ctDate.setCellValueFactory(new PropertyValueFactory<CustomerTransactions, String>("date"));
                    ctDescription.setCellValueFactory(new PropertyValueFactory<CustomerTransactions, String>("description"));
                    ctSales.setCellValueFactory(new PropertyValueFactory<CustomerTransactions, String>("cr"));
                    ctPayment.setCellValueFactory(new PropertyValueFactory<CustomerTransactions, String>("dr"));
                }
                ctTable.setItems(combinedList);
            } catch (SQLException e) {
                e.printStackTrace();
            }

//            //Total sales amount
            String querySales = "SELECT customer_id, sum(total_amount) from customer_bill WHERE customer_id='" + customerId.getText()+"'";
            resultSet = CFunctions.executeQuery(preparedStatement, connection, querySales, resultSet);
//            resultSet = DatabaseOperations.simpleSelect("customer_bill", "customer_id, sum(total_amount)", "null");
            try {
                while (resultSet.next()) {
                    salesTotal = resultSet.getFloat("sum(total_amount)");

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Total paid amount
            String queryPaid = "SELECT customer_id, sum(amount_paid) from customer_payment WHERE customer_id='" + customerId.getText()+ "'";
            resultSet = CFunctions.executeQuery(preparedStatement, connection, queryPaid, resultSet);
            try {
                if (!resultSet.isBeforeFirst()) {
                    paidTotal = 0;
                    System.out.println(paidTotal);
                    receivableTxtField.setText(String.valueOf(salesTotal - paidTotal));
                }
                while (resultSet.next()) {
                    paidTotal = resultSet.getFloat("sum(amount_paid)");
                    receivableTxtField.setText(String.valueOf(salesTotal - paidTotal));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));

    }

    @FXML
    void onclickSaveButton(ActionEvent event) {
        CustomerPayment cp = new CustomerPayment();
        cp.setCustomerId(customerId.getText());
        cp.setAmtToBePaid(Float.valueOf(receivableTxtField.getText()));
        cp.setAmtPaid(Float.valueOf(receivedTxtField.getText()));
        cp.setPaymentDate(paidDatePicker.getValue().toString());

        Float newValue = Float.valueOf(receivableTxtField.getText()) - Float.valueOf(receivedTxtField.getText());
        reamainingLabel.setText(String.valueOf(newValue));
        System.out.println(newValue);

        CustomerInterfaceImplement cii = new CustomerInterfaceImplement();

        if (cii.addCustomerPayment(cp)) {
            CustomAlert alert = new CustomAlert("Info", "Save Successful");
            alert.withoutHeader();
        }

    }
}
