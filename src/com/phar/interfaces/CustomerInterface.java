package com.phar.interfaces;

import com.phar.model.CustomerInfo;
import com.phar.model.CustomerPayment;

/**
 * Created by Sam on 11/27/2016.
 */
public interface CustomerInterface {

    public boolean addCustomer(CustomerInfo customerInfo);
    public boolean addCustomerPayment(CustomerPayment customerPayment);
}
