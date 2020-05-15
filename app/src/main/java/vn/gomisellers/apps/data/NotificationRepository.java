package vn.gomisellers.apps.data;

import java.util.List;

import vn.gomisellers.apps.data.source.model.api.NotificationRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.Notification;
import vn.gomisellers.apps.data.source.model.data.NotificationNew;
import vn.gomisellers.apps.data.source.remote.NotificationRemoteDataSource;

/**
 * Created by KHOI LE on 5/15/2020.
 */
public class NotificationRepository implements NotificationDataSource {
    private volatile static NotificationRepository INSTANCE = null;
    private NotificationDataSource mNotificationDataSource;

    private NotificationRepository() {
        mNotificationDataSource = new NotificationRemoteDataSource();
    }

    public static NotificationRepository getInstance() {
        if (INSTANCE == null) {
            synchronized (NotificationRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NotificationRepository();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void findby(NotificationRequest request, int page, ResultListener<ResponseData<List<Notification>>> callback) {
        mNotificationDataSource.findby(request, page, callback);
    }

    @Override
    public void countnew(NotificationRequest request, ResultListener<ResponseData<NotificationNew>> callback) {
        mNotificationDataSource.countnew(request, callback);
    }

    @Override
    public void updatestatus(NotificationRequest request, ResultListener<ResponseData<Notification>> callback) {
        mNotificationDataSource.updatestatus(request, callback);
    }
}
