package vn.gomicorp.seller.data.source.remote;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.gomicorp.seller.data.AccountDataSource;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.ForgetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.data.Account;

public class AccountRemoteDataSource implements AccountDataSource {
    @Override
    public void signin(final SignInRequest request, final ResultListener<ResponseData<Account>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<Account>> call = client.signIn(request);
        call.enqueue(new Callback<ResponseData<Account>>() {
            @Override
            public void onResponse(Call<ResponseData<Account>> call, Response<ResponseData<Account>> response) {
                try {
                    if (response.body().isSuccess())
                        callback.onLoaded(response.body());
                    else
                        callback.onDataNotAvailable(response.body().getMessage());
                } catch (Exception e) {
                    callback.onDataNotAvailable(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Account>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void signup(SignUpRequest request, final ResultListener<ResponseData<Account>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<Account>> call = client.signUp(request);
        call.enqueue(new Callback<ResponseData<Account>>() {
            @Override
            public void onResponse(Call<ResponseData<Account>> call, Response<ResponseData<Account>> response) {
                try {
                    if (response.body().isSuccess())
                        callback.onLoaded(response.body());
                    else
                        callback.onDataNotAvailable(response.body().getMessage());
                } catch (Exception e) {
                    callback.onDataNotAvailable(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Account>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void forgetPwd(ForgetPwdRequest request, final ResultListener<ResponseData<Account>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<Account>> call = client.forgetPwd(request);
        call.enqueue(new Callback<ResponseData<Account>>() {
            @Override
            public void onResponse(Call<ResponseData<Account>> call, Response<ResponseData<Account>> response) {
                try {
                    if (response.body().isSuccess())
                        callback.onLoaded(response.body());
                    else
                        callback.onDataNotAvailable(response.body().getMessage());
                } catch (Exception e) {
                    callback.onDataNotAvailable(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Account>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void resetPwd(final ResetPwdRequest request, final ResultListener<ResponseData<Account>> callback) {
        ApiService client = ApiConfig.getClient();
        Call<ResponseData<Account>> call = client.resetPwd(request);
        call.enqueue(new Callback<ResponseData<Account>>() {
            @Override
            public void onResponse(Call<ResponseData<Account>> call, Response<ResponseData<Account>> response) {
                try {
                    if (response.body().isSuccess())
                        callback.onLoaded(null);
                    else
                        callback.onDataNotAvailable(response.body().getMessage());
                } catch (Exception e) {
                    callback.onDataNotAvailable(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<Account>> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }
}
