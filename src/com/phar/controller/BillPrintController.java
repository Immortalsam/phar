package com.phar.controller;

import com.phar.extraFunctionality.GetTime;
import com.phar.model.NewSales;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.omg.PortableInterceptor.LOCATION_FORWARD;
import sun.util.resources.LocaleData;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Sam on 12/19/2016.
 */

public class BillPrintController implements Initializable {


    @FXML
    private Label billId, billdate, billtime, name, address, paymentmode, prescribedby, total,
            rouding, netamount, inwords, user,particulars;

    private int counter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for(NewSales x: SalesController.getSalesInfoList()){
            billId.setText(x.getBillNo());
            billdate.setText(String.valueOf(LocalDate.now()));
            GetTime gt = new GetTime();
            billtime.setText(gt.timeNow());
            name.setText(x.getParty());
            address.setText(x.getAddress());
            paymentmode.setText(x.getPayment());
            prescribedby.setText(x.getPrescribedBy());
            total.setText(String.valueOf(x.getTotal()));
        }
    }
}

