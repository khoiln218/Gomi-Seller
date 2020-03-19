package vn.gomicorp.seller.data.source.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import vn.gomicorp.seller.data.source.model.api.ForgetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.api.VerifyPhoneNumberRequest;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.data.source.model.data.Country;

public interface ApiService {
    @Headers({"Accept:application/json", "Content-Type:application/json;charset=utf-8"})
    @POST("account/signup")
    Call<ResponseData<Account>> signUp(@Body SignUpRequest signUpRequest);

    @Headers({"Accept:application/json", "Content-Type:application/json;charset=utf-8"})
    @POST("account/login")
    Call<ResponseData<Account>> signIn(@Body SignInRequest signInRequest);

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @POST("account/forgotpassword")
    Call<ResponseData<Account>> forgetPwd(@Body ForgetPwdRequest request);

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @POST("account/resetpassword")
    Call<ResponseData<Account>> resetPwd(@Body ResetPwdRequest request);

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @POST("account/verify")
    Call<ResponseData<Account>> verifyPhoneNumber(@Body VerifyPhoneNumberRequest request);

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @GET("location/getcountry")
    Call<ResponseData<List<Country>>> getLocationCountry();
}
