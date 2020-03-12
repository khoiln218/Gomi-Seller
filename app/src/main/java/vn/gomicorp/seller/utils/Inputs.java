package vn.gomicorp.seller.utils;

public class Inputs {

    public static boolean validateText(String text) {
        if (Strings.isNullOrEmpty(text)) {
            return false;
        }
        return true;
    }

    public static boolean validateEmail(String email) {
        if (Strings.isNullOrEmpty(email) || !Strings.isEmail(email)) {
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String pwd) {
        if (Strings.isNullOrEmpty(pwd) || pwd.length() < 6 || pwd.length() > 32) {
            return false;
        }
        return true;
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
//        if (Strings.isNullOrEmpty(phoneNumber) || !Strings.isPhoneNumber(phoneNumber)) {
        if (Strings.isNullOrEmpty(phoneNumber)) {
            return false;
        }
        return true;
    }
}
