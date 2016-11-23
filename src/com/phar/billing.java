package com.phar;

import com.phar.model.Sales;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Manish on 11/19/2016.
 */
public abstract class billing {


    public static void bill(List<Sales> s, int counter) {
        {
            //Sales s = new Sales();
            int billNo = 4321;
            String type = "Cash Sales";
            String add = "";
            String paymode = "Cash";
            String user = "User1";
            double amt = 984.63;
            double rounding = 0.37;
            double netAmt = 985.00;
            int k = 0;

            //  DefaultTableModel mod = (DefaultTableModel) jTable1.getModel();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            //get current date time with Date()
            Date date = new Date();
            Date time = new Date();
            String Date = dateFormat.format(date);
            String Time = timeFormat.format(time);
                String Header =
                        "              *****TECHNORIO PHARMACY*****       \n"
                                + "                     PAN : 123456789  \n"
                                + "                  Sankhamul, Kathmandu  \n"
                                + "                   Phone: 01-4231560  \n"
                                + "Bill No : " + s.get(0).getBillNo() + "    \t\t\t\t\t\t\t"
                                + "Date: " + Date + "\n"
                                + "  \t\t\t\t\t\t\t\t\t\t\t  Time: " + Time + "\n"
                                + "----------------------------------------------------------------\n"
                                + "                          INVOICE  \n"
                                + "----------------------------------------------------------------\n"
                                + "Name    : " + type + "\n"
                                + "Address : " + s.get(0).getAddress() + "\n"
                                + "Prescribed by: " + s.get(0).getPrescribedBy() + "\n"
                                + "Payment mode: " + paymode + "\n"
                                + "================================================================\n"
                                + "SN. Particular          Batch     Expiry   Qty  Rate     Amount\n"
                                + "----------------------------------------------------------------\n";

                String amount =
                        "\n \n \n\t\t\t\t\t\t\t\t\t\t   Total Amount: " + amt + "\n"
                                + "\t\t\t\t\t\t\t\t\t\t       Rounding: " + rounding + "\n"
                                + "\t\t\t\t\t\t\t\t\t\t     Net Amount: " + netAmt + "\n"
                                + "In Words: \n"
                                + "-----------------------------------------------------------------\n"
                                + "Thank you. \n"
                                + "User: " + user + "\n";

                String bill = Header;
            for (int i = 0; i < counter; i++) {


                String pName = s.get(i).getProductName();
                    int sn = i + 1;
                    String batch = s.get(i).getProductBatch();
                    String expiry = s.get(i).getExpireDate();
                    Integer qty = s.get(i).getProductQuantity();
                    double rate = s.get(i).getmRp();
                    double amtt = qty * rate;


                    if (pName.length() > 16) {
                        pName = pName.substring(0, 16) + "  ";
                    } else {
                        for (k = pName.length() - 16; k <= 0; k++) {
                            pName = pName + " ";

                        }
                    }


                    if (batch.length() > 8) {
                        batch = batch.substring(0, 8) + " ";
                    } else {
                        for (int j = batch.length() - 8; j <= 0; j++) {
                            batch = batch + " ";
                        }
                    }


                    String items =
                            sn + "\t" + pName + "\t" + batch + expiry + "  " + qty + "\t" + rate + " \t " + amtt + "\n";

                    bill = bill + items;

                }

                bill = bill + amount;
                System.out.println(bill);
                // printCard(bill);
                //dispose();
            }
        }

    }
