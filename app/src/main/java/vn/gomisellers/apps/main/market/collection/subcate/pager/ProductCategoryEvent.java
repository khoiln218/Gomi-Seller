package vn.gomisellers.apps.main.market.collection.subcate.pager;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 4/6/2020.
 */
class ProductCategoryEvent<T> extends BaseEvent<T> {
    static final int ON_PICK = 0;
    static final int ON_SHOW = 1;

    ProductCategoryEvent(int code) {
        super(code);
    }

    ProductCategoryEvent(int code, String message) {
        super(code, message);
    }
}
