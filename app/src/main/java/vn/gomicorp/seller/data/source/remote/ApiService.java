package vn.gomicorp.seller.data.source.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import vn.gomicorp.seller.data.source.model.api.CategoryByIdRequest;
import vn.gomicorp.seller.data.source.model.api.CollectionByIdRequest;
import vn.gomicorp.seller.data.source.model.api.CreateShopRequest;
import vn.gomicorp.seller.data.source.model.api.ForgetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.IntroduceRequest;
import vn.gomicorp.seller.data.source.model.api.ResetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.api.ToggleProductRequest;
import vn.gomicorp.seller.data.source.model.api.VerifyPhoneNumberRequest;
import vn.gomicorp.seller.data.source.model.api.VerifyUrlRequest;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.Introduce;
import vn.gomicorp.seller.data.source.model.data.Location;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.data.source.model.data.Shop;

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
    Call<ResponseData<List<Location>>> getLocationCountry();

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @GET("location/getprovinceby/countryid={id}")
    Call<ResponseData<List<Location>>> getLocationProvinceBy(@Path("id") int id);

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @GET("location/getdistrictby/provinceid={id}")
    Call<ResponseData<List<Location>>> getLocationDistrict(@Path("id") int id);

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @POST("shop/verifyurl")
    Call<ResponseData> verifySellerUrl(@Body VerifyUrlRequest request);

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @POST("shop/create")
    Call<ResponseData<Shop>> createShop(@Body CreateShopRequest request);

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @POST("product/introduce")
    Call<ResponseData<Introduce>> introduce(@Body IntroduceRequest request);

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @POST("product/select")
    Call<ResponseData<Product>> select(@Body ToggleProductRequest request);

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @POST("product/findbycollection/page={page}")
    Call<ResponseData<List<Product>>> findbycollection(@Body CollectionByIdRequest request, @Path("page") int page);

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @POST("product/findbycategory/page={page}")
    Call<ResponseData<List<Product>>> findbycategory(@Body CategoryByIdRequest request, @Path("page") int page);

    @Headers({"Accept:application/json\", \"Content-Type:application/json;charset=utf-8"})
    @POST("utilities/findcatebytype")
    Call<ResponseData<List<Category>>> findcatebytype(@Body CategoryByIdRequest request);
}
