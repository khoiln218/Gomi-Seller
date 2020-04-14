package vn.gomicorp.seller.authen.forget.reset;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 3/16/2020.
 */
public class ResetEvent<T> extends Event {
    public static final int NEW_PASSWORD_SUCCESS = 0;
    public static final int NEW_PASSWORD_ERROR = 1;
    public static final int RESET_SUCCESS = 2;
    public static final int RESET_EEROR = 3;

    public ResetEvent(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public ResetEvent(int code) {
        this.code = code;
    }
}
