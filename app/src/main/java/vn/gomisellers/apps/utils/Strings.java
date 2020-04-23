package vn.gomisellers.apps.utils;

import android.util.Patterns;

public class Strings {

    public static String NA = "-";
    public static String Delimiter = "|";

    public static boolean isNullOrEmpty(String s) {
        if (s == null || s.trim().isEmpty() || s.equals("00000000-0000-0000-0000-000000000000"))
            return true;

        return false;
    }

    public static boolean isEmail(String s) {
        return Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }


    public static boolean isPhoneNumber(String phoneNumber) {
        try {
            String s = phoneNumber.replaceAll("-", "")
                    .replaceAll("\\s", "");

            double isNumber = Double.parseDouble(s);
            int firstChar = Integer.parseInt(Character.toString(s.charAt(0)));
            int secondChar = Integer.parseInt(Character.toString(s.charAt(1)));
            int thirdChar = Integer.parseInt(Character.toString(s.charAt(2)));


            if (firstChar == 0 && secondChar == 1 && thirdChar == 0 && s.length() == 11)
                return true;

            else if (firstChar == 1 && secondChar == 0 && s.length() == 10)
                return true;

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public static String formattingPhoneNumbers(String phoneNumber) {
        try {
            String s = phoneNumber.replaceAll("-", "")
                    .replaceAll("\\s", "");

            double isNumber = Double.parseDouble(s);
            int firstChar = Integer.parseInt(Character.toString(s.charAt(0)));

            if (firstChar == 0 && s.length() == 11)
                return phoneNumber;

            else if (firstChar == 1 && s.length() == 10)
                return "0" + phoneNumber;

        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }

        return "";
    }

    /**
     *
     */
    public static String toAccountNumberFormat(String s) {
        if (s.length() > 6) {
            String s1 = s.substring(0, 4);
            String s2 = s.substring(4, 8);
            String s3 = s.substring(8);
            return String.format("%s-%s-%s", s1, s2, s3);
        } else if (s.length() > 3) {
            String s1 = s.substring(0, 4);
            String s2 = s.substring(4);
            return String.format("%s-%s", s1, s2);
        } else
            return s;
    }

    /**
     * @param s
     * @return
     */
    public static double toDouble(String s) {
        try {

            double num = Double.parseDouble(s);
            return num;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
