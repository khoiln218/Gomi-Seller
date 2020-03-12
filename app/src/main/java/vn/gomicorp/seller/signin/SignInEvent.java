package vn.gomicorp.seller.signin;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 3/12/2020.
 */
public class SignInEvent extends Event {
    public static final int LOG_IN_SUCCESS = 0;
    public static final int LOG_IN_FALSE = 1;
    public static final int GOTO_SIGN_UP = 2;
    public static final int FORGET_PASSWORD = 3;

    public SignInEvent(int code){
        this.code = code;
    }

    public SignInEvent(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
