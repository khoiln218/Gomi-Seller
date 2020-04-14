package vn.gomicorp.seller.authen.signin;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 3/12/2020.
 */
public class SignInEvent extends Event {
    public static final int LOG_IN_SUCCESS = 0;
    public static final int GOTO_SIGN_UP = 1;
    public static final int FORGET_PASSWORD = 2;
    public static final int HIDE_KEYBOARD = 3;

    public SignInEvent(int code) {
        this.code = code;
    }
}
