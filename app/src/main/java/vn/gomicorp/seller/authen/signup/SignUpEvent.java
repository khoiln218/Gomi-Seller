package vn.gomicorp.seller.authen.signup;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 3/12/2020.
 */
public class SignUpEvent extends Event {
    public static final int SIGN_UP_SUCCESS = 0;
    public static final int SIGN_UP_FALSE = 1;
    public static final int GOTO_LOGIN = 2;
    public static final int VERIFY_SUCCESS = 3;
    public static final int VERIFY_ERROR = 4;

    public SignUpEvent(int code) {
        this.code = code;
    }

    public SignUpEvent(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
