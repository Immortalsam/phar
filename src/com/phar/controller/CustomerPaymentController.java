package com.phar.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Created by somecuitears on 11/23/16.
 */
public class CustomerPaymentController implements Initializable {

    private Connection connection;

    @FXML
    private DatePicker paidDatePicker;

    @FXML
    private ComboBox<?> customerNameCBox;

    @FXML
    private TextField receivableTxtField;

    @FXML
    private TextField receivedTxtField;

    @FXML
    private Button okBtn;

    @FXML
    private Label displayRemaining;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        paidDatePicker.setValue(LocalDate.now());
    }
}
