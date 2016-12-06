package com.phar.controller;

import com.phar.database.DatabaseConnection;
import com.phar.model.PurchaseSales;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by somecuitears on 11/29/16.
 */
public class PurchaseSalesController implements Initializable {

    @FXML
    private DatePicker fromDatePicker, toDatePicker;

    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<PurchaseSales, String> tableDate, tableBill, tablePurchase, tableSales;


    private ObservableList<PurchaseSales> forTableList = FXCollections.observableArrayList();
    private Connection connection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toDatePicker.setValue(LocalDate.now());
        fromDatePicker.setValue(LocalDate.now().minusMonths(1));
        myFuntion();
    }

    public void searchBtnClick(ActionEvent actionEvent) {
        myFuntion();
    }

    private void myFuntion() {
        tableView.getItems().clear();
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT purchase_date,bill_no,total_amount AS purchase_Total,NULL AS sales_date, NULL AS customer_billno,NULL AS sales_total FROM bill UNION SELECT NULL AS purchase_date, NULL AS bill_no, NULL AS purchase_Total, sales_date,customer_billno,total_amount FROM customer_bill";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PurchaseSales ps = new PurchaseSales();
                if (resultSet.getString("purchase_date") == null) {
                    ps.setDate(resultSet.getString("sales_date"));
                } else {
                    ps.setDate(resultSet.getString("purchase_date"));
                }
                if (resultSet.getString("bill_no") == null) {
                    ps.setBillNo(resultSet.getString("customer_billno"));
                } else {
                    ps.setBillNo(resultSet.getString("bill_no"));
                }
                ps.setPurchase(resultSet.getString("purchase_Total"));
                ps.setSales(resultSet.getString("sales_total"));
                forTableList.add(ps);
            }
            tableDate.setCellValueFactory(new PropertyValueFactory<PurchaseSales, String>("date"));
            tableBill.setCellValueFactory(new PropertyValueFactory<PurchaseSales, String>("billNo"));
            tablePurchase.setCellValueFactory(new PropertyValueFactory<PurchaseSales, String>("purchase"));
            tableSales.setCellValueFactory(new PropertyValueFactory<PurchaseSales, String>("sales"));
            tableDate.setSortType(TableColumn.SortType.ASCENDING);
            tableView.setItems(forTableList);
            tableView.getSortOrder().add(tableDate);


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
