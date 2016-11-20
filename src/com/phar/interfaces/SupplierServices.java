package com.phar.interfaces;

import com.phar.model.Supplier;
import javafx.collections.ObservableList;

/**
 * Created by Sam on 11/6/2016.
 */

public interface SupplierServices {

    public boolean addSupplier(Supplier supplier);

    public ObservableList<Supplier> listSupplier();

    public boolean editSupplier();

    public boolean deleteSupplier();

}
