package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.extraFunctionality.AutoGenerator;
import com.phar.extraFunctionality.CFunctions;
import com.phar.extraFunctionality.Constants;
import com.phar.interfaceImplement.SupplierImplement;
import com.phar.model.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sam on 11/6/2016.
 */

public class SupplierController implements Initializable {


    private Supplier supplier;
    private SupplierImplement supplierImplement;

    @FXML
    private TextField supplierId, supplierName, supplierAddress, supplierContact, supplierEmail, supplierPanNo;

    @FXML
    private AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AutoGenerator generator = new AutoGenerator();
        supplierId.setText(generator.CurrentID("SID"));
//
//        CFunctions.restrictTxtFieldToAlphabetsOnly(supplierName);
        CFunctions.restrictTxtField(supplierName, Constants.Atoz);
//        CFunctions.restrictTxtFieldToAlphabetsOnly(supplierAddress);
        CFunctions.restrictTxtField(supplierAddress, Constants.Atoz);
//        CFunctions.restrictTxtFieldToNumbersOnly(supplierContact);
        CFunctions.restrictTxtField(supplierContact, Constants.ZerotoNine);
//        CFunctions.restrictTxtFieldToAlphabetsOnly(supplierEmail);
//        CFunctions.restrictTxtField(supplierEmail, Constants.forEmail);
//        CFunctions.restrictTxtFieldToNumbersOnly(supplierPanNo);
        CFunctions.restrictTxtField(supplierPanNo, Constants.ZerotoNine);

    }


    public void onClickSave(ActionEvent actionEvent) {
        if (CFunctions.isValidEmailAddress(supplierEmail.getText())) {

            supplier = new Supplier();
            supplier.setSupplierId(supplierId.getText());
            supplier.setSupplierName(supplierName.getText());
            supplier.setSupplierAddress(supplierAddress.getText());
            supplier.setSupplierContact(supplierContact.getText());
            supplier.setSupplierEmail(supplierEmail.getText());
            supplier.setPanNo(supplierPanNo.getText());

            supplierImplement = new SupplierImplement();
            if (supplierImplement.addSupplier(supplier)) {

                CustomAlert alert = new CustomAlert("Insert info", "New Supplier Saved Successfully");
                alert.withoutHeader();
                pane.getChildren().stream().filter(node -> node instanceof TextField).forEach(node -> {
                    ((TextField) node).clear();
                });
            }
            AutoGenerator generator = new AutoGenerator();
            supplierId.setText(generator.NewID("SID"));
        } else {
            CustomAlert alert = new CustomAlert("Email address Error", "Email You Entered is Invalid");
            alert.withoutHeader();
        }
    }
}
