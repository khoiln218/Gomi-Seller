package vn.gomicorp.seller.data.source.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.gomicorp.seller.data.LocationDataSource;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.data.Country;

/**
 * Created by KHOI LE on 3/18/2020.
 */
public class LocationRemoteDataSource implements LocationDataSource {
    @Override
    public void getLocationCountry(final ResultListener<ResponseData<List<Country>>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<List<Country>>> call = client.getLocationCountry();
        call.enqueue(new Callback<ResponseData<List<Country>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Country>>> call, Response<ResponseData<List<Country>>> response) {
                try {
                    if (response.body().isStatus())
                        callback.onLoaded(response.body());
                    else
                        callback.onDataNotAvailable(response.body().getMessage());
                } catch (Exception e) {
                    callback.onDataNotAvailable(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Country>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }
}
