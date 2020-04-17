package vn.gomicorp.seller.main.mypage.info.password;

import vn.gomicorp.seller.event.BaseEvent;

/**
 * Created by KHOI LE on 4/17/2020.
 */
public class ChangePasswordEvent extends BaseEvent {
    public static final int SHOW_LOADING = 0;
    public static final int HIDE_LOADING = 1;
    public static final int CHANGE_PASSWORD_DONE = 2;

    public ChangePasswordEvent(int code) {
        super(code);
    }
}
