package vn.gomicorp.seller.authen.forget;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 3/16/2020.
 */
public class ForgetEvent<T> extends Event {
    public static final int FORGER_SUCCESS = 1;
    public static final int HIDE_KEYBOARD = 2;

    public ForgetEvent(int code) {
        this.code = code;
    }
}
