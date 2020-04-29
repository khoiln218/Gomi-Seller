package vn.gomisellers.apps.main.mypage.order;

import vn.gomisellers.apps.data.source.model.data.Order;
import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 4/29/2020.
 */
class OrderListEvent extends BaseEvent<Order> {
    static final int SHOW_DETAIL = 0;

    OrderListEvent(int code) {
        super(code);
    }
}
