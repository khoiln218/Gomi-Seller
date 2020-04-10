package vn.gomicorp.seller.shopinfo;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 3/20/2020.
 */
public class ShopInfoEvent<T> extends Event {
    public static final int CREATE_SUCCESS = 0;
    public static final int CREATE_ERROR = 1;
    public static final int VERIFY_ERROR = 2;
    public static final int VERIFY_SUCCESS = 3;
    public static final int START_CROPPER = 4;
    public static final int REQUEST_PERMISSION = 5;

    public ShopInfoEvent(int code) {
        this.code = code;
    }

    public ShopInfoEvent(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
