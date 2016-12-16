package com.phar.interfaces;

import com.phar.model.SalesAdmin;
import com.phar.model.User;

/**
 * Created by Sam on 11/10/2016.
 */
public interface UserInterface {

    public boolean checkUser(User user);

    public boolean checkSalesAdmin(SalesAdmin salesAdmin);
}
