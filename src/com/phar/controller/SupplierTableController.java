package com.phar.controller;

import com.phar.Main;
import com.phar.interfaceImplement.SupplierImplement;
import com.phar.model.Supplier;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Sam on 11/9/2016.
 */

public class SupplierTableController {

    @FXML
    private TableView<Supplier> supplierTable;

    @FXML
    private TableColumn<Supplier, String> supplierId;

    @FXML
    private TableColumn<Supplier, String> supplierName;

    @FXML
    private TableColumn<Supplier, String> supplierAddress;

    @FXML
    private TableColumn<Supplier, String> supplierContact;

    @FXML
    private TableColumn<Supplier, String> supplierCategory;

    @FXML
    private TableColumn<Supplier, Double> supplierPanNo;

    @FXML
    private TextField searchField;

    private ObservableList<Supplier> customerList;

    public SupplierTableController(){

    }

    @FXML
    private void initialize(){
        SupplierImplement supplierImplement = new SupplierImplement();
        customerList = supplierImplement.listSupplier();
        supplierId.setCellValueFactory(new PropertyValueFactory<Supplier, String>("supplierId"));
        supplierName.setCellValueFactory(new PropertyValueFactory<Supplier, String>("supplierName"));
        supplierAddress.setCellValueFactory(new PropertyValueFactory<Supplier, String>("supplierAddress"));
        supplierContact.setCellValueFactory(new PropertyValueFactory<Supplier, String>("supplierContact"));
        supplierCategory.setCellValueFactory(new PropertyValueFactory<Supplier, String>("supplierCategory"));
        supplierPanNo.setCellValueFactory(new PropertyValueFactory<Supplier, Double>("panNo"));
        supplierTable.setItems(customerList);
    }


        public void searchSupplier(KeyEvent keyEvent){
        FilteredList<Supplier> filteredList = new FilteredList<>(customerList, e -> true);
        searchField.setOnKeyReleased(e ->{
            searchField.textProperty().addListener((observedValue, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super Supplier>) supplier -> {
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if(supplier.getSupplierId().contains(newValue)){
                        return true;
                    }
                    else if(supplier.getSupplierName().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                    else if(supplier.getSupplierAddress().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                    else if(supplier.getSupplierCategory().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    }
                    return false;
                });
            });
            SortedList<Supplier> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(supplierTable.comparatorProperty());
            supplierTable.setItems(sortedList);
        });
    }
    }



