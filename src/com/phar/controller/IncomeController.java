package com.phar.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.extraFunctionality.DatabaseOperations;
import com.phar.extraFunctionality.GetTime;
import com.phar.interfaceImplement.IncomeExpImplement;
import com.phar.model.Income;
import com.phar.model.ProductEntry;
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
 * Created by Sam on 12/11/2016.
 */
public class IncomeController implements Initializable {

    public IncomeController(){
        try{
            connection = DatabaseConnection.getConnection();
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    @FXML
    private JFXTextField enterIncome, enterRupees;

    @FXML
    private JFXComboBox<String> listIncome;

    @FXML
    private DatePicker tDate;

    @FXML
    private TableView<Income> iTable;

    @FXML
    private TableColumn<Income, String> iDate, iIncomeSource;

    @FXML
    private TableColumn<Income, Double> iRupees;

    @FXML
    private Label incomeLabel,labelTotal;

    private Income i = new Income();
    private PreparedStatement preparedStatement;
    private Connection connection;
    private ResultSet resultSet,resultSet1;
    private List<String> incomeList = new ArrayList<>();
    private ObservableList<Income> infoList = FXCollections.observableArrayList();
    private Double priceFromList = 0.0;

    @FXML
    void onClickAddButton(ActionEvent event) {
        i.setIncomeSource(enterIncome.getText());
        //Add value to list from enterIncome
        listIncome.getItems().add(i.getIncomeSource());
    }

    @FXML
    void onClickSaveButton(ActionEvent event) {
        i.setPrice(Double.valueOf(enterRupees.getText()));
        i.setIncomeFromList(listIncome.getValue());
        System.out.println(listIncome.getValue());
        IncomeExpImplement iexi = new IncomeExpImplement();
        if(iexi.addIncome(i)){
            CustomAlert alert = new CustomAlert("Save Info.", "Save Successful");
            alert.withoutHeader();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tDate.setValue(LocalDate.now());
        //Date with time
        GetTime gt = new GetTime();
        i.setDate(tDate.getValue().toString().concat(" ").concat(gt.timeNow()));
        enterIncome.textProperty().addListener((observable, oldValue, newValue) -> {
            if(enterIncome.getText() == null || enterIncome.getText().trim().isEmpty()){
                    enterIncome.setText("");
            }
        });
        new CustomComboBox<>(listIncome);
            resultSet = DatabaseOperations.simpleSelect("income", "DISTINCT income_source", "null");
            try {
                while (resultSet.next()) {
                    incomeList.add(resultSet.getString("income_source"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            listIncome.getItems().addAll(incomeList);
            listIncome.valueProperty().addListener(((observable, oldValue, newValue) -> {
                iTable.getItems().clear();
                incomeLabel.setText(listIncome.getValue());
                resultSet1 = DatabaseOperations.simpleSelect("income", "income_source,date,price", "income_source='" + listIncome.getValue() + "'");
                try{
                    while (resultSet1.next()){
                        Income in = new Income();
                        in.setDateDb(resultSet1.getString("date"));
                        in.setIncomeSourceDb(resultSet1.getString("income_source"));
                        in.setPriceDb(resultSet1.getDouble("price"));
                        infoList.addAll(in);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                resultSet1 = DatabaseOperations.simpleSelect("income", "income_source, sum(price)", "income_source='" + listIncome.getValue() + "'");
                try{
                    while (resultSet1.next()){
                        priceFromList = resultSet1.getDouble("sum(price)");
                        System.out.println(priceFromList);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                iDate.setCellValueFactory(new PropertyValueFactory<Income, String>("dateDb"));
                iIncomeSource.setCellValueFactory(new PropertyValueFactory<Income, String>("incomeSourceDb"));
                iRupees.setCellValueFactory(new PropertyValueFactory<Income, Double>("priceDb"));
                iTable.setItems(infoList);
                labelTotal.setText(priceFromList.toString());
            }));
        }
    }