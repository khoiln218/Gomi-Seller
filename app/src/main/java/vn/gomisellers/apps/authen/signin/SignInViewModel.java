package vn.gomisellers.apps.authen.signin;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.EappsApplication;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.AccountRepository;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.local.prefs.AppPreferences;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.SignInRequest;
import vn.gomisellers.apps.data.source.model.data.Account;
import vn.gomisellers.apps.data.source.remote.ResultCode;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.utils.Inputs;
import vn.gomisellers.apps.utils.Strings;
import vn.gomisellers.apps.utils.Utils;

public class SignInViewModel extends BaseViewModel<SignInEvent> implements TextView.OnEditorActionListener {

    private AccountRepository mAppRepository = AccountRepository.getInstance();
    private AppPreferences mAppPreferences = EappsApplication.getPreferences();

    public MutableLiveData<String> userName = new MutableLiveData<>();
    public MutableLiveData<String> userNameError = new MutableLiveData<>();
    public MutableLiveData<Boolean> userNameEnableError = new MutableLiveData<>();
    public MutableLiveData<Boolean> userNameRequestFocus = new MutableLiveData<>();

    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> passwordError = new MutableLiveData<>();
    public MutableLiveData<Boolean> passwordEnableError = new MutableLiveData<>();
    public MutableLiveData<Boolean> passwordRequestFocus = new MutableLiveData<>();

    public MutableLiveData<Boolean> enableLoginBtn = new MutableLiveData<>();

    public void signIn() {
        hideKeyboard();
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

    public void afterTextChanged() {
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

    public void forgetPassword() {
        hideKeyboard();
        getCmd().call(new SignInEvent(SignInEvent.FORGET_PASSWORD));
    }

    public void signUp() {
        hideKeyboard();
        getCmd().call(new SignInEvent(SignInEvent.GOTO_SIGN_UP));
    }

    private void hideKeyboard() {
        getCmd().call(new SignInEvent(SignInEvent.HIDE_KEYBOARD));
    }

    private void userNameError() {
        userNameError.setValue(EappsApplication.getInstance().getString(R.string.err_input_username));
        userNameRequestFocus.setValue(true);
    }

    private void userNameSuccess() {
        userNameEnableError.setValue(false);
    }

    private void passwordError() {
        passwordError.setValue(EappsApplication.getInstance().getString(R.string.err_input_password));
        passwordEnableError.setValue(true);
    }

    private void passwordSuccess() {
        passwordEnableError.setValue(false);
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
                if (res.getCode() == ResultCode.CODE_OK) {
                    saveAccount(res.getResult());
                    loginSuccess();
                } else
                    showToast(res.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                showToast(error);
            }
        });
    }

    private void loginSuccess() {
        getCmd().call(new SignInEvent(SignInEvent.LOG_IN_SUCCESS));
    }

    private void saveAccount(Account account) {
        mAppPreferences.setAccount(account);
    }

    void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GomiConstants.REQUEST_SIGN_UP:
                    loginSuccess();
                    break;

                case GomiConstants.REQUEST_FORGET_PASSWORD:
                    if (data != null && data.getStringExtra(GomiConstants.EXTRA_ID) != null) {
                        forgetSuccess(data.getStringExtra(GomiConstants.EXTRA_ID));
                    }
                    break;
                case GomiConstants.REQUEST_RESET_PASSWORD:
                    showToast(EappsApplication.getInstance().getString(R.string.change_password_succsess));
                    break;
            }
        }
    }

    private void forgetSuccess(String userId) {
        SignInEvent event = new SignInEvent(SignInEvent.RESET_PASSWORD);
        event.setData(userId);
        getCmd().call(event);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            signIn();
        }
        return false;
    }
}
