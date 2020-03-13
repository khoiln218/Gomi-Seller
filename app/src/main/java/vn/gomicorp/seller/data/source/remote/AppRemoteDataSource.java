package vn.gomicorp.seller.data.source.remote;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.gomicorp.seller.data.AppDataSource;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.data.AccountInfo;
import vn.gomicorp.seller.data.source.model.data.Contry;

public class AppRemoteDataSource implements AppDataSource {
    @Override
    public void signin(final SignInRequest request, final ResultListener<AccountInfo> callback) {
        Retrofit retrofit = ApiConfig.createRetrofit(EndPoint.BASE_URL);
        ApiService client = retrofit.create(ApiService.class);
        Call<ResponseData<AccountInfo>> call = client.signIn(request);
        call.enqueue(new Callback<ResponseData<AccountInfo>>() {
            @Override
            public void onResponse(Call<ResponseData<AccountInfo>> call, Response<ResponseData<AccountInfo>> response) {
                try {
                    if (response.body().getStatus())
                        callback.onLoaded(response.body().getResult());
                    else
                        callback.onDataNotAvailable(response.body().getMessage());
                } catch (Exception e) {
                    callback.onDataNotAvailable(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<AccountInfo>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void signup(SignUpRequest request, final ResultListener<AccountInfo> callback) {
        Retrofit retrofit = ApiConfig.createRetrofit(EndPoint.BASE_URL);
        ApiService client = retrofit.create(ApiService.class);
        Call<ResponseData<AccountInfo>> call = client.signUp(request);
        call.enqueue(new Callback<ResponseData<AccountInfo>>() {
            @Override
            public void onResponse(Call<ResponseData<AccountInfo>> call, Response<ResponseData<AccountInfo>> response) {
                try {
                    if (response.body().getStatus())
                        callback.onLoaded(response.body().getResult());
                    else
                        callback.onDataNotAvailable(response.body().getMessage());
                } catch (Exception e) {
                    callback.onDataNotAvailable(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<AccountInfo>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void getContry(ResultListener<List<Contry>> callback) {
        List<Contry> contries = new ArrayList<>();
        contries.add(new Contry(1, "Việt Nam"));
        contries.add(new Contry(2, "Hàn Quốc"));
        contries.add(new Contry(3, "Thái Lan"));
        callback.onLoaded(contries);
    }
}
