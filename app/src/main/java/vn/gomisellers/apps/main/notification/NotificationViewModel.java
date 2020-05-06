package vn.gomisellers.apps.main.notification;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.event.NotificationHandler;

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
        for (int i = 0; i < 20; i++) {
            Notification notification = new Notification(i);
            notification.setRead(new Random().nextBoolean());
            notifications.add(notification);
        }
        adapter.setNotifications(notifications);
        loaded();
    }

    @Override
    public void onClick(Notification notification) {
        NotificationEvent<Notification> event = new NotificationEvent(NotificationEvent.ONCLICK);
        event.setData(notification);
        getCmd().call(event);
    }
}
