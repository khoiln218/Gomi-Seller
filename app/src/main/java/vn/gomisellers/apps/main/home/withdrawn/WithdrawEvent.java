package vn.gomisellers.apps.main.home.withdrawn;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 4/21/2020.
 */
class WithdrawEvent extends BaseEvent {
    static final int WITHDRAW_BANK = 0;
    static final int WITHDRAW_COUPON = 1;

    WithdrawEvent(int code) {
        super(code);
    }
}
