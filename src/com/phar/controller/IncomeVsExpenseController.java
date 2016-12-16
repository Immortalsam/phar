package com.phar.controller;

import com.phar.database.DatabaseConnection;
import com.phar.model.IncomeExpense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

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
 * Created by Sam on 12/14/2016.
 */
public class IncomeVsExpenseController implements Initializable {
    @FXML
    private DatePicker from_tdate,to_tdate;

    @FXML
    private TableView<IncomeExpense> itable;

    @FXML
    private TableColumn<IncomeExpense, String> iparticular;

    @FXML
    private TableColumn<IncomeExpense, Double> iamount;

    @FXML
    private TableView<IncomeExpense> etable;

    @FXML
    private TableColumn<IncomeExpense, String> eparticular;

    @FXML
    private TableColumn<IncomeExpense, Double> eamount;

    @FXML
    private Label iTotal;

    @FXML
    private Label sTotal;

    @FXML
    private Label profit;

    @FXML
    private Label loss;

    private double incomeTotal;
    private double expenseTotal;


    private Connection connection;
    private ObservableList<IncomeExpense> incomeList = FXCollections.observableArrayList();
    private ObservableList<IncomeExpense> expenseList = FXCollections.observableArrayList();
    private ResultSet resultSet,resultSet1;
    private DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public IncomeVsExpenseController(){
        try {
            connection = DatabaseConnection.getConnection();
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        from_tdate.setValue(LocalDate.now());
        to_tdate.setValue(LocalDate.now());

        String cquery = "SELECT sum(total_amount) FROM customer_bill";
        try (PreparedStatement statement = connection.prepareStatement(cquery)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                IncomeExpense ie = new IncomeExpense();
                ie.setIparticulars("Sales".toUpperCase());
                Double total = resultSet.getDouble("sum(total_amount)");
                ie.setIamount(Double.valueOf(decimalFormat.format(total)));
                incomeList.addAll(ie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IncomeExpense i1 = new IncomeExpense();
        i1.setIparticulars(" ");
        i1.setIamount(null);
        incomeList.addAll(i1);

        IncomeExpense i2 = new IncomeExpense();
        i2.setIparticulars("Indirect Income".toUpperCase());
        i2.setIamount(null);

        incomeList.addAll(i2);

        String query = "SELECT DISTINCT income_source, sum(price) FROM income GROUP BY income_source";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                IncomeExpense ie = new IncomeExpense();
                ie.setIparticulars(resultSet.getString("income_source"));
                ie.setIamount(resultSet.getDouble("sum(price)"));
                incomeList.addAll(ie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query2 = "SELECT sum(total_amount) FROM bill";
        try (PreparedStatement statement = connection.prepareStatement(query2)) {
            resultSet1 = statement.executeQuery();
            while (resultSet1.next()) {
                IncomeExpense ei = new IncomeExpense();
                ei.setEamount(resultSet1.getDouble("sum(total_amount)"));
                ei.setEparticulars("Purchase".toUpperCase());
                expenseList.addAll(ei);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        IncomeExpense ei = new IncomeExpense();
        ei.setEparticulars(" ");
        ei.setEamount(null);
        expenseList.addAll(ei);

        IncomeExpense e1 = new IncomeExpense();
        e1.setEparticulars("Indirect Expenses".toUpperCase());
        e1.setEamount(null);
        expenseList.addAll(e1);

        String query1 = "SELECT DISTINCT expense_source, sum(amount) FROM expense GROUP BY expense_source";
        try (PreparedStatement statement = connection.prepareStatement(query1)) {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                IncomeExpense e2 = new IncomeExpense();
                e2.setEparticulars(resultSet.getString("expense_source"));
                e2.setEamount(resultSet.getDouble("sum(amount)"));
                expenseList.addAll(e2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        iparticular.setCellValueFactory(new PropertyValueFactory<IncomeExpense, String>("iparticulars"));
        iamount.setCellValueFactory(new PropertyValueFactory<IncomeExpense, Double>("iamount"));
        eparticular.setCellValueFactory(new PropertyValueFactory<IncomeExpense, String>("eparticulars"));
        eamount.setCellValueFactory(new PropertyValueFactory<IncomeExpense, Double>("eamount"));
        iparticular.setCellFactory(new Callback<TableColumn<IncomeExpense, String>, TableCell<IncomeExpense, String>>() {
            @Override
            public TableCell<IncomeExpense, String> call(TableColumn<IncomeExpense, String> param) {
                return new TableCell<IncomeExpense, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            if (item.contains("SALES") || item.contains("INDIRECT")) {
                                this.setStyle("-fx-font-weight: bold");
                                this.setAlignment(Pos.CENTER);
                            } else {
                                this.setAlignment(Pos.CENTER_LEFT);
                            }
                            setText(item);
                        }
                    }
                };
            }
        });
        eparticular.setCellFactory(new Callback<TableColumn<IncomeExpense, String>, TableCell<IncomeExpense, String>>() {
            @Override
            public TableCell<IncomeExpense, String> call(TableColumn<IncomeExpense, String> param) {
                return new TableCell<IncomeExpense, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            if (item.contains("PURCHASE") || item.contains("INDIRECT")) {
                                this.setStyle("-fx-font-weight: bold");
                                this.setAlignment(Pos.CENTER);
                            } else {
                                this.setAlignment(Pos.CENTER_LEFT);
                            }
                            setText(item);
                        }
                    }
                };
            }
        });
        itable.setItems(incomeList);
        etable.setItems(expenseList);

        TableColumn<IncomeExpense, Double> column = iamount;
        List<Double> columnData = new ArrayList<>();
        for (IncomeExpense item : itable.getItems()) {
            columnData.add(column.getCellObservableValue(item).getValue());
            System.out.println(columnData);
            double sum = 0.0;
            for (Double d : columnData) {
                if (d == null) {
                    d = 0.0;
                }
                sum += d;
            }
            incomeTotal = sum;
            iTotal.setText(String.valueOf(sum));
        }

        TableColumn<IncomeExpense, Double> column1 =  eamount;
        List<Double> amountData = new ArrayList<>();
        for (IncomeExpense item : etable.getItems()) {
            amountData.add(column1.getCellObservableValue(item).getValue());
            System.out.println(amountData);
            double sum1 = 0.0;
            for (Double d : amountData) {
                if (d == null) {
                    d = 0.0;
                }
                sum1 += d;
            }
            expenseTotal = sum1;
            sTotal.setText(String.valueOf(sum1));
        }

        if(incomeTotal > expenseTotal){
            double profitGained = incomeTotal - expenseTotal;
            profit.setText(String.valueOf(profitGained));
            loss.setText("-");
        }
        else{
            loss.setText(Double.valueOf(Math.abs(incomeTotal - expenseTotal)).toString());
            profit.setText("-");
        }
    }
}
