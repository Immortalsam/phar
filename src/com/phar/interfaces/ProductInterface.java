package com.phar.interfaces;

import com.phar.model.Product;
import javafx.collections.ObservableList;

/**
 * Created by Sam on 11/7/2016.
 */
public interface ProductInterface {

    public boolean addProduct(Product product);

    public ObservableList<Product> listProduct();

    public boolean updateProduct();

    public boolean deleteProduct();

    public boolean alertWhenAboutToFinish();

}
