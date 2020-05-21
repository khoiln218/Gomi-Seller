package vn.gomisellers.apps.main.live.main;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 5/21/2020.
 */
class LiveMainEvent<T> extends BaseEvent<T> {
    static final int USER_OFFLINE = 0;
    static final int RENDER_REMOTE_USER = 1;
    static final int FINISH = 2;
    static final int ADD_USER = 3;
    static final int REMOVE_USER = 4;

    LiveMainEvent(int code) {
        super(code);
    }
}
