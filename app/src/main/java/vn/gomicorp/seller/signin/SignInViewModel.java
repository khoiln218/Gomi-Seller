package vn.gomicorp.seller.signin;

import android.text.Editable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.data.AccountRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.local.prefs.AppPreferences;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.utils.Inputs;
import vn.gomicorp.seller.utils.Strings;
import vn.gomicorp.seller.utils.Utils;

public class SignInViewModel extends ViewModel {
    private final int LOGIN_SUCCESS = 200;
    private final int ACCOUNT_LOCK = 1021;

    private AccountRepository mAppRepository = AccountRepository.getInstance();
    private AppPreferences mAppPreferences = EappsApplication.getPreferences();

    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadding = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableLoginBtn = new MutableLiveData<>();

    public SignInViewModel() {
    }

    private final MultableLiveEvent<SignInEvent> mLogInCommand = new MultableLiveEvent<>();

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

    public MultableLiveEvent<SignInEvent> getLoginCommand() {
        return mLogInCommand;
    }

    private void requestLogin() {
        showProgressing();
        SignInRequest request = new SignInRequest();
        request.setUserName(userName.getValue());
        request.setPassword(password.getValue());
        request.setDeviceToken(Utils.getDeviceToken());
        request.setDeviceVersion(Utils.getDeviceVersion());
        mAppRepository.signin(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> res) {
                hideProgressing();
                if (res.getCode() == LOGIN_SUCCESS) {
                    saveAccount(res.getResult());
                    loginSuccess();
                } else if (res.getCode() == ACCOUNT_LOCK) {
                    saveAccount(res.getResult());
                    accountLock(res.getMessage());
                } else
                    loginFalse(res.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                loginFalse(error);
            }
        });
    }

    private void accountLock(String msg) {
        mLogInCommand.call(new SignInEvent(SignInEvent.ACCOUNT_LOCK, msg));
    }

    private void saveAccount(Account account) {
        mAppPreferences.setAccount(account);
    }
}
