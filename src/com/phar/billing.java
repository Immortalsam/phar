package com.phar;

import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Manish on 11/19/2016.
 */
public class billing {

    public static void main(String []args){
        {
            int billNo = 4321;
            String type = "Cash Sales" ;
            String add = "";
            String paymode = "Cash";
            String dr = "Dr. Manish";
            String user ="User1";
            double amt = 984.63;
            double rounding = 0.37;
            double netAmt = 985.00;
            int k=0;

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
                            + "Bill No : "+billNo+"    \t\t\t\t\t\t\t"
                            + "Date: "+Date+ "\n"
                            +"  \t\t\t\t\t\t\t\t\t\t\t  Time: "+Time+"\n"
                            + "----------------------------------------------------------------\n"
                            + "                          INVOICE  \n"
                            + "----------------------------------------------------------------\n"
                            + "Name    :"+ type+"\n"
                            + "Address :"+ add+"\n"
                            + "Prescribed by:" +dr+"\n"
                            + "Payment mode: "+paymode+"\n"
                            + "================================================================\n"
                            + "SN. Particular          Batch     Expiry   Qty  Rate     Amount\n"
                            + "----------------------------------------------------------------\n";

            String amount  =
                    "\n \n \n\t\t\t\t\t\t\t\t\t\t   Total Amount: "+  amt   +"\n"
                            + "\t\t\t\t\t\t\t\t\t\t       Rounding: "   +  rounding    + "\n"
                            + "\t\t\t\t\t\t\t\t\t\t     Net Amount: "   +  netAmt    + "\n"
                            + "In Words: \n"
                            + "-----------------------------------------------------------------\n"
                            + "Thank you. \n"
                            + "User: "+user+"\n";

            String bill = Header;
            int i = 0;
            do
            {

                int sn =     i+1;
                String particular =      "part"+ i+1;
                String batch =     "ba"+ i+1;
                String expiry = "date  " +i+1;
                int qty = i+98;
                double rate= i+998;
                double amtt = qty*rate;


                if(particular.length() > 16)
                {
                    particular = particular.substring(0,16)+"  ";
                }
                else
                {
                    for(k= particular.length()-16; k<= 0; k++)
                    {
                        particular = particular+" ";

                    }
                }


                if(batch.length()>8)
                {
                    batch = batch.substring(0,8)+" ";
                }
                else
                {
                    for(int j= batch.length()-8; j<= 0;  j++)
                    {
                        batch = batch+" ";
                    }
                }


                String items =
                        sn+"\t"+particular+"\t"+batch+expiry +"  "+qty+"\t"+rate+" \t "+amtt+"\n";

                bill = bill+ items;
                i++;

            } while(i <=5);

            bill = bill+amount;
            System.out.println(bill);
            // printCard(bill);
            //dispose();
        }
    }

}
