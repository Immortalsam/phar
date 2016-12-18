package com.phar.extraFunctionality;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sam on 12/16/2016.
 */
public class GetTime {

    public String timeNow(){
        DateFormat timeFormat = new SimpleDateFormat("HH:mm a");
        Date date = new Date();
        String time = timeFormat.format(date);
        return time;
    }

}
