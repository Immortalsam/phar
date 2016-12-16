package com.phar.interfaces;

import com.phar.model.ProductEntry;
import javafx.collections.ObservableList;

/**
 * Created by Sam on 11/15/2016.
 */
public interface ProductEntryInterface {
    public boolean addProduct(ProductEntry product);

    public ObservableList<ProductEntry> listProduct();
}
