package vn.gomicorp.seller.main.mypage.setting;

import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 4/20/2020.
 */
class AccountSettingEvent extends BaseEvent {
    static final int SIGN_OUT = 0;

    AccountSettingEvent(int code) {
        super(code);
    }
}
