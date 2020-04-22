package vn.gomicorp.seller.main.market.collection.cate;

import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 4/22/2020.
 */
class CategoryEvent<T> extends BaseEvent<T> {
    static final int OPEN_SUB_CATEGORY = 0;
    static final int PICK_PRODUCT = 1;
    static final int SHOW_DETAIL = 2;

    CategoryEvent(int code) {
        super(code);
    }
}
