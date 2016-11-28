package com.phar.controller;

import com.phar.Billing;
import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.AutoGenerator;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.extraFunctionality.DatabaseOperations;
import com.phar.model.Sales;
import com.phar.model.SalesInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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
 * Created by Sam on 11/18/2016.
 */

public class SalesController implements Initializable {

    int counter = 0;
    @FXML
    private TextField p_id1;
    @FXML
    private ComboBox<String> pName;

    @FXML
    private ComboBox pBatch;
    @FXML
    private ComboBox<String> searchCustomer, paymentmode;

    @FXML
    private Label qLeftInStore;
    @FXML
    private TextField qEntered;
    @FXML
    private TextField pmrp;
    @FXML
    private TextField pDiscount;
    @FXML
    private TextField pAmount;
    @FXML
    private TextField pExpire;
    @FXML
    private TableColumn<Sales, String> proName;
    @FXML
    private TableColumn<Sales, String> proId;
    @FXML
    private TableColumn<Sales, String> proBatch;
    @FXML
    private TableView<Sales> pTableStore;
    @FXML
    private TableColumn<Sales, Integer> proQuantity;
    @FXML
    private TableColumn<Sales, Double> proMrp;
    @FXML
    private TableColumn<Sales, String> proExpDate;
    @FXML
    private TableColumn<Sales, Double> proDiscount;
    @FXML
    private TableColumn<Sales, Double> proAmount;
    @FXML
    private TextField total;
    @FXML
    private DatePicker tDate;
    @FXML
    private TextField pPrescribedBy;
    @FXML
    private TextField pAddress;
    @FXML
    private TextField pBillNo;

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private List<String> productList = new ArrayList<String>();
    private List<Float> getAmountValue = new ArrayList<>();
    private List<Sales> productBill = new ArrayList<>();
    private List<String> customerList = new ArrayList<String>();
    private List<String> batchList = new ArrayList<>();
    private String pIdd;
    private ObservableList<Sales> productTableList = FXCollections.observableArrayList();
    private ObservableList<SalesInfo> salesInfoList = FXCollections.observableArrayList();
    private Double qtyLeft;
    private float newTotal = 0;
    private AutoGenerator generator = new AutoGenerator();
    private String customer_id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pBillNo.setText(generator.CurrentID("BID"));
        tDate.setValue(LocalDate.now());
        try {
            resultSet = DatabaseOperations.simpleSelect("store", "DISTINCT product_name", "null");
            while (resultSet.next()) {
                productList.add(resultSet.getString("product_name"));
            }
            pName.getItems().addAll(productList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //search customer
        new CustomComboBox<>(searchCustomer);
        resultSet = DatabaseOperations.simpleSelect("customer_info", "customer_name", "null");
        try {
            while (resultSet.next()) {
                customerList.add(resultSet.getString("customer_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        pName.valueProperty().addListener((observable, oldValue, newValue) -> {
            pBatch.getItems().clear();
            batchList.clear();
            qLeftInStore.setText("0");
            resultSet = DatabaseOperations.simpleSelect("store", "product_id,batch", "product_name='" + pName.getValue() + "'");
            try {
                while (resultSet.next()) {
                    pIdd = resultSet.getString("product_id");
                    p_id1.setText(pIdd);
                    batchList.add(resultSet.getString("batch"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            pBatch.getItems().addAll(batchList);
        });

        pBatch.valueProperty().addListener((observable1, oldValue1, newValue1) -> {
            if (pBatch.getValue() != null) {
                resultSet = DatabaseOperations.simpleSelect("store", "quantity,mRP,expire", "product_name='" + pName.getValue() + "' AND batch=" + pBatch.getValue());
                try {
                    while (resultSet.next()) {
                        qLeftInStore.setText(resultSet.getString("quantity"));
                        qtyLeft = Double.valueOf(resultSet.getString("quantity"));
                        pmrp.setText(resultSet.getString("mRP"));
                        pExpire.setText(resultSet.getString("expire"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        searchCustomer.getItems().addAll(customerList);
    }

    @FXML
    void clickToAddToList(ActionEvent event) {
        counter++;
        Sales s = new Sales();
        s.setProductID(pIdd);
        s.setProductName(pName.getValue());
        s.setProductBatch((String) pBatch.getValue());
        s.setmRp(Double.valueOf(pmrp.getText()));
        s.setProductQuantity(Integer.valueOf(qEntered.getText()));
        s.setExpireDate(pExpire.getText());
        s.setDiscount(Double.valueOf(pDiscount.getText()));
        s.setAmount(Double.valueOf(pAmount.getText()) * Integer.valueOf(qEntered.getText()));
        s.setBillNo(pBillNo.getText());
        s.setParty(searchCustomer.getValue());
        s.setPayment(paymentmode.getValue());
        s.setAddress(pAddress.getText());
        s.setPrescribedBy(pPrescribedBy.getText());
        s.setProductDate(tDate.getValue().toString());

        proId.setCellValueFactory(new PropertyValueFactory<Sales, String>("productID"));
        proName.setCellValueFactory(new PropertyValueFactory<Sales, String>("productName"));
        proBatch.setCellValueFactory(new PropertyValueFactory<Sales, String>("productBatch"));
        proMrp.setCellValueFactory(new PropertyValueFactory<Sales, Double>("mRp"));
        proQuantity.setCellValueFactory(new PropertyValueFactory<Sales, Integer>("productQuantity"));
        proExpDate.setCellValueFactory(new PropertyValueFactory<Sales, String>("expireDate"));
        proAmount.setCellValueFactory(new PropertyValueFactory<Sales, Double>("amount"));
        proDiscount.setCellValueFactory(new PropertyValueFactory<Sales, Double>("discount"));

        pTableStore.setItems(productTableList);

        float total1 = Float.valueOf(String.valueOf(s.getAmount()));

        //getAmountValue.add(total1);
        newTotal += total1;
        total.setText(String.valueOf(newTotal));
        s.setTotal(newTotal);
        productTableList.add(s);
        productBill.add(s);

        Double newQty = qtyLeft - Double.valueOf(qEntered.getText());
        String sql1 = "UPDATE store SET quantity='" + newQty + "' WHERE product_name = '" + pName.getValue() + "'";
        System.out.println(sql1);
        try {
//            preparedStatement = connection.prepareStatement(sql1);
//            preparedStatement.executeUpdate();
            DatabaseOperations.simpleUpdate(sql1);
            sql1 = "SELECT quantity FROM store WHERE product_name ='" + pName.getValue() + "'";

            resultSet = DatabaseOperations.simpleSelect("store", "quantity", "product_name ='" + pName.getValue() + "'");
            while (resultSet.next()) {
                qLeftInStore.setText(resultSet.getString("quantity"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickPrintBill(ActionEvent event) {
        Billing.bill(productBill, counter);
    }

    public void onClickSaveButton(ActionEvent actionEvent) {
        SalesInfo si = new SalesInfo();

        resultSet = DatabaseOperations.simpleSelect("customer_info", "customer_id", "customer_name='" + searchCustomer.getValue() + "'");
        try {
            while (resultSet.next()) {
                System.out.println((resultSet.getString("customer_id")));
                si.setCustomerId(resultSet.getString("customer_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        si.setSalesBill(pBillNo.getText());
        si.setSalesParty(searchCustomer.getValue());
        si.setCustomerAddress(pAddress.getText());
        si.setPrescribedBy(pPrescribedBy.getText());
        si.setProductName(pName.getValue());
        si.setSalesDate(tDate.getValue().toString());
        si.setProductQuantity(Integer.valueOf(qEntered.getText()));
        si.setSalesAmount(Float.valueOf(pAmount.getText()));

        salesInfoList.add(si);

        String query = "INSERT into sales_info(customer_id, sales_bill, sales_party, customer_address, prescribed_by, product_name, sales_date, sales_amount, product_quantity) VALUES (?,?,?,?,?,?,?,?,?) ";
        for (SalesInfo salesInfo : salesInfoList) {
            try {
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);
                System.out.println("THIS:" + salesInfo.getCustomerId());
                preparedStatement.setString(1, salesInfo.getCustomerId());
                preparedStatement.setString(2, salesInfo.getSalesBill());
                preparedStatement.setString(3, salesInfo.getSalesParty());
                preparedStatement.setString(4, salesInfo.getCustomerAddress());
                preparedStatement.setString(5, salesInfo.getPrescribedBy());
                preparedStatement.setString(6, salesInfo.getProductName());
                preparedStatement.setString(7, salesInfo.getSalesDate());
                preparedStatement.setFloat(8, salesInfo.getSalesAmount());
                preparedStatement.setInt(9, salesInfo.getProductQuantity());
                preparedStatement.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        CustomAlert alert = new CustomAlert("Info.", "Save Successful");
        alert.withoutHeader();
    }
}
