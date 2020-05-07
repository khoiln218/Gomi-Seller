package vn.gomisellers.apps.main.notification;

import androidx.lifecycle.MutableLiveData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.event.NotificationHandler;
import vn.gomisellers.apps.main.MainEvent;

/**
 * Created by KHOI LE on 5/5/2020.
 */
public class NotificationViewModel extends BaseViewModel<NotificationEvent> implements NotificationHandler {

    public MutableLiveData<NotificationAdapter> notificationAdapterMutableLiveData;
    private NotificationAdapter adapter;
    private List<Notification> notifications;

    public NotificationViewModel() {
        notificationAdapterMutableLiveData = new MutableLiveData<>();
        adapter = new NotificationAdapter(this);
        notificationAdapterMutableLiveData.setValue(adapter);
    }

    void requestNotifications() {
        dummy();
    }

    private void dummy() {
        showProgressing();
        notifications = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < 20; i++) {
            boolean isRead = new Random().nextBoolean();
            Notification notification = new Notification(i);
            notification.setRead(isRead);
            notifications.add(notification);
            if (!isRead) count++;
        }
        updateNotificationBadges(count);
        adapter.setNotifications(notifications);
        loaded();
    }

    private void updateNotificationBadges(int count) {
        MainEvent<Integer> event = new MainEvent<>(MainEvent.NOTIFY);
        event.setData(count);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onClick(Notification notification) {
        NotificationEvent<Notification> event = new NotificationEvent(NotificationEvent.ONCLICK);
        event.setData(notification);
        getCmd().call(event);
    }
}
