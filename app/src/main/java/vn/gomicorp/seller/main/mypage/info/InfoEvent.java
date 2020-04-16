package vn.gomicorp.seller.main.mypage.info;

import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 4/16/2020.
 */
class InfoEvent<T> extends BaseEvent<T> {
    static final int HIDE_KEYBOARD = 0;
    static final int SHOW_DATE_PICKER = 1;

    InfoEvent(int code) {
        super(code);
    }
}
