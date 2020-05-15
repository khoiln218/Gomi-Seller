package vn.gomisellers.apps.main;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.data.NotificationRepository;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.model.api.NotificationRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.NotificationNew;
import vn.gomisellers.apps.data.source.remote.ResultCode;
import vn.gomisellers.apps.utils.LogUtils;

/**
 * Created by KHOI LE on 5/15/2020.
 */
public class MainViewModel extends BaseViewModel<MainEvent> {
    private NotificationRepository mNotificationRepository;

    int unreadNotify = 0;

    public MainViewModel() {
        mNotificationRepository = NotificationRepository.getInstance();
    }

    void requestNotificationBadges() {
        NotificationRequest request = new NotificationRequest();
        mNotificationRepository.countnew(request, new ResultListener<ResponseData<NotificationNew>>() {
            @Override
            public void onLoaded(ResponseData<NotificationNew> result) {
                if (result.getCode() == ResultCode.CODE_OK) {
                    unreadNotify = result.getResult().getCountNew();
                    updateNotificationBadges();
                }
                LogUtils.d("TAG", result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                LogUtils.d("TAG", error);
            }
        });

    }

    private void updateNotificationBadges() {
        MainEvent<Integer> event = new MainEvent<>(MainEvent.NOTIFY);
        event.setData(unreadNotify);
        getCmd().call(event);
    }
}
