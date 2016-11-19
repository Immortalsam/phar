package com.phar.controller;

import com.phar.database.DatabaseConnection;
import com.phar.extraFunctionality.CFunctions;
import com.phar.model.ProductEntry;
import com.phar.model.Sales;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.*;
import java.util.List;

/**
 * Created by Sam on 11/18/2016.
 */
public class SalesController implements Initializable{

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
    private TableColumn<Sales, Double> proQuantity;

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

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private List<String> productList = new ArrayList<String>();
    private List<Float> getAmountValue = new ArrayList<>();
    private String pIdd;
    private ObservableList<Sales> productTableList = FXCollections.observableArrayList();
    private Double qtyLeft;
    private float newTotal = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            connection = DatabaseConnection.getConnection();
            String query = "SELECT product_name from store";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                productList.add(resultSet.getString("product_name"));
            }
            pName.getItems().addAll(productList);
            pName.valueProperty().addListener((observable, oldValue, newValue) -> {
                String otherInfo = "SELECT product_id,batch,quantity,mRP, expire FROM store WHERE product_name='" + pName.getValue() + "'";
                resultSet = CFunctions.executeQuery(preparedStatement, connection, otherInfo, resultSet);
                try{
                    while (resultSet.next()){
                        pIdd = resultSet.getString("product_id");
                        p_id1.setText(pIdd);
                        pBatch.setText(resultSet.getString("batch"));
                        qLeftInStore.setText(resultSet.getString("quantity"));
                        qtyLeft = Double.valueOf(resultSet.getString("quantity"));
                        pmrp.setText(resultSet.getString("mRP"));
                        System.out.println(resultSet.getString("mRP"));
                        pExpire.setText(resultSet.getString("expire"));
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            });
        }catch (SQLException  | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @FXML
    void clickToAddToList(ActionEvent event) {
            Sales s = new Sales();
            s.setProductID(pIdd);
            s.setProductName(pName.getValue());
            s.setProductBatch(pBatch.getText());
            s.setmRP(Double.valueOf(pmrp.getText()));
            s.setProductQuantity(Double.valueOf(qEntered.getText()));
            s.setExpireDate(pExpire.getText());
            s.setDiscount(Double.valueOf(pDiscount.getText()));
            s.setAmount(Double.valueOf(pAmount.getText()));

            productTableList.add(s);


            proId.setCellValueFactory(new PropertyValueFactory<Sales, String>("productID"));
            proName.setCellValueFactory(new PropertyValueFactory<Sales, String>("productName"));
            proBatch.setCellValueFactory(new PropertyValueFactory<Sales, String>("productBatch"));
            proMrp.setCellValueFactory(new PropertyValueFactory<Sales, Double>("mRP"));
            proQuantity.setCellValueFactory(new PropertyValueFactory<Sales, Double>("productQuantity"));
            proExpDate.setCellValueFactory(new PropertyValueFactory<Sales, String>("expireDate"));
            proAmount.setCellValueFactory(new PropertyValueFactory<Sales, Double>("amount"));
            proDiscount.setCellValueFactory(new PropertyValueFactory<Sales, Double>("discount"));

            pTableStore.setItems(productTableList);
            float total1 = Float.valueOf(pAmount.getText());

            getAmountValue.add(total1);
            newTotal +=total1;
            total.setText(String.valueOf(newTotal));

            Double newQty = qtyLeft - Double.valueOf(qEntered.getText());
            String sql1 = "UPDATE store SET quantity='" + newQty + "' WHERE product_name = '" + pName.getValue() + "'";
            System.out.println(sql1);
        try {
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            sql1 = "UPDATE store SET quantity='" + s.getProductQuantity() + "'";
            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.executeUpdate();
            }
            catch (SQLException e){
            e.printStackTrace();}
    }
}
