package com.phar.controller;

import com.phar.billing;
import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.AutoGenerator;
import com.phar.extraFunctionality.CFunctions;
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
    private TextField pBatch;
    @FXML
    private TextField qLeftInStore;
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
    @FXML
    private TextField pParty;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private List<String> productList = new ArrayList<String>();
    private List<Float> getAmountValue = new ArrayList<>();
    private List<Sales> productBill = new ArrayList<>();
    private String pIdd;
    private ObservableList<Sales> productTableList = FXCollections.observableArrayList();
    private ObservableList<SalesInfo> salesInfoList = FXCollections.observableArrayList();
    private Double qtyLeft;
    private float newTotal = 0;
    private AutoGenerator generator = new AutoGenerator();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pBillNo.setText(generator.CurrentID("BID"));
        tDate.setValue(LocalDate.now());
        try {
            connection = DatabaseConnection.getConnection();
            String query = "SELECT product_name from store";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                productList.add(resultSet.getString("product_name"));
            }
            pName.getItems().addAll(productList);
            pName.valueProperty().addListener((observable, oldValue, newValue) -> {
                String otherInfo = "SELECT product_id,batch,quantity,mRP, expire FROM store WHERE product_name='" + pName.getValue() + "'";
                resultSet = CFunctions.executeQuery(preparedStatement, connection, otherInfo, resultSet);
                try {
                    while (resultSet.next()) {
                        pIdd = resultSet.getString("product_id");
                        p_id1.setText(pIdd);
                        pBatch.setText(resultSet.getString("batch"));
                        qLeftInStore.setText(resultSet.getString("quantity"));
                        qtyLeft = Double.valueOf(resultSet.getString("quantity"));
                        pmrp.setText(resultSet.getString("mRP"));
                        System.out.println(resultSet.getString("mRP"));
                        pExpire.setText(resultSet.getString("expire"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clickToAddToList(ActionEvent event) {
        counter++;
        Sales s = new Sales();
        s.setProductID(pIdd);
        s.setProductName(pName.getValue());
        s.setProductBatch(pBatch.getText());
        s.setmRp(Double.valueOf(pmrp.getText()));
        s.setProductQuantity(Integer.valueOf(qEntered.getText()));
        s.setExpireDate(pExpire.getText());
        s.setDiscount(Double.valueOf(pDiscount.getText()));
        s.setAmount(Double.valueOf(pAmount.getText()));

        s.setBillNo(pBillNo.getText());
        s.setParty(pParty.getText());
        s.setAddress(pAddress.getText());
        s.setPrescribedBy(pPrescribedBy.getText());
        s.setProductDate(tDate.getValue().toString());

        productTableList.add(s);

        productBill.add(s);

        proId.setCellValueFactory(new PropertyValueFactory<Sales, String>("productID"));
        proName.setCellValueFactory(new PropertyValueFactory<Sales, String>("productName"));
        proBatch.setCellValueFactory(new PropertyValueFactory<Sales, String>("productBatch"));
        proMrp.setCellValueFactory(new PropertyValueFactory<Sales, Double>("mRp"));
        proQuantity.setCellValueFactory(new PropertyValueFactory<Sales, Integer>("productQuantity"));
        proExpDate.setCellValueFactory(new PropertyValueFactory<Sales, String>("expireDate"));
        proAmount.setCellValueFactory(new PropertyValueFactory<Sales, Double>("amount"));
        proDiscount.setCellValueFactory(new PropertyValueFactory<Sales, Double>("discount"));

        pTableStore.setItems(productTableList);
        float total1 = Float.valueOf(pAmount.getText());

        getAmountValue.add(total1);
        newTotal += total1;
        total.setText(String.valueOf(newTotal));

        Double newQty = qtyLeft - Double.valueOf(qEntered.getText());
        String sql1 = "UPDATE store SET quantity='" + newQty + "' WHERE product_name = '" + pName.getValue() + "'";
        System.out.println(sql1);
        try {
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();
            sql1 = "SELECT quantity FROM store WHERE product_name ='" + pName.getValue() + "'";
            preparedStatement = connection.prepareStatement(sql1);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                qLeftInStore.setText(resultSet.getString("quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onClickPrintBill(ActionEvent event) {
        billing.bill(productBill, counter);
    }

    public void onClickSaveButton(ActionEvent actionEvent) {
        SalesInfo si = new SalesInfo();
        si.setSalesBill(pBillNo.getText());
        si.setSalesParty(pParty.getText());
        si.setCustomerAddress(pAddress.getText());
        si.setPrescribedBy(pPrescribedBy.getText());
        si.setProductName(pName.getValue());
        si.setSalesDate(tDate.getValue().toString());
        si.setProductQuantity(Integer.valueOf(qEntered.getText()));
        si.setSalesAmount(Float.valueOf(pAmount.getText()));

        salesInfoList.add(si);

        String query = "INSERT into sales_info(sales_bill, sales_party, customer_address, prescribed_by, product_name, sales_date, sales_amount, product_quantity) VALUES (?,?,?,?,?,?,?,?) ";
        for (SalesInfo salesInfo : salesInfoList) {
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, salesInfo.getSalesBill());
                preparedStatement.setString(2, salesInfo.getSalesParty());
                preparedStatement.setString(3, salesInfo.getCustomerAddress());
                preparedStatement.setString(4, salesInfo.getPrescribedBy());
                preparedStatement.setString(5, salesInfo.getProductName());
                preparedStatement.setString(6, salesInfo.getSalesDate());
                preparedStatement.setFloat(7, salesInfo.getSalesAmount());
                preparedStatement.setInt(8, salesInfo.getProductQuantity());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        CustomAlert alert = new CustomAlert("Info.", "Save Successful");
        alert.withoutHeader();
    }
}
