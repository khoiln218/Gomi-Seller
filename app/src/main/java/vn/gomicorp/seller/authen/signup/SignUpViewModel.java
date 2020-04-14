package vn.gomicorp.seller.authen.signup;

import android.text.TextUtils;
import android.view.MotionEvent;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.adapter.LocationAdapter;
import vn.gomicorp.seller.data.AccountRepository;
import vn.gomicorp.seller.data.LocationRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.local.prefs.AppPreferences;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.api.VerifyPhoneNumberRequest;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.data.source.model.data.Location;
import vn.gomicorp.seller.data.source.remote.ResultCode;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.utils.Inputs;
import vn.gomicorp.seller.utils.Utils;

public class SignUpViewModel extends BaseViewModel {
    private AccountRepository accountRepository = AccountRepository.getInstance();
    private LocationRepository locationRepository = LocationRepository.getInstance();
    private AppPreferences mAppPreferences = EappsApplication.getPreferences();

    public MutableLiveData<String> fullName = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> certificationCode = new MutableLiveData<>();

    public MutableLiveData<String> errorEmailMsg = new MutableLiveData<>();
    public MutableLiveData<String> errorPhoneNumberMsg = new MutableLiveData<>();
    public MutableLiveData<String> errorPwdMsg = new MutableLiveData<>();

    public MutableLiveData<Boolean> errorEnableEmail = new MutableLiveData<>();
    public MutableLiveData<Boolean> errorEnablePhoneNumber = new MutableLiveData<>();
    public MutableLiveData<Boolean> errorEnablePwd = new MutableLiveData<>();

    public MutableLiveData<Boolean> requestFocusEmail = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestFocusPhoneNumber = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestFocusPwd = new MutableLiveData<>();

    public MutableLiveData<Boolean> countDownIsShow = new MutableLiveData<>();
    public MutableLiveData<Boolean> verifyIsShow = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableBtnSigup = new MutableLiveData<>();
    public MutableLiveData<String> countDown = new MutableLiveData<>();

    public MutableLiveData<LocationAdapter> locationAdapter = new MutableLiveData<>();

    private final MultableLiveEvent<SignUpEvent> mSignUpCommand = new MultableLiveEvent<>();

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

        if (!Inputs.validatePassword(password.getValue())) {
            passwordError();
            return;
        } else {
            passwordSuccess();
        }

        requestSignUp();
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
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.GOTO_LOGIN));
    }

    private void signUpSuccess() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.SIGN_UP_SUCCESS));
    }

    private void hideKeyboard() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.HIDE_KEYBOARD));
    }

    MultableLiveEvent<SignUpEvent> getSignInCommand() {
        return mSignUpCommand;
    }

    public void afterTextChanged() {
        if (checkLengthName() && checkLengthEmail() && checkLengthPhoneNumber() && checkLengthPwd() && checkLengthVerifyCode()) {
            enableBtnSigup.setValue(true);
        } else {
            enableBtnSigup.setValue(false);
        }
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
}
