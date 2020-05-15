package vn.gomisellers.apps.event;

import vn.gomisellers.apps.data.source.model.data.Notification;

/**
 * Created by KHOI LE on 5/6/2020.
 */
public interface NotificationHandler {
    void onClick(Notification notification);
}
