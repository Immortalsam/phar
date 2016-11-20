package com.phar.interfaces;

import com.phar.model.ProductDetails;
import javafx.collections.ObservableList;

import java.sql.Connection;

/**
 * Created by Sam on 11/16/2016.
 */
public interface ProductDetailInterface {
    public ObservableList<ProductDetails> listSupplier(Connection connection);
}
