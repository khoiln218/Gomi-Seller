package vn.gomisellers.apps.main;

import vn.gomisellers.apps.BaseViewModel;

/**
 * Created by KHOI LE on 5/15/2020.
 */
public class MainViewModel extends BaseViewModel<MainEvent> {
    int unreadNotify = 0;

    void requestNotificationBadges() {
        updateNotificationBadges();
    }

    private void updateNotificationBadges() {
        MainEvent<Integer> event = new MainEvent<>(MainEvent.NOTIFY);
        event.setData(unreadNotify);
        getCmd().call(event);
    }
}
