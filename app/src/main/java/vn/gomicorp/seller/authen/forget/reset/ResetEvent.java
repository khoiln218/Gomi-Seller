package vn.gomicorp.seller.authen.forget.reset;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 3/16/2020.
 */
public class ResetEvent<T> extends Event {
    public static final int RESET_SUCCESS = 0;
    public static final int HIDE_KEYBOARD = 1;

    public ResetEvent(int code) {
        this.code = code;
    }
}
