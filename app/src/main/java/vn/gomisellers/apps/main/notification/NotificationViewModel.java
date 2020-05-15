package vn.gomisellers.apps.main.notification;

import androidx.lifecycle.MutableLiveData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.data.NotificationRepository;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.model.api.NotificationRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.Notification;
import vn.gomisellers.apps.data.source.remote.ResultCode;
import vn.gomisellers.apps.event.NotificationHandler;
import vn.gomisellers.apps.event.OnLoadMoreListener;
import vn.gomisellers.apps.main.MainEvent;
import vn.gomisellers.apps.utils.LogUtils;

/**
 * Created by KHOI LE on 5/5/2020.
 */
public class NotificationViewModel extends BaseViewModel<NotificationEvent> implements NotificationHandler, OnLoadMoreListener {

    private NotificationRepository mNotificationRepository;

    public MutableLiveData<NotificationAdapter> notificationAdapterMutableLiveData;

    private NotificationAdapter adapter;
    private List<Notification> notifications;

    private int page;
    private int totalPage;

    public NotificationViewModel() {
        mNotificationRepository = NotificationRepository.getInstance();
        notificationAdapterMutableLiveData = new MutableLiveData<>();
        notifications = new ArrayList<>();
        adapter = new NotificationAdapter(this, this);
        notificationAdapterMutableLiveData.setValue(adapter);
    }

    public void onRefresh() {
        page = 1;
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
        NotificationRequest request = new NotificationRequest();
        mNotificationRepository.findby(request, page, new ResultListener<ResponseData<List<Notification>>>() {
            @Override
            public void onLoaded(ResponseData<List<Notification>> result) {
                loaded();
                if (result.getCode() == ResultCode.CODE_OK) {
                    notifications.addAll(result.getResult());
                    totalPage = result.getResult().size() > 0 ? result.getResult().get(0).getTotalPage() : 0;
                    notifications.remove(null);
                    updateNotificationBadges();
                    updateNotification();
                    checkNotifyEmpty(notifications);
                } else {
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                checkConnection(error);
            }
        });
    }

    private void updateNotification() {
        adapter.setNotifications(notifications);
    }

    private void updateNotificationBadges() {
        MainEvent<Integer> event = new MainEvent<>(MainEvent.NOTIFY);
        EventBus.getDefault().post(event);
    }

    @Override
    public void onClick(Notification notification) {
        NotificationEvent<Notification> event = new NotificationEvent(NotificationEvent.ONCLICK);
        event.setData(notification);
        getCmd().call(event);

        requestUpdateStatus(notification);
    }

    private void requestUpdateStatus(Notification notification) {
        if (notification.getStatus() == 1) return;
        NotificationRequest request = new NotificationRequest();
        request.setId(notification.getId());
        mNotificationRepository.updatestatus(request, new ResultListener<ResponseData<Notification>>() {
            @Override
            public void onLoaded(ResponseData<Notification> result) {
                LogUtils.d("TAG", result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                LogUtils.d("TAG", error);
            }
        });
    }
}
