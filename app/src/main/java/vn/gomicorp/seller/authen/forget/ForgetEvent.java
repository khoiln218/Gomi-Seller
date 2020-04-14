package vn.gomicorp.seller.authen.forget;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 3/16/2020.
 */
public class ForgetEvent<T> extends Event {
    public static final int USERNAME_SUCCESS = 0;
    public static final int USERNAME_ERROR = 1;
    public static final int FORGER_SUCCESS = 2;
    public static final int FORGER_EEROR = 3;

    public ForgetEvent(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public ForgetEvent(int code) {
        this.code = code;
    }
}
