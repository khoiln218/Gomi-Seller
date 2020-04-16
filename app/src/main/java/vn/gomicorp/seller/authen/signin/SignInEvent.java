package vn.gomicorp.seller.authen.signin;

import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 3/12/2020.
 */
class SignInEvent extends BaseEvent {
    static final int LOG_IN_SUCCESS = 0;
    static final int GOTO_SIGN_UP = 1;
    static final int FORGET_PASSWORD = 2;
    static final int RESET_PASSWORD = 3;
    static final int HIDE_KEYBOARD = 4;

    SignInEvent(int code) {
        super(code);
    }
}
