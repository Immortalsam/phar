package com.phar.controller;

import com.phar.extraFunctionality.CustomComboBox;
import com.phar.extraFunctionality.DatabaseOperations;
import com.phar.extraFunctionality.GetTime;
import com.phar.model.PurchaseReturn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Sam on 11/30/2016.
 */
public class PurchaseReturnController implements Initializable {

    @FXML
    private ComboBox<String> pName, pBatch, searchSupplierName;

    @FXML
    private TextField pId, pExpiry, pRate, pQuantity, pCCCharge, pTotal;

    @FXML
    private DatePicker returnDate;

    @FXML
    private TableView<PurchaseReturn> ptTabelView;

    @FXML
    private TableColumn<PurchaseReturn, String> ptName, ptId, ptBatch, ptExpiry;

    @FXML
    private TableColumn<PurchaseReturn, Integer> ptQuantity;

    @FXML
    private TableColumn<PurchaseReturn, Double> ptRate, ptCcharge, ptTotal;

    @FXML
    private Label supplierId;


    @FXML
    private AnchorPane purchaseReturnAnchor;

    private ResultSet resultSet, rs1;
    private List<String> supplierList = new ArrayList<String>();
    private List<String> productList = new ArrayList<String>();
    private List<String> batchList = new ArrayList<>();


    private ObservableList<PurchaseReturn> productListDisplay = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        returnDate.setValue(LocalDate.now());

        //For supplier Search
        new CustomComboBox<>(searchSupplierName);
        new CustomComboBox<>(pName);
        resultSet = DatabaseOperations.simpleSelect("supplier", "supplier_name", "null");
        try {
            while (resultSet.next()) {
                supplierList.add(resultSet.getString("supplier_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        searchSupplierName.getItems().addAll(supplierList);
        searchSupplierName.valueProperty().addListener(((observable, oldValue, newValue) -> {
            pName.getItems().clear();
            productList.clear();
            resultSet = DatabaseOperations.simpleSelect("supplier", "supplier_id", "supplier_name='" + searchSupplierName.getValue() + "'");
            try {
                while (resultSet.next()) {
                    supplierId.setText(resultSet.getString("supplier_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs1 = DatabaseOperations.simpleSelect("new_purchase_entry", "product_name", "supplier_id='" + supplierId.getText() + "'");
            try {
                while (rs1.next()) {
                    productList.add(rs1.getString("product_name"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            pName.getItems().addAll(productList);
            pName.valueProperty().addListener((((observable1, oldValue1, newValue1) -> {
                batchList.clear();
                pBatch.getItems().clear();
                rs1 = DatabaseOperations.simpleSelect("new_purchase_entry", "product_id, product_batch", "product_name='" + pName.getValue() + "'AND product_quantity>=1");
                try {
                    while (rs1.next()) {
                        batchList.add(rs1.getString("product_batch"));
                        pId.setText(rs1.getString("product_id"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                pBatch.getItems().addAll(batchList);
            })));

            pBatch.valueProperty().addListener((((observable1, oldValue1, newValue1) -> {
                if (pBatch.getValue() != null) {
                    rs1 = DatabaseOperations.simpleSelect("new_purchase_entry", "product_rate, product_quantity,product_expdate,product_cccharge", "product_batch='" + pBatch.getValue() + "'");
                    try {
                        while (rs1.next()) {
                            pRate.setText(rs1.getString("product_rate"));
                            pQuantity.setText(rs1.getString("product_quantity"));
                            pExpiry.setText(rs1.getString("product_expdate"));
                            pCCCharge.setText(rs1.getString("product_cccharge"));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            })));
        }));
    }

    public void addBtnClick(ActionEvent actionEvent) {
        PurchaseReturn purchaseReturn = new PurchaseReturn();
        //For Date with time
        GetTime gt = new GetTime();
        purchaseReturn.setReturnDate(String.valueOf(returnDate.getValue()).concat(" ").concat(gt.timeNow()));
        purchaseReturn.setSupplierName(searchSupplierName.getValue());
        purchaseReturn.setProductName(pName.getValue());
        purchaseReturn.setProductId(pId.getText());
        purchaseReturn.setProductBatch(pBatch.getValue());
        purchaseReturn.setProductExpiryDate(pExpiry.getText());
        purchaseReturn.setProductRate(Double.valueOf(pRate.getText()));
        purchaseReturn.setProductQuantity(Integer.valueOf(pQuantity.getText()));
        purchaseReturn.setProductCCCharge(Double.valueOf(pCCCharge.getText()));
        purchaseReturn.setTotalAmount(Double.valueOf(pTotal.getText()));

        productListDisplay.add(purchaseReturn);

        ptName.setCellValueFactory(new PropertyValueFactory<PurchaseReturn, String>("supplierName"));
        ptId.setCellValueFactory(new PropertyValueFactory<PurchaseReturn, String>("productId"));
        ptBatch.setCellValueFactory(new PropertyValueFactory<PurchaseReturn, String>("productBatch"));
        ptExpiry.setCellValueFactory(new PropertyValueFactory<PurchaseReturn, String>("productExpiryDate"));
        ptRate.setCellValueFactory(new PropertyValueFactory<PurchaseReturn, Double>("productRate"));
        ptQuantity.setCellValueFactory(new PropertyValueFactory<PurchaseReturn, Integer>("productQuantity"));
        ptCcharge.setCellValueFactory(new PropertyValueFactory<PurchaseReturn, Double>("productCCCharge"));
        ptTotal.setCellValueFactory(new PropertyValueFactory<PurchaseReturn, Double>("totalAmount"));

        ptTabelView.setItems(productListDisplay);
        searchSupplierName.setDisable(true);
        returnDate.setDisable(true);

        for (Node node : purchaseReturnAnchor.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).clear();
            }
        }
    }

    public void toDatabaseClick(ActionEvent actionEvent) {
        String updateQuery = "UPDATE";
        for (PurchaseReturn purchase : productListDisplay) {

        }
    }
}
