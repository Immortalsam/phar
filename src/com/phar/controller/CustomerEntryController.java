package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.extraFunctionality.AutoGenerator;
import com.phar.interfaceImplement.CustomerInterfaceImplement;
import com.phar.model.CustomerInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sam on 11/27/2016.
 */
public class CustomerEntryController implements Initializable {

    @FXML
    private TextField cId, cName, cAddress, cPhoneNo, cEmail;

    @FXML
    void onClickSaveButton(ActionEvent event) {

        CustomerInfo ci = new CustomerInfo();
        ci.setCustomerId(cId.getText());
        ci.setCustomerName(cName.getText());
        ci.setCustomerAddress(cAddress.getText());
        ci.setCustomerContact(cPhoneNo.getText());
        ci.setCustomerEmail(cEmail.getText());

        CustomerInterfaceImplement cii = new CustomerInterfaceImplement();
        if (cii.addCustomer(ci)) {
            CustomAlert ca = new CustomAlert("Insert Info.", "Saved Cutomer");
            ca.withoutHeader();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AutoGenerator generator = new AutoGenerator();
        cId.setText(generator.NewID("CID"));
    }
}
