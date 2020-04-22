package vn.gomicorp.seller.authen.forget.reset;

import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 3/16/2020.
 */
class ResetEvent<T> extends BaseEvent<T> {
    static final int RESET_SUCCESS = 0;
    static final int HIDE_KEYBOARD = 1;

    ResetEvent(int code) {
        super(code);
    }
}
