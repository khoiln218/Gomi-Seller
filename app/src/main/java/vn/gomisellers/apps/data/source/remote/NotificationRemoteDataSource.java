package vn.gomisellers.apps.data.source.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.gomisellers.apps.data.NotificationDataSource;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.model.api.NotificationRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.Notification;

/**
 * Created by KHOI LE on 5/15/2020.
 */
public class NotificationRemoteDataSource implements NotificationDataSource {
    @Override
    public void findby(NotificationRequest request, int page, final ResultListener<ResponseData<List<Notification>>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<List<Notification>>> call = client.findby(request, page);
        call.enqueue(new Callback<ResponseData<List<Notification>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Notification>>> call, Response<ResponseData<List<Notification>>> response) {
                if (response.isSuccessful()) {
                    callback.onLoaded(response.body());
                } else {
                    callback.onDataNotAvailable(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Notification>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }
}
