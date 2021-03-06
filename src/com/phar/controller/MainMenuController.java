package com.phar.controller;

/**
 * Created by Sam on 11/10/2016.
 */

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.phar.extraFunctionality.CFunctions;
import com.phar.extraFunctionality.Constants;
import com.phar.extraFunctionality.NavigationHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private NavigationHandler navigation = new NavigationHandler();

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Button purchaseDepartmentBtn, billingBtn, purchaseEntryBtn, productDetailsBtn, productEntryBtn, inventoryBtn, salesBtn, oldSupplier, paymentReceiveBtn, paymentPayBtn, paymentBtn, newSupplier;

    @FXML
    private JFXHamburger menuHam;

    @FXML
    private JFXDrawer drawer;

    private HamburgerSlideCloseTransition btask;
    private CFunctions c = new CFunctions();

//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        try {
//            VBox vBox = FXMLLoader.load(getClass().getResource("/com/phar/sideBar.fxml"));
//            drawer.setSidePane(vBox);
//            for (Node node : vBox.getChildren()) {
//                if (node.getAccessibleText() != null) {
//                    node.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
//                        switch (node.getAccessibleText()) {
//                            case "vNewSupplier":
//                                System.out.println("Supplier");
//                                c.openAnchor(mainAnchorPane, "/com/phar/newSupplier.fxml");
//                                drawer.close();
//                                drawer.setVisible(false);
//                                break;
//                            case "vOldSupplier":
//                                System.out.println("Purchase");
//                                c.openAnchor(mainAnchorPane, "/com/phar/supplierTable.fxml");
//                                drawer.close();
//                                drawer.setVisible(false);
//                                break;
//                            case "vNewSales":
//                                System.out.println("Purchase");
//                                c.openAnchor(mainAnchorPane, "/com/phar/newSales.fxml");
//                                drawer.close();
//                                drawer.setVisible(false);
//                                break;
//
//                            default:
//                                drawer.close();
//                                drawer.setVisible(false);
//                                break;
//                        }
//                    });
//                }
//            }
//
//            btask = new HamburgerSlideCloseTransition(menuHam);
//            btask.setRate(-1);
//            menuHam.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
//                drawer.setVisible(true);
//                btask.setRate(btask.getRate() * -1);
//                btask.play();
//                if (drawer.isShown()) {
//                    drawer.close();
//                } else {
//                    drawer.open();
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    void mouseExitOnDrawer(MouseEvent event) {
        drawer.close();

    }

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

    @FXML
    void onProductsClick(ActionEvent actionEvent) {
        navigation.frameNavigation(actionEvent, "/com/phar/product_table.fxml", Constants.MAIN_MENU_FXML, "Product Table", "Main Menu");

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
        navigation.frameNavigation(actionEvent, "/com/phar/supplierPayment.fxml", Constants.MAIN_MENU_FXML, "Product Table", "Main Menu");

    }

    public void paymentPayBtnClick(ActionEvent actionEvent) {
    }

    public void billingBtnClick(ActionEvent actionEvent) {
    }

    public void purchaseDepartmentBtnClick(ActionEvent actionEvent) {
        productDetailsBtn.setOpacity(1);
        purchaseEntryBtn.setOpacity(1);
        productEntryBtn.setOpacity(1);
        oldSupplier.setOpacity(0);
        newSupplier.setOpacity(0);
        paymentPayBtn.setOpacity(0);
        paymentReceiveBtn.setOpacity(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

