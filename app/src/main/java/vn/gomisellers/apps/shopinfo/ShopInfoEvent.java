package vn.gomisellers.apps.shopinfo;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 3/20/2020.
 */
class ShopInfoEvent<T> extends BaseEvent<T> {
    static final int CREATE_SUCCESS = 0;
    static final int CREATE_ERROR = 1;
    static final int VERIFY_ERROR = 2;
    static final int VERIFY_SUCCESS = 3;
    static final int START_CROPPER = 4;
    static final int REQUEST_PERMISSION = 5;

    ShopInfoEvent(int code) {
        super(code);
    }

    ShopInfoEvent(int code, String msg) {
        super(code, msg);
    }
}
