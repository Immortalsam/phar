package com.phar.controller;

import com.phar.custom.CustomAlert;
import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CustomComboBox;
import com.phar.model.ProductEntry;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.ResourceBundle;

/**
 * Created by Sam on 11/15/2016.
 */

public class PurchaseEntryController implements Initializable {

    Connection conn;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private List<String> supplierList = new ArrayList<String>();
    private ObservableList<ProductEntry> productList = FXCollections.observableArrayList();
    private List<Float> getAmountValue = new ArrayList<>();
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
    private ComboBox supplierSearchName;

    @FXML
    private ComboBox cashCredit;

    @FXML
    private ComboBox vat;

    @FXML
    private TableColumn<ProductEntry, String> supplierId;

    @FXML
    private TableColumn<ProductEntry, String> productId;

    @FXML
    private TableColumn<ProductEntry, String> productName;

    @FXML
    private TableColumn<ProductEntry, String> productBatch;

    @FXML
    private TableColumn<ProductEntry, String> productExpDate;

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
    private TextField sid, pid, pname, pbatch, pexpdate, pcccharge, pqufor, prate, pquantity, pamount, pmrp, bNo;

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
        p.setSupplierId(sid.getText());
        p.setProductId(pid.getText());
        p.setProductName(pname.getText());
        p.setProductBatch(pbatch.getText());
        p.setProductExpDate(pexpdate.getText());
        p.setProductCcCharge(Float.valueOf(pcccharge.getText()));
        p.setProductQuFoR(Integer.valueOf(pqufor.getText()));
        p.setProductRate(Float.valueOf(prate.getText()));
        p.setProductQuantity(Integer.valueOf(pquantity.getText()));
        p.setProductAmount(Float.valueOf(prate.getText()) * Float.valueOf(pquantity.getText()));
        p.setTodayDate(purchaseDate.getValue().toString());
        p.setBillNo(Integer.valueOf(bNo.getText()));
        p.setProductCashCredit(cashCredit.getValue().toString());
        p.setProductVat(vat.getValue().toString());
        p.setProductMrp(Float.valueOf(pmrp.getText()));

        productList.add(p);

        productId.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("productId"));
        supplierId.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("supplierId"));
        productName.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("productName"));
        productBatch.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("productBatch"));
        productExpDate.setCellValueFactory(new PropertyValueFactory<ProductEntry, String>("productExpDate"));
        productCcharge.setCellValueFactory(new PropertyValueFactory<ProductEntry, Float>("productCcCharge"));
        productQcfor.setCellValueFactory(new PropertyValueFactory<ProductEntry, Integer>("productQuFoR"));
        productRate.setCellValueFactory(new PropertyValueFactory<ProductEntry, Float>("productRate"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<ProductEntry, Integer>("productQuantity"));
        productAmount.setCellValueFactory(new PropertyValueFactory<ProductEntry, Float>("productAmount"));
        productMrp.setCellValueFactory(new PropertyValueFactory<ProductEntry, Float>("productMrp"));

        float totalValue = Float.valueOf(prate.getText()) * Float.valueOf(pquantity.getText());


        //loop
        getAmountValue.add(totalValue);
        newTotalValue += totalValue;
        total.setText(String.valueOf(newTotalValue));

        discount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
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
        new CustomComboBox<>(supplierSearchName);
        try
        {
            conn = DatabaseConnection.getConnection();
            String selectQuery = "SELECT supplier_name FROM supplier";
            preparedStatement = conn.prepareStatement(selectQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                supplierList.add(resultSet.getString("supplier_name"));
            }
        } catch (ClassNotFoundException |
                SQLException e)

        {
            e.printStackTrace();
        }
        supplierSearchName.getItems().addAll(supplierList);
        supplierSearchName.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String idQuery = "SELECT supplier_id FROM supplier WHERE supplier_name= '" + supplierSearchName.getValue() + "'";
                System.out.println(idQuery);
                try {
                    preparedStatement = conn.prepareStatement(idQuery);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        sid.setText(resultSet.getString("supplier_id"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });

        pid.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    pid.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }


    @FXML
    void saveToDatabase(ActionEvent event) throws SQLException {

        String insertQuery = "INSERT INTO new_purchase_entry VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        for (ProductEntry p : productList) {
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, p.getProductId());
            preparedStatement.setString(2, p.getSupplierId());
            preparedStatement.setString(3, p.getProductName());
            preparedStatement.setString(4, p.getProductBatch());
            preparedStatement.setString(5, p.getProductExpDate());
            preparedStatement.setFloat(6, p.getProductCcCharge());
            preparedStatement.setInt(7, p.getProductQuFoR());
            preparedStatement.setFloat(8, p.getProductRate());
            preparedStatement.setInt(9, p.getProductQuantity());
            preparedStatement.setFloat(10, p.getProductMrp());
            preparedStatement.setString(11, p.getTodayDate());
            preparedStatement.setString(12, p.getProductCashCredit());
            preparedStatement.setString(13, p.getProductVat());
            preparedStatement.setInt(14, p.getBillNo());
            preparedStatement.executeUpdate();
        }
        supplierSearchName.setDisable(false);
        bNo.setDisable(false);

        CustomAlert alert = new CustomAlert("Insert to database Info", "Save successful");
        alert.withoutHeader();
    }
}