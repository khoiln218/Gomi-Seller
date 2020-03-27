package vn.gomicorp.seller.main.market.collection;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 3/27/2020.
 */
public class CollectionEvent extends Event {
    public static final int UPDATE_TOOLBAR = 0;

    public CollectionEvent(int code) {
        this.code = code;
    }
}
