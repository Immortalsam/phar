package com.phar.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.phar.Billing;
import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.AutoGenerator;
import com.phar.extraFunctionality.CFunctions;
import com.phar.extraFunctionality.DatabaseOperations;
import com.phar.extraFunctionality.GetTime;
import com.phar.interfaceImplement.CustomerBillImplement;
import com.phar.interfaceImplement.CustomerInterfaceImplement;
import com.phar.model.Bill;
import com.phar.model.CustomerBill;
import com.phar.model.CustomerPayment;
import com.phar.model.NewSales;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Sam on 12/5/2016.
 */
public class SalesController implements Initializable {

    @FXML
    private JFXDatePicker tDate;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXTextField sBillNo, sPrescribedBy, sTotal, sAddress, sProductId, sExpireDate, sMrp, sQuantityEntered, sAmount, sQuantityLeft, sNetTotal, sDiscount, cashReceived, sReturn;

    @FXML
    private JFXComboBox<String> sProductName, sParty, sBatch, sPaymentMode;

    @FXML
    private TableColumn<NewSales, String> tProductName, tProductId, tBatch, tExpireDate;

    @FXML
    private TableColumn<NewSales, Double> tMrp, tAmount;

    @FXML
    private TableColumn<NewSales, Integer> tQuantity;

    @FXML
    private JFXTextField rounding, finalamount;

    @FXML
    private TableView<NewSales> pTable;

    private Stage primaryStage;
    private Parent root;

    public static int getCounter() {
        return counter;
    }

    static int counter = 0;

    private AutoGenerator generator = new AutoGenerator();
    private ResultSet resultSet;
    private List<String> productList = new ArrayList<>();
    private List<String> customerList = new ArrayList<>();
    private List<String> batchList = new ArrayList<>();
    private static ObservableList<NewSales> salesInfoList = FXCollections.observableArrayList();

    public static ObservableList<NewSales> getSalesInfoList() {
        return salesInfoList;
    }

    private List<NewSales> productBill = new ArrayList<>();
    private ObservableList<NewSales> productDetailsTableList = FXCollections.observableArrayList();
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");
    private String pIdd;
    private CustomerBill cb;
    private NewSales ns;
    private Double newTotal = 0.0;
    private Double qtyLeft;
    private Connection connection;
    private PreparedStatement preparedStatement;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tDate.setValue(LocalDate.now());
        CFunctions.restrictTxtField(sQuantityEntered, "[0-9]*");
        sBillNo.setText(generator.CurrentID("BID"));

        try {
            resultSet = DatabaseOperations.simpleSelect("store", "DISTINCT product_name", "null");
            while (resultSet.next()) {
                productList.add(resultSet.getString("product_name"));
            }
            sProductName.getItems().addAll(productList);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultSet = DatabaseOperations.simpleSelect("customer_info", "customer_name", "null");
        try {
            while (resultSet.next()) {
                customerList.add(resultSet.getString("customer_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sParty.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (sParty.getValue().equals("CASH_SALES")) {
                sPaymentMode.setDisable(true);
            } else {
                sAddress.setDisable(false);
                sPrescribedBy.setDisable(false);
                sPaymentMode.setDisable(false);
            }
        });

        sProductName.valueProperty().addListener((observable, oldValue, newValue) -> {
            sBatch.getItems().clear();
            batchList.clear();
            sQuantityLeft.setText("0");
            resultSet = DatabaseOperations.simpleSelect("store", "product_id,batch", "product_name='" + sProductName.getValue() + "' AND quantity>=1");
            try {
                while (resultSet.next()) {
                    pIdd = resultSet.getString("product_id");
                    sProductId.setText(pIdd);
                    batchList.add(resultSet.getString("batch"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sBatch.getItems().addAll(batchList);
        });

        cashReceived.textProperty().addListener((observable, oldValue, newValue) -> {

            if (cashReceived.getText().trim().isEmpty() || cashReceived.getText() == null) {
                cashReceived.setText("");
                sReturn.setText("0.00");
            } else {
                Double d = Math.abs(Double.valueOf(sNetTotal.getText()) - Double.valueOf(cashReceived.getText()));
                sReturn.setText(d.toString());
            }

        });

        sDiscount.textProperty().addListener((observable, oldValue, newValue) -> {
            if (sDiscount.getText().trim().isEmpty() || sDiscount.getText() == null) {
                String rnTotal = decimalFormat.format(newTotal.toString());
                sNetTotal.setText(rnTotal);
            } else {
                Double dis = ((Double.valueOf(sDiscount.getText())) / 100) * (Double.valueOf(sTotal.getText()));
                Double d = Math.abs(Double.valueOf(sTotal.getText()) - dis);
                String rdTotal = decimalFormat.format(d);
                sNetTotal.setText(rdTotal);
                double x = Double.valueOf(sNetTotal.getText()) - Math.floor(Double.valueOf(sNetTotal.getText()));
                String rx = decimalFormat.format(x);
                rounding.setText(String.valueOf(rx));
                double y = Math.round(Double.parseDouble(rdTotal));
                finalamount.setText(String.valueOf(y));
            }
        });

        sBatch.valueProperty().addListener((observable1, oldValue1, newValue1) -> {
            if (sBatch.getValue() != null) {
                resultSet = DatabaseOperations.simpleSelect("store", "quantity,mRP,expire", "product_name='" + sProductName.getValue() + "' AND batch='" + sBatch.getValue() + "'");
                try {
                    while (resultSet.next()) {
                        sQuantityLeft.setText(resultSet.getString("quantity"));
                        qtyLeft = Double.valueOf(resultSet.getString("quantity"));
                        sMrp.setText(resultSet.getString("mRP"));
                        sExpireDate.setText(resultSet.getString("expire"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        sParty.getItems().addAll(customerList);

        sQuantityEntered.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (sQuantityEntered.getText() == null || sQuantityEntered.getText().trim().isEmpty() || sQuantityEntered.getText().startsWith("0")) {
                    sAmount.setText("0");

                } else {
                    double tempAmt = (Double.valueOf(sMrp.getText()) * Double.valueOf(sQuantityEntered.getText()));
                    sAmount.setText(decimalFormat.format(tempAmt));
                }
                if (Integer.valueOf(sQuantityEntered.getText()) > Integer.valueOf(sQuantityEntered.getText())) {
                    sQuantityEntered.setText(sQuantityLeft.getText());
                    sQuantityEntered.getStyleClass().add("redd");
                }
            } catch (NumberFormatException ex) {
                sQuantityEntered.getStyleClass().add("redd");
            }
        });
    }

    @FXML
    void onClickAddButton(ActionEvent event) {
        counter++;
        ns = new NewSales();
        cb = new CustomerBill();

        resultSet = DatabaseOperations.simpleSelect("customer_info", "customer_id", "customer_name='" + sParty.getValue() + "'");
        try {
            while (resultSet.next()) {
                ns.setCustomerId(resultSet.getString("customer_id"));
                cb.setCustomerId(resultSet.getString("customer_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ns.setProductID(pIdd);
        //For date with time
        GetTime gt = new GetTime();
        ns.setProductDate(tDate.getValue().toString().concat(" ").concat(gt.timeNow()));
        ns.setProductName(sProductName.getValue());
        ns.setProductBatch(sBatch.getValue());
        ns.setMrp(Double.valueOf(sMrp.getText()));
        ns.setProductQuantity(Integer.valueOf(sQuantityEntered.getText()));
        ns.setExpireDate(sExpireDate.getText());
        ns.setSalesAmount(Double.valueOf(sAmount.getText()));
        ns.setBillNo(sBillNo.getText());
        ns.setParty(sParty.getValue());
        ns.setPayment(sPaymentMode.getValue());
        ns.setAddress(sAddress.getText());
        ns.setPrescribedBy(sPrescribedBy.getText());
        salesInfoList.add(ns);

        tProductId.setCellValueFactory(new PropertyValueFactory<NewSales, String>("productID"));
        tProductName.setCellValueFactory(new PropertyValueFactory<NewSales, String>("productName"));
        tBatch.setCellValueFactory(new PropertyValueFactory<NewSales, String>("productBatch"));
        tExpireDate.setCellValueFactory(new PropertyValueFactory<NewSales, String>("expireDate"));
        tMrp.setCellValueFactory(new PropertyValueFactory<NewSales, Double>("mrp"));
        tQuantity.setCellValueFactory(new PropertyValueFactory<NewSales, Integer>("productQuantity"));
        tAmount.setCellValueFactory(new PropertyValueFactory<NewSales, Double>("salesAmount"));

        pTable.setItems(productDetailsTableList);

        Double allTotal = Double.valueOf(ns.getSalesAmount());
        newTotal += allTotal;
        sTotal.setText(newTotal.toString());
        ns.setTotal(newTotal);
        productDetailsTableList.add(ns);
        productBill.add(ns);

        Double newQty = qtyLeft - Double.valueOf(sQuantityEntered.getText());
        String sql1 = "UPDATE store SET quantity='" + newQty + "' WHERE product_name = '" + sProductName.getValue() + "' AND batch='" + sBatch.getValue() + "'";
        System.out.println(sql1);
        try {
//            preparedStatement = connection.prepareStatement(sql1);
//            preparedStatement.executeUpdate();
            DatabaseOperations.simpleUpdate(sql1);
            sql1 = "SELECT quantity FROM store WHERE product_name ='" + sProductName.getValue() + "'";

            resultSet = DatabaseOperations.simpleSelect("store", "quantity", "product_name ='" + sProductName.getValue() + "' AND batch='" + sBatch.getValue() + "'");
            while (resultSet.next()) {
                sQuantityLeft.setText(resultSet.getString("quantity"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        sAmount.setText("");
        sQuantityEntered.setText("");
    }

    @FXML
    void onClickPrintButton(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/com/phar/billing.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickSaveButton(ActionEvent event) {
        //For customer bill
        cb.setCustomerBillNo(sBillNo.getText());
        //For Date with time
        GetTime gt = new GetTime();
        cb.setSalesDate(tDate.getValue().toString().concat(" ").concat(gt.timeNow()));
        cb.setDiscount(Double.valueOf(sDiscount.getText()));
        cb.setTotalAmount(Double.valueOf(finalamount.getText()));

        CustomerBillImplement cbi = new CustomerBillImplement();
        cbi.addCustomerBill(cb);

        generator.NewID("BID");

        String query = "INSERT into sales_info(customer_id, sales_bill, sales_party, customer_address, prescribed_by, product_name, sales_date, sales_amount, product_quantity,product_batch,payment_mode) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        for (NewSales salesInfo : salesInfoList) {
            try {
                connection = DatabaseConnection.getConnection();
                preparedStatement = connection.prepareStatement(query);
                System.out.println("THIS:" + salesInfo.getCustomerId());
                preparedStatement.setString(1, salesInfo.getCustomerId());
                preparedStatement.setString(2, salesInfo.getBillNo());
                preparedStatement.setString(3, salesInfo.getParty());
                preparedStatement.setString(4, salesInfo.getAddress());
                preparedStatement.setString(5, salesInfo.getPrescribedBy());
                preparedStatement.setString(6, salesInfo.getProductName());
                preparedStatement.setString(7, salesInfo.getProductDate());
                preparedStatement.setDouble(8, salesInfo.getSalesAmount());
                preparedStatement.setInt(9, salesInfo.getProductQuantity());
                preparedStatement.setString(10, salesInfo.getProductBatch());
                preparedStatement.setString(11, salesInfo.getPayment());
                preparedStatement.executeUpdate();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (sParty.getValue().toString() != "CASH_SALES" && sPaymentMode.getValue().toString().equals("Cash")) {
            System.out.println("true");
            CustomerPayment cp = new CustomerPayment();
            cp.setCustomerId(ns.getCustomerId());
            cp.setAmtPaid(Float.valueOf(sNetTotal.getText()));
            cp.setPaymentDate(tDate.getValue().toString());

            CustomerInterfaceImplement cii = new CustomerInterfaceImplement();

            if (cii.addCustomerPayment(cp)) {
                CustomAlert alert1 = new CustomAlert("Info", "Payment Also saved.");
                alert1.withoutHeader();
            }
        }
    }
}
