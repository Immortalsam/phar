package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CFunctions;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.extraFunctionality.DateFormatter;
import com.phar.interfaceImplement.InventoryImplement;
import com.phar.model.ProductEntry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Sam on 11/15/2016.
 */

public class PurchaseEntryController implements Initializable {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private List<String> supplierList = new ArrayList<String>();
    private List<Float> getAmountValue = new ArrayList<>();
    private ObservableList<ProductEntry> productList = FXCollections.observableArrayList();

    private float newTotalValue = 0;
    private float newDiscountAmount = 0;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<ProductEntry> purchaseTable;

    @FXML
    private Label fiscalYear;

    @FXML
    private DatePicker purchaseDate;

    @FXML
    private ComboBox<String> supplierSearchName;

    @FXML
    private ComboBox cashCredit;

    @FXML
    private ComboBox vat;

    @FXML
    private TableColumn<ProductEntry, String> supplierId, productId, productName, productBatch, productExpDate;

    @FXML
    private TableColumn<ProductEntry, Float> productCcharge;

    @FXML
    private TableColumn<ProductEntry, Integer> productQcfor;

    @FXML
    private TableColumn<ProductEntry, Float> productRate;

    @FXML
    private TableColumn<ProductEntry, Integer> productQuantity;

    @FXML
    private TableColumn<ProductEntry, Float> productAmount;

    @FXML
    private TableColumn<ProductEntry, Float> productMrp;

    // For adding

    @FXML
    private TextField sid, pid, pname, pbatch, pcccharge, pqufor, prate, pquantity, fisYear, pmrp, bNo;

    //ComboBox for Date
    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private ComboBox<String> yearComboBox;

    @FXML
    private TextField total;

    @FXML
    private TextField discount;

    @FXML
    private TextField netTotal;


    //Default Constructor
    public PurchaseEntryController() throws SQLException, ClassNotFoundException {


    }

    @FXML
    private void addButton(ActionEvent e) {
        ProductEntry p = new ProductEntry();
        p.setFisalYear(fisYear.getText());
        p.setSupplierId(sid.getText());
        p.setProductId(pid.getText());
        p.setProductName(pname.getText());
        p.setProductBatch(pbatch.getText());
      //  p.setProductExpDate(pexpdate.getText());
        p.setProductCcCharge(Float.valueOf(pcccharge.getText()));
        p.setProductQuFoR(Integer.valueOf(pqufor.getText()));
        p.setProductRate(Float.valueOf(prate.getText()));
        p.setProductQuantity(Integer.valueOf(pquantity.getText()));
        p.setProductAmount(Float.valueOf(prate.getText()) * Float.valueOf(pquantity.getText()));
        p.setTodayDate(purchaseDate.getValue().toString());
        p.setBillNo(bNo.getText());
        p.setProductCashCredit(cashCredit.getValue().toString());
        p.setProductVat(vat.getValue().toString());
        p.setProductMrp(Float.valueOf(pmrp.getText()));
        p.setExpYear(yearComboBox.getValue());
        p.setExpMonth(monthComboBox.getValue());
        p.setExpCombined(yearComboBox.getValue().concat(" / ").concat(monthComboBox.getValue().toUpperCase()));

        productList.add(p);

        productId.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("productId"));
        supplierId.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("supplierId"));
        productName.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("productName"));
        productBatch.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("productBatch"));
    //    productExpDate.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("productExpDate"));
        productCcharge.setCellValueFactory(new PropertyValueFactory<ProductEntry, Float>("productCcCharge"));
        productQcfor.setCellValueFactory(new PropertyValueFactory<ProductEntry, Integer>("productQuFoR"));
        productRate.setCellValueFactory(new PropertyValueFactory<ProductEntry, Float>("productRate"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<ProductEntry, Integer>("productQuantity"));
        productAmount.setCellValueFactory(new PropertyValueFactory<ProductEntry, Float>("productAmount"));
        productMrp.setCellValueFactory(new PropertyValueFactory<ProductEntry, Float>("productMrp"));
        productExpDate.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("expCombined"));

        float totalValue = Float.valueOf(prate.getText()) * Float.valueOf(pquantity.getText());

        //loop
        getAmountValue.add(totalValue);
        newTotalValue += totalValue;
        total.setText(String.valueOf(newTotalValue));
        netTotal.setText(String.valueOf(newTotalValue));
        discount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (discount.getText() == null || discount.getText().trim().isEmpty()) {
                    netTotal.setText(String.valueOf(newTotalValue));
                }
                float dis = Float.valueOf(discount.getText());
                newDiscountAmount = newTotalValue - dis;
                //discount
                netTotal.setText(String.valueOf(newDiscountAmount));
            }
        });

        purchaseTable.setItems(productList);
        supplierSearchName.setDisable(true);
        sid.setDisable(true);
        bNo.setDisable(true);
        //for clearing filed
        for (Node node : anchorPane.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).clear();
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        monthComboBox.getItems().addAll("Jan","Feb","Mar","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
        monthComboBox.setValue("Jan");
        monthComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            monthComboBox.setValue(newValue);
        }));


        yearComboBox.getItems().addAll("2017","2018","2019","2020","2021","2022","2023");
        yearComboBox.setValue("2017");
        yearComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            yearComboBox.setValue(newValue);
        }));


        DateFormatter.dateFormatterForDatePicker(purchaseDate);
        purchaseDate.setValue(DateFormatter.NOW_LOCAL_DATE());

        new CustomComboBox<>(supplierSearchName);
        try {
            connection = DatabaseConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        String selectQuery = "SELECT supplier_name FROM supplier";
        resultSet = CFunctions.executeQuery(preparedStatement, connection, selectQuery, resultSet);
        try {
            while (resultSet.next()) {
                supplierList.add(resultSet.getString("supplier_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        supplierSearchName.getItems().addAll(supplierList);
        supplierSearchName.valueProperty().addListener((observable, oldValue, newValue) -> {
            String idQuery = "SELECT supplier_id FROM supplier WHERE supplier_name= '" + supplierSearchName.getValue() + "'";
            System.out.println(idQuery);
            resultSet = CFunctions.executeQuery(preparedStatement, connection, idQuery, resultSet);
            try {
                while (resultSet.next()) {
                    sid.setText(resultSet.getString("supplier_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }


    @FXML
    void saveToDatabase(ActionEvent event) throws SQLException {
        InventoryImplement inventoryImplement = new InventoryImplement();

        String insertQuery = "INSERT INTO new_purchase_entry VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        for (ProductEntry p : productList) {
            inventoryImplement.addtoDb(p);
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, p.getFisalYear());
            preparedStatement.setString(3, p.getProductId());
//            System.out.println(p.getSupplierId());
            preparedStatement.setString(4, p.getSupplierId());
            preparedStatement.setString(5, p.getProductName());
            preparedStatement.setString(6, p.getProductBatch());
            preparedStatement.setString(7, p.getExpCombined());
            preparedStatement.setFloat(8, p.getProductCcCharge());
            preparedStatement.setInt(9, p.getProductQuFoR());
            preparedStatement.setFloat(10, p.getProductRate());
            preparedStatement.setInt(11, p.getProductQuantity());
            preparedStatement.setFloat(12, p.getProductMrp());
            preparedStatement.setString(13, p.getTodayDate());
            preparedStatement.setString(14, p.getProductCashCredit());
            preparedStatement.setString(15, p.getProductVat());
            preparedStatement.setString(16, p.getBillNo());
            preparedStatement.executeUpdate();

        }
        supplierSearchName.setDisable(false);
        bNo.setDisable(false);

        CustomAlert alert = new CustomAlert("Insert to database Info", "Save successful");
        alert.withoutHeader();
    }
}