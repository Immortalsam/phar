package com.phar.model;

/**
 * Created by Sam on 11/29/2016.
 */
public class CustomerPayment {
    private String customerId;
    private String PaymentDate;
    private float AmtToBePaid;
    private float AmtPaid;
    private float AmtRemaining;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String supplierId) {
        customerId = supplierId;
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
