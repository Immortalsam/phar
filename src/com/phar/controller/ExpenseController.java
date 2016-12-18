package com.phar.controller;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.extraFunctionality.DatabaseOperations;
import com.phar.extraFunctionality.GetTime;
import com.phar.interfaceImplement.IncomeExpImplement;
import com.phar.model.Expenses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Sam on 12/12/2016.
 */
public class ExpenseController  implements Initializable {

    @FXML
    private JFXTextField enterExpense,enterAmount;

    @FXML
    private JFXComboBox<String> listExpense;

    @FXML
    private DatePicker tDate;

    @FXML
    private TableView<Expenses> eTable;

    @FXML
    private TableColumn<Expenses, String> eDate;

    @FXML
    private TableColumn<Expenses, String> eExpenseList;

    @FXML
    private TableColumn<Expenses, Double> eAmount;

    @FXML
    private Label expenseLabel,eTotal;

    Expenses e = new Expenses();
    private ResultSet resultSet;
    private List<String> expenseList = new ArrayList<>();
    private ObservableList<Expenses> infoList = FXCollections.observableArrayList();
    private Connection connection;
    private ResultSet resultSet1;
    private Double amountFromList = 0.0;
    String dateWithTime = "";

    public ExpenseController(){
        try{
            connection = DatabaseConnection.getConnection();
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onClickAddButton(ActionEvent event) {
        //Get the value entered from text field and
        // show it in the list when the add button is clicked.

        e.setExpense(enterExpense.getText());
        listExpense.getItems().addAll(e.getExpense());

    }

    @FXML
    void onClickSaveButton(ActionEvent event) {
        GetTime gt = new GetTime();
        dateWithTime = tDate.getValue().toString().concat(" ").concat(gt.timeNow());
        e.setDate(dateWithTime);
        System.out.println(dateWithTime);
        e.setAmount(Double.valueOf(enterAmount.getText()));
        e.setExpenseFromList(listExpense.getValue());
        IncomeExpImplement iexi = new IncomeExpImplement();
        if(iexi.addExpense(e)){
            CustomAlert alert = new CustomAlert("Save Info.", "Save Successful");
            alert.withoutHeader();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tDate.setValue(LocalDate.now());
        //For the amount entered is empty.
        enterAmount.textProperty().addListener((observable, oldValue, newValue) -> {
            if(enterAmount.getText() == null || enterAmount.getText().trim().isEmpty()){
                enterAmount.setText("");
            }
        });
        new CustomComboBox<>(listExpense);
        resultSet = DatabaseOperations.simpleSelect("expense", "DISTINCT expense_source", "null");
        try {
            while (resultSet.next()) {
                expenseList.add(resultSet.getString("expense_source"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listExpense.getItems().addAll(expenseList);
        listExpense.valueProperty().addListener(((observable, oldValue, newValue) -> {
            eTable.getItems().clear();
            expenseLabel.setText(listExpense.getValue());
            resultSet1 = DatabaseOperations.simpleSelect("expense", "expense_source,date,amount", "expense_source='" + listExpense.getValue() + "'");
            try {
                while (resultSet1.next()) {
                    Expenses ex = new Expenses();
                    ex.setDateDb(resultSet1.getString("date"));
                    ex.setExpenseDb(resultSet1.getString("expense_source"));
                    ex.setAmountDb(resultSet1.getDouble("amount"));
                    infoList.addAll(ex);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resultSet1 = DatabaseOperations.simpleSelect("expense", "expense_source, sum(amount)", "expense_source='" + listExpense.getValue() + "'");
            try {
                while (resultSet1.next()) {
                    amountFromList = resultSet1.getDouble("sum(amount)");
                    System.out.println(amountFromList);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            eDate.setCellValueFactory(new PropertyValueFactory<Expenses, String>("dateDb"));
            eExpenseList.setCellValueFactory(new PropertyValueFactory<Expenses, String>("expenseDb"));
            eAmount.setCellValueFactory(new PropertyValueFactory<Expenses, Double>("amountDb"));
            eTable.setItems(infoList);
            eTotal.setText(amountFromList.toString());
        }));
    }
}
