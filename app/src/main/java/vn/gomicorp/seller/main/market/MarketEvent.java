package vn.gomicorp.seller.main.market;

import vn.gomicorp.seller.event.Event;

/**
 * Created by KHOI LE on 3/25/2020.
 */
public class MarketEvent extends Event {
    public static final int SELECT_ERROR = 0;

    public MarketEvent(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
