package vn.gomicorp.seller.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.gomicorp.seller.signin.SignInRequest;
import vn.gomicorp.seller.signup.AccountInfo;
import vn.gomicorp.seller.signup.SignUpRequest;

public interface GomiClient {
    @Headers({"Accept:application/json", "Content-Type:application/json;charset=utf-8"})
    @POST("signup/0")
    Call<ResponseData<AccountInfo>> signUp(@Body SignUpRequest signUpRequest);

    @Headers({"Accept:application/json", "Content-Type:application/json;charset=utf-8"})
    @POST("signin/0")
    Call<ResponseData<AccountInfo>> signIn(@Body SignInRequest signInRequest);
}
