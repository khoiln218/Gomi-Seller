package vn.gomicorp.seller.main.market;

import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 3/25/2020.
 */
class MarketEvent<T> extends BaseEvent<T> {
    static final int SELECT_ERROR = 0;
    static final int ON_PICK = 1;
    static final int ONCLICK_CATEGORY = 2;
    static final int ONCLICK_COLLECTION = 3;
    static final int SHOW_DETAIL = 4;

    MarketEvent(int code) {
        super(code);
    }

    MarketEvent(int code, String message) {
        super(code, message);
    }
}
