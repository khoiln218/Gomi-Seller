package vn.gomisellers.apps.data.source.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.gomisellers.apps.data.OrderDataSource;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.model.api.OrderRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.Order;

/**
 * Created by KHOI LE on 5/11/2020.
 */
public class OrderRemoteDataSource implements OrderDataSource {

    @Override
    public void findbyshopid(OrderRequest request, int page, final ResultListener<ResponseData<List<Order>>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<List<Order>>> call = client.findbyshopid(request, page);
        call.enqueue(new Callback<ResponseData<List<Order>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Order>>> call, Response<ResponseData<List<Order>>> response) {
                if (response.body().isStatus()) {
                    callback.onLoaded(response.body());
                } else {
                    callback.onDataNotAvailable(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Order>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }
}
