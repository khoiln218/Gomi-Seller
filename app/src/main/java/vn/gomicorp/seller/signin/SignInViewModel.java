package vn.gomicorp.seller.signin;

import android.text.Editable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.gomicorp.seller.data.AppRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.data.AccountInfo;
import vn.gomicorp.seller.event.MultibleLiveEvent;
import vn.gomicorp.seller.utils.Inputs;
import vn.gomicorp.seller.utils.Strings;
import vn.gomicorp.seller.utils.Utils;

public class SignInViewModel extends ViewModel {
    private AppRepository mAppRepository = AppRepository.getInstance();

    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadding = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableLoginBtn = new MutableLiveData<>();

    public SignInViewModel() {
    }

    private final MultibleLiveEvent<SignInEvent> mLogInCommand = new MultibleLiveEvent<>();

    public void signIn() {
        submitForm();
    }

    private void submitForm() {
        if (!Inputs.validateEmail(userName.getValue()) && !Inputs.validatePhoneNumber(userName.getValue())) {
            userNameError();
            return;
        } else {
            userNameSuccess();
        }

        if (!Inputs.validatePassword(password.getValue())) {
            passwordError();
            return;
        } else {
            passwordSuccess();
        }

        requestLogin();
    }

    public void afterTextChanged(Editable s) {
        if (checkLengthUserName() && checkLengthPwd())
            enableLoginBtn.setValue(true);
        else
            enableLoginBtn.setValue(false);
    }

    private boolean checkLengthPwd() {
        return !Strings.isNullOrEmpty(password.getValue()) && password.getValue().length() > 3;
    }

    private boolean checkLengthUserName() {
        return !Strings.isNullOrEmpty(userName.getValue()) && userName.getValue().length() > 3;
    }

    private void showProgressing() {
        loadding.setValue(true);
    }

    private void hideProgressing() {
        loadding.setValue(false);
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

    public void userNameError() {
        mLogInCommand.call(new SignInEvent(SignInEvent.USERNAME_INPUT_ERROR));
    }

    public void userNameSuccess() {
        mLogInCommand.call(new SignInEvent(SignInEvent.USERNAME_INPUT_SUCCESS));
    }

    public void passwordError() {
        mLogInCommand.call(new SignInEvent(SignInEvent.PASSWORD_INPUT_ERROR));
    }

    public void passwordSuccess() {
        mLogInCommand.call(new SignInEvent(SignInEvent.PASSWORD_INPUT_SUCCESS));
    }

    public MultibleLiveEvent<SignInEvent> getLoginCommand() {
        return mLogInCommand;
    }

    private void requestLogin() {
        showProgressing();
        SignInRequest request = new SignInRequest();
        request.setUserName(userName.getValue());
        request.setPassword(password.getValue());
        request.setDeviceToken(Utils.getDeviceToken());
        request.setDeviceVersion(Utils.getDeviceVersion());
        mAppRepository.signin(request, new ResultListener<AccountInfo>() {
            @Override
            public void onLoaded(AccountInfo result) {
                hideProgressing();
                loginSuccess();
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                loginFalse(error);
            }
        });
    }
}
