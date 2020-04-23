package vn.gomisellers.apps.authen.signup;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 3/12/2020.
 */
class SignUpEvent extends BaseEvent {
    static final int SIGN_UP_SUCCESS = 0;
    static final int HIDE_KEYBOARD = 1;
    static final int GOTO_LOGIN = 2;

    SignUpEvent(int code) {
        super(code);
    }
}
