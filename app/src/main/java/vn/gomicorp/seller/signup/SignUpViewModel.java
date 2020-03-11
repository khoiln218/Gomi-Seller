package vn.gomicorp.seller.signup;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.gomicorp.seller.SingleLiveEvent;
import vn.gomicorp.seller.data.GomiClient;
import vn.gomicorp.seller.data.ResponseData;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Utils;

public class SignUpViewModel extends ViewModel {
    private String TAG = getClass().getSimpleName();
    private SignUpRequest signUpRequest = new SignUpRequest();

    public MutableLiveData<String> fullName = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> contryId = new MutableLiveData<>();
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public final SingleLiveEvent<Void> mSignInCommand = new SingleLiveEvent<>();

    public void signUp() {
        signUpRequest.setCountryId(Integer.parseInt(contryId.getValue()));
        signUpRequest.setEmail(email.getValue());
        signUpRequest.setFullName(fullName.getValue());
        signUpRequest.setPhoneNumber(phoneNumber.getValue());
        signUpRequest.setPassword(password.getValue());
        signUpRequest.setDeviceToken(Utils.getDeviceToken());
        signUpRequest.setDeviceVersion(Utils.getDeviceVersion());
        requestSignUp(signUpRequest);
    }

    public void signIn() {
        gotoSignIn();
    }

    private void requestSignUp(SignUpRequest signUpRequest) {
        Retrofit retrofit = Utils.createRetrofit(GomiConstants.BASE_URL);
        GomiClient client = retrofit.create(GomiClient.class);
        Call<ResponseData<AccountInfo>> call = client.signUp(signUpRequest);
        call.enqueue(new Callback<ResponseData<AccountInfo>>() {
            @Override
            public void onResponse(Call<ResponseData<AccountInfo>> call, Response<ResponseData<AccountInfo>> response) {
                try {
                    ResponseData<AccountInfo> body = response.body();
                    Log.d(TAG, "onResponse: " + new Gson().toJson(body.getResult()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseData<AccountInfo>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                t.printStackTrace();
            }
        });
    }

    private void gotoSignIn() {
        mSignInCommand.call();
    }

    public SingleLiveEvent<Void> getSignInCommand() {
        return mSignInCommand;
    }
}
