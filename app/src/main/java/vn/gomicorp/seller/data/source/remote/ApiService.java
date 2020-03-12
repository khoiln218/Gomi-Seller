package vn.gomicorp.seller.data.source.remote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.data.AccountInfo;

public interface ApiService {
    @Headers({"Accept:application/json", "Content-Type:application/json;charset=utf-8"})
    @POST("account/signup")
    Call<ResponseData<AccountInfo>> signUp(@Body SignUpRequest signUpRequest);

    @Headers({"Accept:application/json", "Content-Type:application/json;charset=utf-8"})
    @POST("account/login")
    Call<ResponseData<AccountInfo>> signIn(@Body SignInRequest signInRequest);
}
