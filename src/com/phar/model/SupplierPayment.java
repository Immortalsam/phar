package com.phar.model;

/**
 * Created by Sam on 11/22/2016.
 */
public class SupplierPayment {

    private String SupplierId;
    private String PaymentDate;
    private float AmtToBePaid;
    private float AmtPaid;
    private float AmtRemaining;

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        PaymentDate = paymentDate;
    }

    public float getAmtToBePaid() {
        return AmtToBePaid;
    }

    public void setAmtToBePaid(float amtToBePaid) {
        AmtToBePaid = amtToBePaid;
    }

    public float getAmtPaid() {
        return AmtPaid;
    }

    public void setAmtPaid(float amtPaid) {
        AmtPaid = amtPaid;
    }

    public float getAmtRemaining() {
        return AmtRemaining;
    }

    public void setAmtRemaining(float amtRemaining) {
        AmtRemaining = amtRemaining;
    }
}
