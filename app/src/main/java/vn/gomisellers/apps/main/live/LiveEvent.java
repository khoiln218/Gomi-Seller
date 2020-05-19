package vn.gomisellers.apps.main.live;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 5/19/2020.
 */
public class LiveEvent<T> extends BaseEvent<T> {
    static final int START_BROADCAST = 0;

    public LiveEvent(int code) {
        super(code);
    }
}
