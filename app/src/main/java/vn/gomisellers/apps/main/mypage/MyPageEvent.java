package vn.gomisellers.apps.main.mypage;

import android.net.Uri;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 4/16/2020.
 */
class MyPageEvent extends BaseEvent<Uri> {
    static final int UPDATE_INFO = 0;
    static final int SETTING = 1;
    static final int UPDATE_SHOP = 2;
    static final int SIGN_OUT = 3;

    MyPageEvent(int code) {
        super(code);
    }
}
