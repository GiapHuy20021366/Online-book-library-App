package com.example.library2.format;

import java.util.Date;

public class DateParse {
    public static String parseDate(Date date) {
        if (date == null) {
            return "";
        }
        String d = date.toString();
        int year = Integer.parseInt(d.substring(0, 4));
        int month = Integer.parseInt(d.substring(5, 7));
        return "" + month + "/" + year;
    }
}
