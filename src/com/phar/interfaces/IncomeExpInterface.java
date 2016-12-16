package com.phar.interfaces;

import com.phar.model.Expenses;
import com.phar.model.Income;

/**
 * Created by Sam on 12/12/2016.
 */
public interface IncomeExpInterface {

    public boolean addIncome(Income in);
    public boolean addExpense(Expenses ex);

}
