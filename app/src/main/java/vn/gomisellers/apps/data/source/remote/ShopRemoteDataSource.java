package vn.gomisellers.apps.data.source.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.ShopDataSource;
import vn.gomisellers.apps.data.source.model.api.CategoryByIdRequest;
import vn.gomisellers.apps.data.source.model.api.CreateShopRequest;
import vn.gomisellers.apps.data.source.model.api.MegaCategoryRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.ShopRequest;
import vn.gomisellers.apps.data.source.model.api.UpdateShopRequest;
import vn.gomisellers.apps.data.source.model.api.VerifyUrlRequest;
import vn.gomisellers.apps.data.source.model.data.Category;
import vn.gomisellers.apps.data.source.model.data.Shop;

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

    @Override
    public void findcatebytype(CategoryByIdRequest request, final ResultListener<ResponseData<List<Category>>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<List<Category>>> call = client.findcatebytype(request);
        call.enqueue(new Callback<ResponseData<List<Category>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Category>>> call, Response<ResponseData<List<Category>>> response) {
                try {
                    if (response.body().isStatus())
                        callback.onLoaded(response.body());
                    else callback.onDataNotAvailable(response.body().getMessage());
                } catch (Exception e) {
                    callback.onDataNotAvailable(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Category>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void megacategory(MegaCategoryRequest request, final ResultListener<ResponseData<List<Category>>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<List<Category>>> call = client.megacategory(request);
        call.enqueue(new Callback<ResponseData<List<Category>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Category>>> call, Response<ResponseData<List<Category>>> response) {
                try {
                    if (response.body().isStatus())
                        callback.onLoaded(response.body());
                    else callback.onDataNotAvailable(response.body().getMessage());
                } catch (Exception e) {
                    callback.onDataNotAvailable(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Category>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void findbyid(ShopRequest request, final ResultListener<ResponseData<Shop>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<Shop>> call = client.findbyid(request);
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

    @Override
    public void updateinfo(UpdateShopRequest request, final ResultListener<ResponseData<Shop>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<Shop>> call = client.updateinfo(request);
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
