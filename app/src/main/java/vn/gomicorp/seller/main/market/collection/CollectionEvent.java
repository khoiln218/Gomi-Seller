package vn.gomicorp.seller.main.market.collection;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 3/27/2020.
 */
public class CollectionEvent extends Event {
    public static final int OPEN_SUB_CATEGORY = 0;
    public static final int ON_PICK = 1;
    public static final int SELECT_ERROR = 2;

    public CollectionEvent(int code) {
        this.code = code;
    }

    public CollectionEvent(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
