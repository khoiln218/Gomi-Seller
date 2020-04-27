package vn.gomisellers.apps.shopinfo;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 3/20/2020.
 */
class ShopInfoEvent<T> extends BaseEvent<T> {
    static final int CREATE_SUCCESS = 0;
    static final int UPDATE_SUCCESS = 1;
    static final int START_CROPPER = 2;
    static final int REQUEST_PERMISSION = 3;
    static final int SHOW_COUNTRY_DIALOG = 4;
    static final int SHOW_PROVINCE_DIALOG = 5;
    static final int SHOW_DISTRICT_DIALOG = 6;

    ShopInfoEvent(int code) {
        super(code);
    }
}
