package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.interfaceImplement.ProductImplement;
import com.phar.interfaceImplement.SupplierImplement;
import com.phar.model.Product;
import com.phar.model.Supplier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Sam on 11/6/2016.
 */

public class SupplierController {


    private Supplier supplier;
    private SupplierImplement supplierImplement;

    @FXML
    private TextField supplierId, supplierName, supplierAddress, supplierContact, supplierCategory, supplierPanNo;

    @FXML
    private AnchorPane pane;

    @FXML
    private void onClickSave(ActionEvent e) {

        supplier = new Supplier();
        supplier.setSupplierId(supplierId.getText());
        supplier.setSupplierName(supplierName.getText());
        supplier.setSupplierAddress(supplierAddress.getText());
        supplier.setSupplierContact(supplierContact.getText());
        supplier.setSupplierCategory(supplierCategory.getText());
        supplier.setPanNo(Double.valueOf(supplierPanNo.getText()));

        supplierImplement = new SupplierImplement();
        if (supplierImplement.addSupplier(supplier)) {

            CustomAlert alert = new CustomAlert("Insert info", "New Supplier Saved Successfully");
            alert.withoutHeader();
            pane.getChildren().stream().filter(node -> node instanceof TextField).forEach(node -> {
                ((TextField) node).clear();
            });
        }
    }
}
