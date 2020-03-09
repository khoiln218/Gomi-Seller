package vn.gomicorp.seller.signup.viewmodels;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.gomicorp.seller.data.GomiClient;
import vn.gomicorp.seller.data.ResponseData;
import vn.gomicorp.seller.signup.models.UserInfo;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Utils;

public class SignUpViewModel extends ViewModel {
    private String TAG = getClass().getSimpleName();
    private UserInfo userInfo = new UserInfo();

    public void dangky() {
        Log.d(TAG, "dangky: ");
        userInfo.setBirthDay("27/11/1988");
        userInfo.setCountryId(84);
        userInfo.setEmail("khoiln218@gmail.com");
        userInfo.setFullName("Khôi Lê");
        userInfo.setGender((byte) 0);
        userInfo.setPhoneNumber("0937001038");
        userInfo.setDeviceToken(Utils.getDeviceToken());
        userInfo.setDeviceVersion(Utils.getDeviceVersion());
        requestSignUp(userInfo);
    }

    private void requestSignUp(UserInfo userInfo) {
        Retrofit retrofit = Utils.createRetrofit(GomiConstants.BASE_URL);
        GomiClient client = retrofit.create(GomiClient.class);
        Call<ResponseData<Object>> call = client.signUp(userInfo);
        call.enqueue(new Callback<ResponseData<Object>>() {
            @Override
            public void onResponse(Call<ResponseData<Object>> call, Response<ResponseData<Object>> response) {
                ResponseData<Object> body = response.body();
                Log.d(TAG, "onResponse: " + body.getData().toString());
            }

            @Override
            public void onFailure(Call<ResponseData<Object>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
