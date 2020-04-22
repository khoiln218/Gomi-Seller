package vn.gomicorp.seller.main.home.withdrawn.bank;

import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 4/21/2020.
 */
class BankAccountEvent extends BaseEvent {
    static final int SELECT_BANK = 0;

    BankAccountEvent(int code) {
        super(code);
    }
}
