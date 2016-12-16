package com.phar.controller;

import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.extraFunctionality.DatabaseOperations;
import com.phar.extraFunctionality.DateFormatter;
import com.phar.interfaceImplement.BillInterfaceImplement;
import com.phar.interfaceImplement.InventoryImplement;
import com.phar.model.Bill;
import com.phar.model.ProductEntry;
import com.phar.testCases.TestCases;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private ComboBox<String> supplierSearchName, monthComboBox, yearComboBox;

    @FXML
    private ComboBox cashCredit, vat, pname;

    @FXML
    private TableColumn<ProductEntry, String> supplierId, productId, productName, productBatch, productExpDate;

    @FXML
    private TableColumn<ProductEntry, Float> productCcharge, productRate, productAmount, productMrp;

    @FXML
    private TableColumn<ProductEntry, Integer> productQcfor, productQuantity;

    @FXML
    private TextField sid, pid, pbatch, pcccharge, pqufor, prate, pquantity, fisYear, pmrp, bNo;

    @FXML
    private TextField total, discount, netTotal;

    private List<String> productNameList = new ArrayList<String>();

    //Default Constructor
    public PurchaseEntryController() throws SQLException, ClassNotFoundException {
    }

    @FXML
    private void addButton(ActionEvent e) {
        ProductEntry p = new ProductEntry();

        p.setFisalYear(fisYear.getText());
        p.setSupplierId(sid.getText());
        p.setProductId(pid.getText());
        p.setProductName(pname.getValue().toString());
        p.setProductBatch(pbatch.getText());
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
        p.setExpCombined(yearComboBox.getValue().concat("/").concat(monthComboBox.getValue().toUpperCase()));

        productList.add(p);

        productId.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("productId"));
        supplierId.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("supplierId"));
        productName.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("productName"));
        productBatch.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("productBatch"));
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
        discount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (discount.getText() == null || discount.getText().trim().isEmpty()) {
                netTotal.setText(String.valueOf(newTotalValue));
            }
            float dis = Float.valueOf(discount.getText());
            newDiscountAmount = newTotalValue - dis;
            //discount
            netTotal.setText(String.valueOf(newDiscountAmount));
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

        TestCases.TabAndCheck(pquantity);

        monthComboBox.getItems().addAll("Jan", "Feb", "Mar", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        monthComboBox.setValue("Jan");
        monthComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            monthComboBox.setValue(newValue);
        }));


        yearComboBox.getItems().addAll("2017", "2018", "2019", "2020", "2021", "2022", "2023");
        yearComboBox.setValue("2017");
        yearComboBox.valueProperty().addListener(((observable, oldValue, newValue) -> {
            yearComboBox.setValue(newValue);
        }));


        DateFormatter.dateFormatterForDatePicker(purchaseDate);
        purchaseDate.setValue(DateFormatter.NOW_LOCAL_DATE());

        new CustomComboBox<>(supplierSearchName);
//        try {
//            connection = DatabaseConnection.getConnection();
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//        String selectQuery = "SELECT supplier_name FROM supplier";
//        resultSet = CFunctions.executeQuery(preparedStatement, connection, selectQuery, resultSet);
        resultSet = DatabaseOperations.simpleSelect("supplier", "supplier_name", "null");
        try {
            while (resultSet.next()) {
                supplierList.add(resultSet.getString("supplier_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        supplierSearchName.getItems().addAll(supplierList);
        supplierSearchName.valueProperty().addListener((observable, oldValue, newValue) -> {
//            String idQuery = "SELECT supplier_id FROM supplier WHERE supplier_name= '" + supplierSearchName.getValue() + "'";
//            System.out.println(idQuery);
//            resultSet = CFunctions.executeQuery(preparedStatement, connection, idQuery, resultSet);
            resultSet = DatabaseOperations.simpleSelect("supplier", "supplier_id", "supplier_name='" + supplierSearchName.getValue() + "'");
            try {
                while (resultSet.next()) {
                    sid.setText(resultSet.getString("supplier_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        new CustomComboBox<>(pname);
        listingCompanyName();

    }

    private void listingCompanyName() {
        productNameList.clear();
        try {
            resultSet = DatabaseOperations.simpleSelect("new_product_entry", "product_name", "null");
            while (resultSet.next()) {
                productNameList.add(resultSet.getString("product_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pname.getItems().addAll(productNameList);
        pname.valueProperty().addListener((observable, oldValue, newValue) -> {
            resultSet = DatabaseOperations.simpleSelect("new_product_entry", "product_id", "product_name='" + pname.getValue() + "'");
            try {
                while (resultSet.next()) {
                    pid.setText(resultSet.getString("product_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    void saveToDatabase(ActionEvent event) throws SQLException {
        Bill bill = new Bill();
        bill.setPurchaseDate(purchaseDate.getValue().toString());
        bill.setBillNo(bNo.getText());
        bill.setSupplierId(sid.getText());
        bill.setTotalAmount(Float.valueOf(netTotal.getText()));

        BillInterfaceImplement billInterfaceImplement = new BillInterfaceImplement();
        billInterfaceImplement.addBill(bill);

        InventoryImplement inventoryImplement = new InventoryImplement();
        String insertQuery = "INSERT INTO new_purchase_entry VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


//        CustomAlert alert = new CustomAlert("Insert to database Info", "Save successful");
//        alert.withoutHeader();

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Please Choose an Option.");
        alert1.setHeaderText(null);
        alert1.setContentText("Please Choose Payment to make payment right now. Press Save for credit");
        ButtonType makePayment = new ButtonType("Make Payment");
        ButtonType saveToDb = new ButtonType("Save to Database");

        alert1.getButtonTypes().clear();
        alert1.getButtonTypes().addAll(makePayment,saveToDb);
        Optional<ButtonType> result = alert1.showAndWait();
        if (result.get() == makePayment) {
            System.out.println("Payment Made");
        } else if (result.get() == saveToDb) {
            System.out.println("Saved to DB");
            for (ProductEntry p : productList) {
                try {
                    connection = DatabaseConnection.getConnection();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setInt(1, 0);
                preparedStatement.setString(2, p.getFisalYear());
                preparedStatement.setString(3, p.getProductId());
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
                inventoryImplement.addtoDb(p);
            }
            supplierSearchName.setDisable(false);
            bNo.setDisable(false);
        }

    }
}