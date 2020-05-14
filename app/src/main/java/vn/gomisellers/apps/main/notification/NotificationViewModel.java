package vn.gomisellers.apps.main.notification;

import android.os.Handler;

import androidx.lifecycle.MutableLiveData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.event.NotificationHandler;
import vn.gomisellers.apps.event.OnLoadMoreListener;
import vn.gomisellers.apps.main.MainEvent;

/**
 * Created by KHOI LE on 5/5/2020.
 */
public class NotificationViewModel extends BaseViewModel<NotificationEvent> implements NotificationHandler, OnLoadMoreListener {

    public MutableLiveData<NotificationAdapter> notificationAdapterMutableLiveData;

    private NotificationAdapter adapter;
    private List<Notification> notifications;

    private int page;
    private int totalPage;
    private int unreadBadges;

    public NotificationViewModel() {
        notificationAdapterMutableLiveData = new MutableLiveData<>();
        notifications = new ArrayList<>();
        adapter = new NotificationAdapter(this, this);
        notificationAdapterMutableLiveData.setValue(adapter);
    }

    public void onRefresh() {
        page = 1;
        unreadBadges = 0;
        notifications.clear();
        updateNotification();

        requestNotifications();
    }

    @Override
    public void onLoadMore() {
        if (page >= totalPage) return;
        page++;
        notifications.add(null);
        requestNotifications();
    }

    private void requestNotifications() {
        showProgressing();
        adapter.setLoading();
        dummy();
    }

    private void dummy() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int maxItems = 20;
                int start = maxItems * (page - 1);
                for (int i = start; i < start + maxItems; i++) {
                    boolean isRead = new Random().nextBoolean();
                    Notification notification = new Notification(i);
                    notification.setRead(isRead);
                    notifications.add(notification);
                    if (!isRead) unreadBadges++;
                }
                totalPage = 10;
                notifications.remove(null);
                updateNotificationBadges(unreadBadges);
                updateNotification();
                checkNotifyEmpty(notifications);
                loaded();
            }
        }, 300);
    }

    private void updateNotification() {
        adapter.setNotifications(notifications);
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
