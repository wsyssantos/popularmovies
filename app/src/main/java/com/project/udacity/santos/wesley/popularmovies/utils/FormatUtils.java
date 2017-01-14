package com.project.udacity.santos.wesley.popularmovies.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by wesley on 14/01/17.
 */

public class FormatUtils {

    public static String formatDoubleToOneDecimal(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        DecimalFormat df = new DecimalFormat("#,##0.0", symbols);
        return df.format(value);
    }
}
