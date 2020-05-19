package vn.gomisellers.apps.authen.signup;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.EappsApplication;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.adapter.LocationAdapter;
import vn.gomisellers.apps.data.AccountRepository;
import vn.gomisellers.apps.data.LocationRepository;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.local.prefs.AppPreferences;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.SignUpRequest;
import vn.gomisellers.apps.data.source.model.api.VerifyPhoneNumberRequest;
import vn.gomisellers.apps.data.source.model.data.Account;
import vn.gomisellers.apps.data.source.model.data.Location;
import vn.gomisellers.apps.data.source.remote.ResultCode;
import vn.gomisellers.apps.utils.Inputs;
import vn.gomisellers.apps.utils.Utils;

public class SignUpViewModel extends BaseViewModel<SignUpEvent> implements TextView.OnEditorActionListener {
    private AccountRepository accountRepository = AccountRepository.getInstance();
    private LocationRepository locationRepository = LocationRepository.getInstance();
    private AppPreferences mAppPreferences = EappsApplication.getPreferences();

    public MutableLiveData<String> fullName = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> confirmPassword = new MutableLiveData<>();
    public MutableLiveData<String> certificationCode = new MutableLiveData<>();

    public MutableLiveData<String> errorFullNameMsg = new MutableLiveData<>();
    public MutableLiveData<String> errorEmailMsg = new MutableLiveData<>();
    public MutableLiveData<String> errorPhoneNumberMsg = new MutableLiveData<>();
    public MutableLiveData<String> errorPwdMsg = new MutableLiveData<>();
    public MutableLiveData<String> errorConfirmPwdMsg = new MutableLiveData<>();
    public MutableLiveData<String> errorOTPMsg = new MutableLiveData<>();

    public MutableLiveData<Boolean> errorEnableFullName = new MutableLiveData<>();
    public MutableLiveData<Boolean> errorEnableEmail = new MutableLiveData<>();
    public MutableLiveData<Boolean> errorEnablePhoneNumber = new MutableLiveData<>();
    public MutableLiveData<Boolean> errorEnablePwd = new MutableLiveData<>();
    public MutableLiveData<Boolean> errorEnableConfirmPwd = new MutableLiveData<>();
    public MutableLiveData<Boolean> errorEnableOTP = new MutableLiveData<>();

    public MutableLiveData<Boolean> requestFocusFullName = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestFocusEmail = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestFocusPhoneNumber = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestFocusPwd = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestFocusConfirmPwd = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestFocusOTP = new MutableLiveData<>();

    public MutableLiveData<Boolean> countDownIsShow = new MutableLiveData<>();
    public MutableLiveData<Boolean> verifyIsShow = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableBtnSigup = new MutableLiveData<>();
    public MutableLiveData<String> countDown = new MutableLiveData<>();

    public MutableLiveData<LocationAdapter> locationAdapter = new MutableLiveData<>();

    private LocationAdapter adapter;
    private List<Location> countries;
    private int countryCode;
    private Timer timer;

    public SignUpViewModel() {
        showVerifyBtn();
        countries = new ArrayList<>();
        adapter = new LocationAdapter(countries);
        locationAdapter.setValue(adapter);
    }

    public void signUp() {
        hideKeyboard();
        submitForm();
    }

    public void verify() {
        hideKeyboard();
        submitCertificationCode();
    }

    private void submitCertificationCode() {
        if (!TextUtils.isEmpty(phoneNumber.getValue())) {
            phoneNumberSuccess();
            requestVerifyPhoneNumber();
        } else {
            phoneNumberError();
        }
    }

    void requestCountryId() {
        showProgressing();
        locationRepository.getLocationCountry(new ResultListener<ResponseData<List<Location>>>() {
            @Override
            public void onLoaded(ResponseData<List<Location>> result) {
                hideProgressing();
                if (result.getCode() == ResultCode.CODE_OK) {
                    countries = result.getResult();
                    updateCountry();
                } else {
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                showToast(error);
            }
        });
    }

    private void updateCountry() {
        adapter.setData(countries);
    }

    private void requestVerifyPhoneNumber() {
        showCountDown();
        VerifyPhoneNumberRequest request = new VerifyPhoneNumberRequest();
        request.setPhoneNumber(phoneNumber.getValue());
        accountRepository.verifyPhoneNumber(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                showToast(result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                showToast(error);
            }
        });
    }

    private void showVerifyBtn() {
        verifyIsShow.postValue(true);
        countDownIsShow.postValue(false);
        countDown.postValue("");
        if (timer != null)
            timer.cancel();
    }

    private void showCountDown() {
        timer = new Timer();
        verifyIsShow.setValue(false);
        countDownIsShow.setValue(true);
        timer.schedule(new TimerTask() {
            int start = 30;

            @Override
            public void run() {
                start--;
                countDown.postValue(String.format(EappsApplication.getInstance().getString(R.string.msg_resend), start));
                if (start == 0) {
                    showVerifyBtn();
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    private void submitForm() {
        if (!checkLengthName()) {
            fullNameError();
            return;
        } else {
            fullNameSuccess();
        }

        if (!Inputs.validateEmail(email.getValue())) {
            emailError();
            return;
        } else {
            emailSuccess();
        }

        if (!Inputs.validatePhoneNumber(phoneNumber.getValue())) {
            phoneNumberError();
            return;
        } else {
            phoneNumberSuccess();
        }

        if (!checkLengthVerifyCode()) {
            verifyCodeError();
            return;
        } else {
            verifyCodeSuccess();
        }

        if (!Inputs.validatePassword(password.getValue())) {
            passwordError();
            return;
        } else {
            passwordSuccess();
        }

        if (!confirmPassword())
            return;

        requestSignUp();
    }

    private void verifyCodeSuccess() {
        errorEnableOTP.setValue(false);
    }

    private void verifyCodeError() {
        errorOTPMsg.setValue(EappsApplication.getInstance().getString(R.string.err_input_verify_code));
        requestFocusOTP.setValue(true);
    }

    private void fullNameSuccess() {
        errorEnableFullName.setValue(false);
    }

    private void fullNameError() {
        errorFullNameMsg.setValue(EappsApplication.getInstance().getString(R.string.err_input_full_name));
        requestFocusFullName.setValue(true);
    }

    private boolean confirmPassword() {
        if (!TextUtils.equals(confirmPassword.getValue(), password.getValue())) {
            errorConfirmPwdMsg.setValue(EappsApplication.getInstance().getString(R.string.err_password_confirm));
            requestFocusConfirmPwd.setValue(true);
            return false;
        }

        errorEnableConfirmPwd.setValue(false);
        return true;
    }

    private void passwordSuccess() {
        errorEnablePwd.setValue(false);
    }

    private void passwordError() {
        errorPwdMsg.setValue(EappsApplication.getInstance().getString(R.string.err_input_password));
        requestFocusPwd.setValue(true);
    }

    private void phoneNumberSuccess() {
        errorEnablePhoneNumber.setValue(false);
    }

    private void phoneNumberError() {
        errorPhoneNumberMsg.setValue(EappsApplication.getInstance().getString(R.string.err_input_phone_number));
        requestFocusPhoneNumber.setValue(true);
    }

    private void emailSuccess() {
        errorEnableEmail.setValue(false);
    }

    private void emailError() {
        errorEmailMsg.setValue(EappsApplication.getInstance().getString(R.string.err_input_email));
        requestFocusEmail.setValue(true);
    }

    public void signIn() {
        hideKeyboard();
        gotoSignIn();
    }

    private void requestSignUp() {
        showProgressing();
        SignUpRequest request = new SignUpRequest();
        request.setCountryId(countryCode);
        request.setEmail(email.getValue());
        request.setFullName(fullName.getValue());
        request.setPhoneNumber(phoneNumber.getValue());
        request.setPassword(password.getValue());
        request.setCertificationCode(certificationCode.getValue());
        request.setDeviceToken(Utils.getDeviceToken());
        request.setDeviceVersion(Utils.getDeviceVersion());
        accountRepository.signup(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                hideProgressing();
                if (result.getCode() == ResultCode.CODE_OK) {
                    saveAccount(result.getResult());
                    signUpSuccess();
                } else
                    showToast(result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                showToast(error);
            }
        });
    }

    private void saveAccount(Account account) {
        mAppPreferences.setAccount(account);
    }

    private void gotoSignIn() {
        getCmd().call(new SignUpEvent(SignUpEvent.GOTO_LOGIN));
    }

    private void signUpSuccess() {
        getCmd().call(new SignUpEvent(SignUpEvent.SIGN_UP_SUCCESS));
    }

    private void hideKeyboard() {
        getCmd().call(new SignUpEvent(SignUpEvent.HIDE_KEYBOARD));
    }

    public void afterTextChanged() {
        enableBtnSigup.setValue(isReadySignUp());
    }

    private boolean isReadySignUp() {
        return checkLengthName() && checkLengthEmail() && checkLengthPhoneNumber() && checkLengthPwd() && checkLenghtConfirmPwd() && checkLengthVerifyCode();
    }

    private boolean checkLenghtConfirmPwd() {
        return !TextUtils.isEmpty(confirmPassword.getValue()) && confirmPassword.getValue().length() > 3;
    }

    public void afterPhoneNumberChanged() {
        afterTextChanged();
        showVerifyBtn();
    }

    private boolean checkLengthVerifyCode() {
        return !TextUtils.isEmpty(certificationCode.getValue());
    }

    private boolean checkLengthPwd() {
        return !TextUtils.isEmpty(password.getValue()) && password.getValue().length() > 3;
    }

    private boolean checkLengthPhoneNumber() {
        return !TextUtils.isEmpty(phoneNumber.getValue()) && phoneNumber.getValue().length() > 3;
    }

    private boolean checkLengthEmail() {
        return !TextUtils.isEmpty(email.getValue()) && email.getValue().length() > 3;
    }

    private boolean checkLengthName() {
        return !TextUtils.isEmpty(fullName.getValue());
    }

    public void onItemSelected(int position) {
        this.countryCode = countries.get(position).getId();
    }

    public boolean onTouch(MotionEvent event) {
        hideKeyboard();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (countries.size() == 0)
                requestCountryId();
        }
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            signUp();
        }
        return false;
    }
}
