package vn.gomisellers.apps.data.source.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.gomisellers.apps.data.ProductDataSource;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.model.api.CategoryByIdRequest;
import vn.gomisellers.apps.data.source.model.api.CollectionByIdRequest;
import vn.gomisellers.apps.data.source.model.api.IntroduceRequest;
import vn.gomisellers.apps.data.source.model.api.ProductDetailRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.ShopRequest;
import vn.gomisellers.apps.data.source.model.api.ToggleProductRequest;
import vn.gomisellers.apps.data.source.model.data.Introduce;
import vn.gomisellers.apps.data.source.model.data.Product;

/**
 * Created by KHOI LE on 3/24/2020.
 */
public class ProductRemoteDataSource implements ProductDataSource {

    @Override
    public void introduce(final IntroduceRequest request, final ResultListener<ResponseData<Introduce>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<Introduce>> call = client.introduce(request);
        call.enqueue(new Callback<ResponseData<Introduce>>() {
            @Override
            public void onResponse(Call<ResponseData<Introduce>> call, Response<ResponseData<Introduce>> response) {
                if (response.body().isStatus()) {
                    callback.onLoaded(response.body());
                } else {
                    callback.onDataNotAvailable(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Introduce>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void select(ToggleProductRequest request, final ResultListener<ResponseData<Product>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<Product>> call = client.select(request);
        call.enqueue(new Callback<ResponseData<Product>>() {
            @Override
            public void onResponse(Call<ResponseData<Product>> call, Response<ResponseData<Product>> response) {
                if (response.body().isStatus()) {
                    callback.onLoaded(response.body());
                } else {
                    callback.onDataNotAvailable(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Product>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void findbycollection(CollectionByIdRequest request, int page, final ResultListener<ResponseData<List<Product>>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<List<Product>>> call = client.findbycollection(request, page);
        call.enqueue(new Callback<ResponseData<List<Product>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Product>>> call, Response<ResponseData<List<Product>>> response) {
                if (response.body().isStatus()) {
                    callback.onLoaded(response.body());
                } else {
                    callback.onDataNotAvailable(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Product>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void findbycategory(CategoryByIdRequest request, int page, final ResultListener<ResponseData<List<Product>>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<List<Product>>> call = client.findbycategory(request, page);
        call.enqueue(new Callback<ResponseData<List<Product>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Product>>> call, Response<ResponseData<List<Product>>> response) {
                if (response.body().isStatus()) {
                    callback.onLoaded(response.body());
                } else {
                    callback.onDataNotAvailable(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Product>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void findbyseen(CollectionByIdRequest request, int page, final ResultListener<ResponseData<List<Product>>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<List<Product>>> call = client.findbyseen(request, page);
        call.enqueue(new Callback<ResponseData<List<Product>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Product>>> call, Response<ResponseData<List<Product>>> response) {
                if (response.body().isStatus()) {
                    callback.onLoaded(response.body());
                } else {
                    callback.onDataNotAvailable(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Product>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void findbyid(ProductDetailRequest request, final ResultListener<ResponseData<Product>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<Product>> call = client.findbyid(request);
        call.enqueue(new Callback<ResponseData<Product>>() {
            @Override
            public void onResponse(Call<ResponseData<Product>> call, Response<ResponseData<Product>> response) {
                if (response.body().isStatus()) {
                    callback.onLoaded(response.body());
                } else {
                    callback.onDataNotAvailable(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Product>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void findbyshop(ShopRequest request, int page, final ResultListener<ResponseData<List<Product>>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<List<Product>>> call = client.findbyshop(request, page);
        call.enqueue(new Callback<ResponseData<List<Product>>>() {
            @Override
            public void onResponse(Call<ResponseData<List<Product>>> call, Response<ResponseData<List<Product>>> response) {
                if (response.body().isStatus()) {
                    callback.onLoaded(response.body());
                } else {
                    callback.onDataNotAvailable(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<List<Product>>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }
}
