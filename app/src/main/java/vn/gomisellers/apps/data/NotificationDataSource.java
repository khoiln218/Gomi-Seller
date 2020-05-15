package vn.gomisellers.apps.data;

import java.util.List;

import vn.gomisellers.apps.data.source.model.api.NotificationRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.Notification;

/**
 * Created by KHOI LE on 5/15/2020.
 */
public interface NotificationDataSource {
    void findby(NotificationRequest request, int page, ResultListener<ResponseData<List<Notification>>> callback);
}
