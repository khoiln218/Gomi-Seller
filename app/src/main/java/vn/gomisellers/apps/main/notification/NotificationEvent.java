package vn.gomisellers.apps.main.notification;

import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 5/6/2020.
 */
class NotificationEvent<T> extends BaseEvent<T> {
    static final int ONCLICK = 0;

    NotificationEvent(int code) {
        super(code);
    }
}
