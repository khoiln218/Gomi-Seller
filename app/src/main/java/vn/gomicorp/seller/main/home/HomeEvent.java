package vn.gomicorp.seller.main.home;

import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 4/21/2020.
 */
class HomeEvent<T> extends BaseEvent<T> {
    static final int SHOW_DETAIL = 0;
    static final int REMOVE_PRODUCT = 1;
    static final int WITHDRAW = 2;
    static final int SHARE_SNS = 3;

    HomeEvent(int code) {
        super(code);
    }
}
