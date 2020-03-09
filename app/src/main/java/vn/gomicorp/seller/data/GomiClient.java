package vn.gomicorp.seller.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.gomicorp.seller.signup.models.UserInfo;

public interface GomiClient {
    @Headers({"Accept:application/json", "Content-Type:application/json;charset=utf-8"})
    @POST("signup/0")
    Call<ResponseData<Object>> signUp(@Body UserInfo userInfo);
}
