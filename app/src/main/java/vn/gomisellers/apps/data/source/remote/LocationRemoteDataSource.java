package vn.gomisellers.apps.data.source.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.gomisellers.apps.data.LocationDataSource;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.Location;

/**
 * Created by KHOI LE on 3/18/2020.
 */
public class LocationRemoteDataSource implements LocationDataSource {
    @Override
    public void getLocationCountry(final ResultListener<ResponseData<List<Location>>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<List<Location>>> call = client.getLocationCountry();
        call.enqueue(new Callback<ResponseData<List<Location>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Location>>> call, Response<ResponseData<List<Location>>> response) {
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
            public void onFailure(Call<ResponseData<List<Location>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void getLocationProvince(int id, final ResultListener<ResponseData<List<Location>>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<List<Location>>> call = client.getLocationProvinceBy(id);
        call.enqueue(new Callback<ResponseData<List<Location>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Location>>> call, Response<ResponseData<List<Location>>> response) {
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
            public void onFailure(Call<ResponseData<List<Location>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void getLocationDistrict(int id, final ResultListener<ResponseData<List<Location>>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<List<Location>>> call = client.getLocationDistrict(id);
        call.enqueue(new Callback<ResponseData<List<Location>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Location>>> call, Response<ResponseData<List<Location>>> response) {
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
            public void onFailure(Call<ResponseData<List<Location>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }
}
