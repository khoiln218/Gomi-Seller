package vn.gomicorp.seller.signin;

import android.util.Log;
import android.view.View;

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
import vn.gomicorp.seller.signup.AccountInfo;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Utils;

public class SignInViewModel extends ViewModel {
    private String TAG = getClass().getSimpleName();
    private SignInRequest signInRequest = new SignInRequest();

    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Integer> loadding = new MutableLiveData<>();

    private final SingleLiveEvent<Void> mSignInCommand = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> mSignUpCommand = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> mForgetPasswordCommand = new SingleLiveEvent<>();

    public void signIn() {
        loadding.setValue(View.VISIBLE);
        signInRequest.setUserName(userName.getValue());
        signInRequest.setPassword(password.getValue());
        signInRequest.setDeviceToken(Utils.getDeviceToken());
        signInRequest.setDeviceVersion(Utils.getDeviceVersion());
        requestSignIn(signInRequest);
    }

    public void forgetPassword() {
        gotoForgetPassword();
    }

    private void gotoForgetPassword() {
        mForgetPasswordCommand.call();
    }

    public void signUp() {
        gotoSignUp();
    }

    public void gotoSignIn() {
        mSignInCommand.call();
    }

    public void gotoSignUp() {
        mSignUpCommand.call();
    }

    public SingleLiveEvent<Void> getSignInCommand() {
        return mSignInCommand;
    }

    public SingleLiveEvent<Void> getSignUpCommand() {
        return mSignUpCommand;
    }

    public SingleLiveEvent<Void> getForgetPasswordCommand() {
        return mForgetPasswordCommand;
    }

    private void requestSignIn(SignInRequest signInRequest) {
        Retrofit retrofit = Utils.createRetrofit(GomiConstants.BASE_URL);
        GomiClient client = retrofit.create(GomiClient.class);
        Call<ResponseData<AccountInfo>> call = client.signIn(signInRequest);
        call.enqueue(new Callback<ResponseData<AccountInfo>>() {
            @Override
            public void onResponse(Call<ResponseData<AccountInfo>> call, Response<ResponseData<AccountInfo>> response) {
                ResponseData<AccountInfo> body = response.body();
                Log.d(TAG, "onResponse: " + new Gson().toJson(body.getResult()));
                if (body.getStatus()) {
                    Log.d(TAG, "login succsess");
                    gotoSignIn();
                } else
                    Log.d(TAG, body.getMessage());
            }

            @Override
            public void onFailure(Call<ResponseData<AccountInfo>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                t.printStackTrace();
            }
        });
    }
}
