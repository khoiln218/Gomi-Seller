package vn.gomisellers.apps.main.market.detail;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 4/21/2020.
 */
class ProductDetailEvent<T> extends BaseEvent<T> {
    static final int SHOW_DETAIL = 0;
    static final int VIEW_DESCRIPTION = 1;

    ProductDetailEvent(int code) {
        super(code);
    }
}
