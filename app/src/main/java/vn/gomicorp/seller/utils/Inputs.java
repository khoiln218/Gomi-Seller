package vn.gomicorp.seller.utils;

import androidx.lifecycle.MutableLiveData;

import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.R;

public class Inputs {

    public static boolean validateEmail(String email) {
        return !Strings.isNullOrEmpty(email) && Strings.isEmail(email);
    }

    public static boolean validatePassword(String pwd) {
        return !Strings.isNullOrEmpty(pwd) && pwd.length() >= 6 && pwd.length() <= 32;
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return !Strings.isNullOrEmpty(phoneNumber);
    }

    public static boolean validateText(String text, MutableLiveData<String> error, MutableLiveData<Boolean> enableError, MutableLiveData<Boolean> focus, String msg) {
        if (Strings.isNullOrEmpty(text)) {
            error.setValue(msg);
            focus.setValue(true);
            return false;
        }

        enableError.setValue(false);
        return true;
    }

    public static boolean validatePassword(String password, MutableLiveData<String> error, MutableLiveData<Boolean> enableError, MutableLiveData<Boolean> focus) {
        if (Strings.isNullOrEmpty(password) || password.length() < 6 || password.length() > 32) {
            error.setValue(EappsApplication.getInstance().getString(R.string.err_input_password));
            focus.setValue(true);
            return false;
        }
        return true;
    }
}
