package vn.gomisellers.apps.main.market;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 3/25/2020.
 */
class MarketEvent<T> extends BaseEvent<T> {
    static final int ON_PICK = 0;
    static final int ONCLICK_CATEGORY = 1;
    static final int ONCLICK_COLLECTION = 2;
    static final int SHOW_DETAIL = 3;

    MarketEvent(int code) {
        super(code);
    }
}
