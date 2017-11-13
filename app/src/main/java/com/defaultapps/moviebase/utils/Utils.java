package com.defaultapps.moviebase.utils;



import android.content.Context;

import com.defaultapps.moviebase.R;
import com.firebase.ui.auth.AuthUI;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

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
        } catch (ParseException ex) {
            Timber.e(ex.getMessage());
        }
        return finalString;
    }

    public static List<AuthUI.IdpConfig> getProvidersList() {
        List<AuthUI.IdpConfig> providers = new ArrayList<>();
        providers.add(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
        providers.add(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());
        return providers;
    }

    public static String formatNumber(int number) {
        return formatNumber((long) number);
    }

    public static String formatNumber(long number) {
        return NumberFormat.getInstance().format(number);
    }

    public static String formatMinutes(Context context, int minutes) {
        int hours = minutes / 60;
        minutes = minutes % 60;
        return context.getString(R.string.movie_runtime, hours, minutes);
    }
}
