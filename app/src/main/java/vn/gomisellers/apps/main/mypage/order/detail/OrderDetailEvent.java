package vn.gomisellers.apps.main.mypage.order.detail;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 5/13/2020.
 */
class OrderDetailEvent<T> extends BaseEvent<T> {
    static final int SHOW_DETAIL = 0;

    OrderDetailEvent(int code) {
        super(code);
    }
}
