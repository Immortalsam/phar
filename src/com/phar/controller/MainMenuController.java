package com.phar.controller;

/**
 * Created by Sam on 11/10/2016.
 */

import com.phar.extraFunctionality.CFunctions;
import com.phar.extraFunctionality.Constants;
import com.phar.extraFunctionality.NavigationHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private NavigationHandler navigation = new NavigationHandler();

    @FXML
    private Button purchaseEntryBtn, productDetailsBtn, productEntryBtn, inventoryBtn, salesBtn, oldSupplier, paymentReceiveBtn, paymentPayBtn, paymentBtn, newSupplier;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        System.out.println("Username : " + CFunctions.session.get("userName", ""));
//        System.out.println("Password : " + CFunctions.session.get("passWord", ""));

    }


//    void mainPurchaseButton(ActionEvent event) {
//        productDetailsBtn.setOpacity(1);
//        purchaseEntryBtn.setOpacity(1);
//        productEntryBtn.setOpacity(1);
//        oldSupplier.setOpacity(0);
//        newSupplier.setOpacity(0);
//        paymentPayBtn.setOpacity(0);
//        paymentReceiveBtn.setOpacity(0);
//    }

    public void showOption(MouseEvent mouseEvent) {
        oldSupplier.setOpacity(1);
        newSupplier.setOpacity(1);
        productDetailsBtn.setOpacity(0);
        purchaseEntryBtn.setOpacity(0);
        productEntryBtn.setOpacity(0);
        paymentPayBtn.setOpacity(0);
        paymentReceiveBtn.setOpacity(0);
    }


    public void onActionNewSupplier(ActionEvent event) throws IOException {
        navigation.frameNavigation(event, Constants.SUPPLIER_FXML, Constants.MAIN_MENU_FXML, "Add New Suppliers", "Main Menu");
    }

    public void purchaseEntryClick(ActionEvent event) {
        navigation.frameNavigation(event, Constants.NEW_PURCHASE_ENTRY_FXML, Constants.MAIN_MENU_FXML, "New Purchase Entry", "Main Menu");
    }

    public void productDetailsClick(ActionEvent event) {
        navigation.frameNavigation(event, Constants.PRODUCT_TABLE_FXML, Constants.MAIN_MENU_FXML, "Product Details", "Main Menu");
    }

    public void productEntryClick(ActionEvent event) {
        navigation.frameNavigation(event, Constants.PRODUCT_ENTRY_FXML, Constants.MAIN_MENU_FXML, "Product Entry", "Main Menu");
    }

    public void inventoryBtnClick(ActionEvent event) {
        navigation.frameNavigation(event, Constants.INVENTORY_FXML, Constants.MAIN_MENU_FXML, "Inventory to Store", "Main Menu");
    }

    public void salesBtnClick(ActionEvent event) {
        navigation.frameNavigation(event, Constants.SALES_FXML, Constants.MAIN_MENU_FXML, "Sales", "Main Menu");
    }

    public void oldSupplierBtnClick(ActionEvent actionEvent) {
        navigation.frameNavigation(actionEvent, Constants.OLD_SUPPLIER_FXML, Constants.MAIN_MENU_FXML, "Old Suppliers", "Main Menu");
    }

    public void paymentBtnClick(ActionEvent actionEvent) {
        oldSupplier.setOpacity(0);
        newSupplier.setOpacity(0);
        productDetailsBtn.setOpacity(0);
        purchaseEntryBtn.setOpacity(0);
        productEntryBtn.setOpacity(0);
        paymentPayBtn.setOpacity(1);
        paymentReceiveBtn.setOpacity(1);
    }

    public void paymentReceiveBtnClick(ActionEvent actionEvent) {
    }

    public void paymentPayBtnClick(ActionEvent actionEvent) {
    }
}

