package com.phar.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sam on 12/5/2016.
 */
public class SupplierController implements Initializable {

    @FXML
    private JFXTextField sId;

    @FXML
    private JFXTextField sName;

    @FXML
    private JFXTextField sAddress;

    @FXML
    private JFXTextField sContact;

    @FXML
    private JFXTextField sEmail;

    @FXML
    private JFXTextField sPanNo;

    @FXML
    private JFXButton saveButton;

    @FXML
    private Pane pane;

    @FXML
    private AnchorPane pane1;


    private Supplier supplier;
    private SupplierImplement supplierImplement;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AutoGenerator generator = new AutoGenerator();
        sId.setText(generator.CurrentID("SID"));
        CFunctions.restrictTxtField(sName, Constants.Atoz);
        CFunctions.restrictTxtField(sAddress, Constants.Atoz);
        CFunctions.restrictTxtField(sContact, Constants.ZerotoNine);
        CFunctions.restrictTxtField(sPanNo, Constants.ZerotoNine);

    }

    @FXML
    void onClickSaveButton(ActionEvent event) {
        if (CFunctions.isValidEmailAddress(sEmail.getText())) {

            supplier = new Supplier();
            supplier.setSupplierId(sId.getText());
            supplier.setSupplierName(sName.getText());
            supplier.setSupplierAddress(sAddress.getText());
            supplier.setSupplierContact(sContact.getText());
            supplier.setSupplierEmail(sEmail.getText());
            supplier.setPanNo(sPanNo.getText());

            supplierImplement = new SupplierImplement();
            if (supplierImplement.addSupplier(supplier)) {

                CustomAlert alert = new CustomAlert("Insert info", "New Supplier Saved Successfully");
                alert.withoutHeader();
                pane1.getChildren().stream().filter(node -> node instanceof TextField).forEach(node -> {
                    ((TextField) node).clear();
                });
            }
            AutoGenerator generator = new AutoGenerator();
            sId.setText(generator.NewID("SID"));
        } else {
            CustomAlert alert = new CustomAlert("Email address Error", "Email You Entered is Invalid");
            alert.withoutHeader();
        }
    }
}
