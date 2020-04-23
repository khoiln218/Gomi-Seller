package vn.gomisellers.apps.authen.forget;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 3/16/2020.
 */
class ForgetEvent<T> extends BaseEvent<T> {
    static final int FORGER_SUCCESS = 1;
    static final int HIDE_KEYBOARD = 2;

    ForgetEvent(int code) {
        super(code);
    }
}
