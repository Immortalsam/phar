package com.phar.model;

/**
 * Created by Sam on 12/14/2016.
 */
public class IncomeExpense {

    private String iparticulars;
    private Double iamount;
    private String eparticulars;
    private Double eamount;


    public String getIparticulars() {
        return iparticulars;
    }

    public void setIparticulars(String iparticulars) {
        this.iparticulars = iparticulars;
    }

    public Double getIamount() {
        return iamount;
    }

    public void setIamount(Double iamount) {
        this.iamount = iamount;
    }

    public String getEparticulars() {
        return eparticulars;
    }

    public void setEparticulars(String eparticulars) {
        this.eparticulars = eparticulars;
    }

    public Double getEamount() {
        return eamount;
    }

    public void setEamount(Double eamount) {
        this.eamount = eamount;
    }
}
