package vn.gomicorp.seller.signup;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 3/12/2020.
 */
public class SignUpEvent extends Event {
    public static final int SIGN_UP_SUCCESS = 0;
    public static final int SIGN_UP_FALSE = 1;
    public static final int GOTO_LOGIN = 3;
    public static final int FULLNAME_ERROR = 4;
    public static final int FULLNAME_SUCCESS = 5;
    public static final int EMAIL_ERROR = 6;
    public static final int EMAIL_SUCCESS = 7;
    public static final int PHONENUMBER_ERROR = 8;
    public static final int PHONENUMBER_SUCCESS = 9;
    public static final int PWD_ERROR = 10;
    public static final int PWD_SUCCESS = 11;
    public static final int CONTRYID_ERROR = 12;
    public static final int CONTRYID_SUCCESS = 13;
    public static final int VERIFY_SUCCESS = 14;
    public static final int VERIFY_ERROR = 15;
    public static final int VERIFY = 16;

    public SignUpEvent(int code) {
        this.code = code;
    }

    public SignUpEvent(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
