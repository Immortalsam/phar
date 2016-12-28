package com.phar.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.extraFunctionality.DatabaseOperations;
import com.phar.model.PurchaseReturn;
import com.phar.model.PurchaseReturnNew;
import com.phar.model.Sales;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.transform.Result;
import java.net.InterfaceAddress;
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
 * Created by Sam on 12/26/2016.
 */
public class PurchaseReturnNewController implements Initializable {

    @FXML
    private JFXDatePicker tDate;

    @FXML
    private JFXComboBox<String> sname, pName,pBatch;

    @FXML
    private JFXTextField  qReturned, qPurchased, qRate,ccharge, qtotal;

    @FXML
    private Label supplierId, qLeftInStore, qLeftInInventory;

    @FXML
    private TableColumn<PurchaseReturnNew, String> tProductName, tBatch;

    @FXML
    private TableColumn<PurchaseReturnNew, Integer> tReturned;

    @FXML
    private TableColumn<PurchaseReturnNew, Double> tCCCharge,tTotal;

    @FXML
    private TableView<PurchaseReturnNew> tTableView;


    private ResultSet resultSet, rs1;
    private List<String> supplierList = new ArrayList<String>();
    private List<String> productList = new ArrayList<String>();
    private List<String> batchList = new ArrayList<>();
    private ObservableList<PurchaseReturnNew> productListDisplay = FXCollections.observableArrayList();
    PurchaseReturnNew purchaseReturn = new PurchaseReturnNew();
    private Double rq = 0.0;
    private Double total = 0.0;
    private Connection connection;

    public PurchaseReturnNewController() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            tDate.setValue(LocalDate.now());

        new CustomComboBox<>(sname);
        new CustomComboBox<>(pName);
        resultSet = DatabaseOperations.simpleSelect("supplier", "supplier_name", "null");
        try {
            while (resultSet.next()) {
                supplierList.add(resultSet.getString("supplier_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sname.getItems().addAll(supplierList);
        sname.valueProperty().addListener(((observable, oldValue, newValue) -> {
            pName.getItems().clear();
            productList.clear();
            resultSet = DatabaseOperations.simpleSelect("supplier", "supplier_id", "supplier_name='" + sname.getValue() + "'");
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
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                pBatch.getItems().addAll(batchList);
                pBatch.valueProperty().addListener((((observable2, oldValue2, newValue2) -> {
                    if (pBatch.getValue() != null) {
                        rs1 = DatabaseOperations.simpleSelect("new_purchase_entry", "product_rate, product_quantity,product_cccharge", "product_batch='" + pBatch.getValue() + "'");
                        try {
                            while (rs1.next()) {
                                qRate.setText(rs1.getString("product_rate"));
                                qPurchased.setText(rs1.getString("product_quantity"));
                                ccharge.setText(rs1.getString("product_cccharge"));
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        qLeftInStore.setText("");
                        resultSet = DatabaseOperations.simpleSelect("store","quantity,product_name,batch", "batch='" + pBatch.getValue()+ "'AND product_name='" + pName.getValue()+ "'");
                        try{
                            while (resultSet.next()){
                                qLeftInStore.setText(resultSet.getString("quantity"));
                            }
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                        qLeftInInventory.setText("");
                        resultSet = DatabaseOperations.simpleSelect("inventory","quantity,product_name,batch","batch='" + pBatch.getValue()+ "'AND product_name='" + pName.getValue() + "'");
                        try{
                            while (resultSet.next()){
                                qLeftInInventory.setText(resultSet.getString("quantity"));
                            }
                        }catch (SQLException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    qReturned.textProperty().addListener(((observable3, oldValue3, newValue3) -> {
                        if(qReturned.getText() == null || qReturned.getText().trim().isEmpty()){
                            qReturned.setText("");
                        }else
                        {
                            rq = Double.valueOf(qRate.getText()) * Integer.valueOf(qReturned.getText());
                            total = rq - (Double.valueOf(ccharge.getText())/100)*rq;
                            qtotal.setText(String.valueOf(total));
                        }
                    }));

                    Integer InventorySumStore = Integer.valueOf(qLeftInInventory.getText()) +  Integer.valueOf(qLeftInStore.getText());
                    System.out.println(InventorySumStore);
                    qReturned.textProperty().addListener(((observable3, oldValue3, newValue3) -> {
                        if(qReturned.getText() == null || qReturned.getText().trim().isEmpty()){
                            qReturned.setText("");
                        }
                        else if(Integer.valueOf(qReturned.getText()) > InventorySumStore){
                            qReturned.setText(InventorySumStore.toString());
                        }
                    }));
                })));
            })));
        }));
    }

    @FXML
    void onClickAdd(ActionEvent event) {
        purchaseReturn.setReturnDate(String.valueOf(tDate.getValue()));
        purchaseReturn.setProductName(pName.getValue());
        purchaseReturn.setProductBatch(pBatch.getValue());
        purchaseReturn.setQuantityReturned(Integer.valueOf(qReturned.getText()));
        purchaseReturn.setCcCharge(Double.valueOf(ccharge.getText()));
        purchaseReturn.setTotal(Double.valueOf(total));
        purchaseReturn.setSupplierId(supplierId.getText());

        productListDisplay.add(purchaseReturn);

        tProductName.setCellValueFactory(new PropertyValueFactory<PurchaseReturnNew, String>("productName"));
        tBatch.setCellValueFactory(new PropertyValueFactory<PurchaseReturnNew, String>("productBatch"));
        tReturned.setCellValueFactory(new PropertyValueFactory<PurchaseReturnNew, Integer>("quantityReturned"));
        tCCCharge.setCellValueFactory(new PropertyValueFactory<PurchaseReturnNew, Double>("ccCharge"));
        tTotal.setCellValueFactory(new PropertyValueFactory<PurchaseReturnNew, Double>("total"));

        tTableView.setItems(productListDisplay);
        sname.setDisable(true);
    }

    @FXML
    void onClickConfirm(ActionEvent event) {
        String query = "INSERT into purchase_return(return_date,product_name,supplier_id,product_batch,quantity_returned, total) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, purchaseReturn.getReturnDate());
            preparedStatement.setString(2, purchaseReturn.getProductName());
            preparedStatement.setString(3, purchaseReturn.getSupplierId());
            preparedStatement.setString(4, purchaseReturn.getProductBatch());
            preparedStatement.setInt(5,    purchaseReturn.getQuantityReturned());
            preparedStatement.setDouble(6, purchaseReturn.getTotal());
            if(preparedStatement.executeUpdate() == 1){
                CustomAlert ca = new CustomAlert("Purchase Return Information!", "Success");
                ca.withoutHeader();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(Integer.valueOf(qReturned.getText()) <= Integer.valueOf(qLeftInInventory.getText())){
            String updateQuantity = "UPDATE inventory SET quantity ='" + (Integer.valueOf(qLeftInInventory.getText()) - Integer.valueOf(qReturned.getText()))  + "'";
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuantity);
                preparedStatement.executeUpdate();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }else{
            Integer qInInventory =Integer.valueOf(qLeftInInventory.getText());
            Integer qleft = Integer.valueOf(qLeftInStore.getText()) - (Integer.valueOf(qReturned.getText()) - qInInventory);
            String updateQuantityInInventory = "UPDATE inventory SET quantity = 0";
            String updateQuantityInStore = "UPDATE store SET quantity ='" + qleft + "'";
            try{
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuantityInInventory);
                preparedStatement.executeUpdate();
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuantityInStore);
                preparedStatement.executeUpdate();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
