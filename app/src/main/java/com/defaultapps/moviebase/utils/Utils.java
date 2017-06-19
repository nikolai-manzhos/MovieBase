package com.defaultapps.moviebase.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private Utils() {
        throw new AssertionError("This class cannot be instantiated.");
    }

    public static String convertDate(String dateString) {
        String finalString = "NaN";
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = inputFormat.parse(dateString);
            DateFormat outputFormat = new SimpleDateFormat(" MMMM-dd, yyyy", Locale.ENGLISH);
            finalString = outputFormat.format(date);
        } catch (ParseException ex) {}
        return finalString;
    }
}
