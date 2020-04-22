package vn.gomicorp.seller.main.mypage.info.password;

import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 4/17/2020.
 */
class ChangePasswordEvent extends BaseEvent {
    static final int SHOW_LOADING = 0;
    static final int HIDE_LOADING = 1;
    static final int CHANGE_PASSWORD_DONE = 2;
    static final int HIDE_KEY_BOARD = 3;

    ChangePasswordEvent(int code) {
        super(code);
    }
}
