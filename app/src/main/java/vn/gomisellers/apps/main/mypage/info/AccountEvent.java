package vn.gomisellers.apps.main.mypage.info;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 5/6/2020.
 */
public class AccountEvent<T> extends BaseEvent<T> {
    public static final int SHOW_LOADDING = 0;
    public static final int HIDE_LOADDING = 1;
    public static final int UPDATE_DONE = 2;

    public AccountEvent(int code) {
        super(code);
    }
}
