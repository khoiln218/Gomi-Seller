package vn.gomicorp.seller.utils;

import java.text.NumberFormat;

public class Numbers {

    public static String doubleFormat(double value) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        return formatter.format(value);
    }

    public static String currencyFormat(double value) {
        return String.format("$%s", doubleFormat(value));
    }
}