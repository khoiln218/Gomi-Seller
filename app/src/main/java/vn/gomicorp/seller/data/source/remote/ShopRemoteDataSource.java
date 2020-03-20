package vn.gomicorp.seller.data.source.remote;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.ShopDataSource;
import vn.gomicorp.seller.data.source.model.api.CreateShopRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.VerifyUrlRequest;
import vn.gomicorp.seller.data.source.model.data.Shop;

/**
 * Created by KHOI LE on 3/19/2020.
 */
public class ShopRemoteDataSource implements ShopDataSource {
    @Override
    public void verifySellerUrl(VerifyUrlRequest request, final ResultListener<ResponseData> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData> call = client.verifySellerUrl(request);
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().isStatus()) {
                    callback.onLoaded(response.body());
                } else
                    callback.onDataNotAvailable(response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void create(final CreateShopRequest request, final ResultListener<ResponseData<Shop>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<Shop>> call = client.createShop(request);
        call.enqueue(new Callback<ResponseData<Shop>>() {
            @Override
            public void onResponse(Call<ResponseData<Shop>> call, Response<ResponseData<Shop>> response) {
                try {
                    if (response.body().isStatus())
                        callback.onLoaded(response.body());
                    else callback.onDataNotAvailable(response.body().getMessage());
                } catch (Exception e) {
                    callback.onDataNotAvailable(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Shop>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }
}
