package com.phar.controller;

import com.jfoenix.controls.JFXDrawer;
import com.phar.extraFunctionality.CFunctions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    private CFunctions forAnchorPane = new CFunctions();

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane mainAnchor;

    private VBox vBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
//            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/com/phar/newSales.fxml"));
            vBox = FXMLLoader.load(getClass().getResource("/com/phar/sideBar.fxml"));
//            mainAnchor.getChildren().add(anchorPane);
        } catch (IOException ex) {
        }
        drawer.setSidePane(vBox);
        if (drawer.isShown()) {
            drawer.close();
            mainAnchor.toFront();
            slideBarBtn.toFront();
        }

        for (Node node : vBox.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    switch (node.getAccessibleText()) {
                        case "vBoxNewSupplier":
                            forAnchorPane.openAnchor(mainAnchor, "/com/phar/newSupplier.fxml", drawer);
//                            drawer.close();
//                            mainAnchor.toFront();
                            break;
                        case "vBoxOldSupplier":
                            forAnchorPane.openAnchor(mainAnchor, "/com/phar/supplierTable.fxml", drawer);
//                            drawer.close();
//                            mainAnchor.toFront();
                            break;
                        case "vBoxNewPurchase":
                            forAnchorPane.openAnchor(mainAnchor, "/com/phar/new_purchase_entry.fxml", drawer);
//                            drawer.close();
//                            mainAnchor.toFront();
                            break;
                        case "vBoxNewProduct":
                            forAnchorPane.openAnchor(mainAnchor, "/com/phar/productEntry.fxml", drawer);
//                            drawer.close();
//                            mainAnchor.toFront();
                            break;
                        case "vBoxProductDetails":
                            forAnchorPane.openAnchor(mainAnchor, "/com/phar/product_table.fxml", drawer);
//                            drawer.close();
//                            mainAnchor.toFront();
                            break;
                        case "vBoxAddtoStore":
                            forAnchorPane.openAnchor(mainAnchor, "/com/phar/inventory.fxml", drawer);
//                            drawer.close();
//                            mainAnchor.toFront();
                            break;
                        case "vBoxSalesEntry":

                        case "vBoxSalesReturn":
                        case "vBoxPurchaseReturn":
                        case "vBoxSupplierPayment":
                        case "vBoxCustomerPayment":
                        case "vBoxPurchaseVsSales":
                            forAnchorPane.openAnchor(mainAnchor, "/com/phar/purchaseVSsales.fxml", drawer);
//                            drawer.close();
                            break;
                        case "vBoxCustomerDetails":
                        case "vBoxRecord":
                            break;
                        default:
                            drawer.close();
                            mainAnchor.toFront();
                    }
                });
            }
        }
    }

    @FXML
    void mouseExit(MouseEvent event) {
        drawer.close();
        mainAnchor.toFront();
        slideBarBtn.toFront();
    }

    @FXML
    private Button slideBarBtn;

    @FXML
    void slideBarBtnAction(ActionEvent event) {
        if (drawer.isShown()) {
            drawer.close();
            mainAnchor.toFront();
            slideBarBtn.toFront();
        } else {
            drawer.open();
            drawer.toFront();
        }
    }
}
