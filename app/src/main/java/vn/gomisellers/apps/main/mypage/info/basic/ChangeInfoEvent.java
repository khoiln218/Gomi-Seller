package vn.gomisellers.apps.main.mypage.info.basic;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 4/17/2020.
 */
class ChangeInfoEvent extends BaseEvent<Long> {

    static final int HIDE_KEYBOARD = 0;
    static final int SHOW_DATE_PICKER = 1;
    static final int SHOW_LOADING = 2;
    static final int HIDE_LOADING = 3;
    static final int UPDATE_DONE = 4;

    ChangeInfoEvent(int code) {
        super(code);
    }
}
