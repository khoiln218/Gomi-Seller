package vn.gomicorp.seller.signup;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Spinner;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

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
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.utils.Inputs;
import vn.gomicorp.seller.utils.Utils;

public class SignUpViewModel extends ViewModel {
    private final int CODE_OK = 200;

    private AccountRepository accountRepository = AccountRepository.getInstance();
    private LocationRepository locationRepository = LocationRepository.getInstance();
    private AppPreferences mAppPreferences = EappsApplication.getPreferences();

    private int countryCode;

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

    public MutableLiveData<Boolean> loading = new MutableLiveData<>();
    public MutableLiveData<Boolean> countDownIsShow = new MutableLiveData<>();
    public MutableLiveData<Boolean> verifyIsShow = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableBtnSigup = new MutableLiveData<>();
    public MutableLiveData<String> countDown = new MutableLiveData<>();

    @SuppressLint("StaticFieldLeak")
    private static LocationAdapter adapter;
    public MutableLiveData<List<Location>> countries = new MutableLiveData<>();

    private final MultableLiveEvent<SignUpEvent> mSignUpCommand = new MultableLiveEvent<>();

    public SignUpViewModel() {
        showVerifyBtn();
    }

    public void signUp() {
        submitForm();
    }

    public void verify() {
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
        locationRepository.getLocationCountry(new ResultListener<ResponseData<List<Location>>>() {
            @Override
            public void onLoaded(ResponseData<List<Location>> result) {
                if (result.getCode() == CODE_OK) {
                    updateCountry(result.getResult());
                } else {
                    Log.d("requestCountryId", "onLoaded: " + result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                Log.d("requestCountryId", "onDataNotAvailable: " + error);
            }
        });
    }

    private void updateCountry(List<Location> locs) {
        countries.postValue(locs);
    }

    private void requestVerifyPhoneNumber() {
        showCountDown();
        VerifyPhoneNumberRequest request = new VerifyPhoneNumberRequest();
        request.setPhoneNumber(phoneNumber.getValue());
        accountRepository.verifyPhoneNumber(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                if (result.getCode() == CODE_OK) {
                    verifySuccess();
                } else
                    verifyError(result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                verifyError(error);
            }
        });
    }

    private void setCountryCode(int position) {
        this.countryCode = Objects.requireNonNull(countries.getValue()).get(position).getId();
    }

    private void showVerifyBtn() {
        verifyIsShow.postValue(true);
        countDownIsShow.postValue(false);
        countDown.postValue("");
        if (timer != null)
            timer.cancel();
    }

    private Timer timer;

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

    private void verifyError(String error) {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.VERIFY_ERROR, error));
    }

    private void verifySuccess() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.VERIFY_SUCCESS));
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

    private void showProgressing() {
        loading.setValue(true);
    }

    private void hideProgressing() {
        loading.setValue(false);
    }

    public void signIn() {
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
                if (result.getCode() == CODE_OK) {
                    saveAccount(result.getResult());
                    signUpSuccess();
                } else
                    signUpFalse(result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                signUpFalse(error);
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

    private void signUpFalse(String error) {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.SIGN_UP_FALSE, error));
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

    public OnItemSelectedListener itemSelectedListener = new OnItemSelectedListener();

    public OnTouchListener touchListener = new OnTouchListener();

    public class OnItemSelectedListener {
        public void onItemSelected(int position) {
            setCountryCode(position);
        }
    }

    public class OnTouchListener {
        public boolean onTouch(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (countries.getValue() == null || countries.getValue().size() == 0)
                    requestCountryId();
            }
            return false;
        }
    }

    void releaseAdapter() {
        adapter = null;
    }

    @BindingAdapter("locations")
    public static void setAdapter(Spinner spinner, List<Location> locations) {
        if (adapter == null) {
            adapter = new LocationAdapter(spinner.getContext(), new ArrayList<Location>());
            spinner.setAdapter(adapter);
        } else {
            adapter.setData(locations);
            adapter.notifyDataSetChanged();
        }
    }
}
