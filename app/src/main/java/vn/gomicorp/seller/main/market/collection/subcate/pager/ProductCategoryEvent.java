package vn.gomicorp.seller.main.market.collection.subcate.pager;

import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 4/6/2020.
 */
class ProductCategoryEvent<T> extends BaseEvent<T> {
    static final int SELECT_ERROR = 1;
    static final int ON_PICK = 2;
    static final int ON_SHOW = 3;

    ProductCategoryEvent(int code) {
        super(code);
    }

    ProductCategoryEvent(int code, String message) {
        super(code, message);
    }
}
