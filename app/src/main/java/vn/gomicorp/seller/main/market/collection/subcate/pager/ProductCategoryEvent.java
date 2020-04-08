package vn.gomicorp.seller.main.market.collection.subcate.pager;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 4/6/2020.
 */
public class ProductCategoryEvent extends Event {
    public static final int SELECT_ERROR = 1;
    public static final int ON_PICK = 2;
    public static final int ON_SHOW = 3;

    public ProductCategoryEvent(int code) {
        this.code = code;
    }

    public ProductCategoryEvent(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
