package vn.gomicorp.seller.main.mypage;

import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 4/16/2020.
 */
class MyPageEvent extends BaseEvent {
    static final int UPDATE_INFO = 0;

    MyPageEvent(int code) {
        super(code);
    }
}
