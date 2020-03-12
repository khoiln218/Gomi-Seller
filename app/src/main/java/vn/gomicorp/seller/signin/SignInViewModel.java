package vn.gomicorp.seller.signin;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.gomicorp.seller.event.MultibleLiveEvent;
import vn.gomicorp.seller.data.AppRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.data.AccountInfo;
import vn.gomicorp.seller.utils.Utils;

public class SignInViewModel extends ViewModel {
    private SignInRequest signInRequest = new SignInRequest();
    private AppRepository mAppRepository = AppRepository.getInstance();

    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Integer> loadding = new MutableLiveData<>();

    private final MultibleLiveEvent<SignInEvent> mLogInCommand = new MultibleLiveEvent<>();

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

    public void signUp() {
        gotoSignUp();
    }

    private void gotoForgetPassword() {
        mLogInCommand.call(new SignInEvent(SignInEvent.FORGET_PASSWORD));
    }

    public void loginSuccess() {
        mLogInCommand.call(new SignInEvent(SignInEvent.LOG_IN_SUCCESS));
    }

    private void loginFalse(String error) {
        mLogInCommand.call(new SignInEvent(SignInEvent.LOG_IN_FALSE, error));
    }

    public void gotoSignUp() {
        mLogInCommand.call(new SignInEvent(SignInEvent.GOTO_SIGN_UP));
    }

    public MultibleLiveEvent<SignInEvent> getLoginCommand() {
        return mLogInCommand;
    }

    private void requestSignIn(SignInRequest request) {
        mAppRepository.signin(request, new ResultListener<AccountInfo>() {
            @Override
            public void onLoaded(AccountInfo result) {
                loginSuccess();
            }

            @Override
            public void onDataNotAvailable(String error) {
                loginFalse(error);
            }
        });
    }
}
