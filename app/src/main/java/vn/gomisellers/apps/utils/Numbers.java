package vn.gomisellers.apps.utils;

import java.text.NumberFormat;
import java.util.Currency;

public class Numbers {
    private static Currency currencyVN = Currency.getInstance("VND");

    public static String doubleFormat(double value) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setCurrency(currencyVN);
        return formatter.format(value);
    }

    public static String currencyFormat(double value) {
        return String.format("%s%s", doubleFormat(value), currencyVN.getSymbol());
    }
}