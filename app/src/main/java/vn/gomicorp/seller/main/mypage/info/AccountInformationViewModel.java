package vn.gomicorp.seller.main.mypage.info;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.adapter.GenderAdapter;
import vn.gomicorp.seller.data.AccountRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.local.prefs.AppPreferences;
import vn.gomicorp.seller.data.source.model.api.AccountChangePasswordRequest;
import vn.gomicorp.seller.data.source.model.api.AccountRequest;
import vn.gomicorp.seller.data.source.model.api.AccountUpdateRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.data.source.remote.ResultCode;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.utils.DateTimes;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Inputs;
import vn.gomicorp.seller.utils.Strings;

/**
 * Created by KHOI LE on 4/16/2020.
 */
public class AccountInformationViewModel extends BaseViewModel {

    private AccountRepository mAccountRepository;
    private AppPreferences mAppPreferences;

    public MutableLiveData<String> fullName;
    public MutableLiveData<String> fullNameError;
    public MutableLiveData<Boolean> fullNameErrorEnable;
    public MutableLiveData<Boolean> fullNameFocus;

    public MutableLiveData<String> email;
    public MutableLiveData<String> emailError;
    public MutableLiveData<Boolean> emailErrorEnable;
    public MutableLiveData<Boolean> emailFocus;

    public MutableLiveData<String> phoneNumber;
    public MutableLiveData<String> phoneNumberError;
    public MutableLiveData<Boolean> phoneNumberErrorEnable;
    public MutableLiveData<Boolean> phoneNumberFocus;

    public MutableLiveData<String> verifyCode;
    public MutableLiveData<String> verifyCodeError;
    public MutableLiveData<Boolean> verifyCodeErrorEnable;
    public MutableLiveData<Boolean> verifyCodeFocus;
    public MutableLiveData<Boolean> verifyCodeEnable;

    public MutableLiveData<String> newPassword;
    public MutableLiveData<String> newPasswordError;
    public MutableLiveData<Boolean> newPasswordErrorEnable;
    public MutableLiveData<Boolean> newPasswordFocus;

    public MutableLiveData<String> confirmPassword;
    public MutableLiveData<String> confirmPasswordError;
    public MutableLiveData<Boolean> confirmPasswordErrorEnable;
    public MutableLiveData<Boolean> confirmPasswordFocus;

    public MutableLiveData<String> oldPassword;
    public MutableLiveData<String> oldPasswordError;
    public MutableLiveData<Boolean> oldPasswordErrorEnable;
    public MutableLiveData<Boolean> oldPasswordFocus;

    public MutableLiveData<String> resendCode;
    public MutableLiveData<Boolean> resendCodeShow;

    public MutableLiveData<String> birthday;
    public MutableLiveData<Integer> gender;
    public MutableLiveData<Boolean> requestCodeEnable;
    public MutableLiveData<Boolean> changePasswordEnable;
    public MutableLiveData<Boolean> updateEnable;

    public MutableLiveData<Boolean> changeInfoHide;
    public MutableLiveData<Boolean> changeInfoFocus;

    public MutableLiveData<GenderAdapter> genderAdapter;

    private MultableLiveEvent<InfoEvent> cmd;

    private boolean isInfoChanged;
    private CountDownTimer countDownTimer;
    private Account account;
    private Calendar selectBirthday;
    private boolean genderFirstSelect = false;

    public AccountInformationViewModel() {
        mAccountRepository = AccountRepository.getInstance();
        mAppPreferences = EappsApplication.getPreferences();

        fullName = new MutableLiveData<>();
        fullNameError = new MutableLiveData<>();
        fullNameErrorEnable = new MutableLiveData<>();
        fullNameFocus = new MutableLiveData<>();

        email = new MutableLiveData<>();
        emailError = new MutableLiveData<>();
        emailErrorEnable = new MutableLiveData<>();
        emailFocus = new MutableLiveData<>();

        phoneNumber = new MutableLiveData<>();
        phoneNumberError = new MutableLiveData<>();
        phoneNumberErrorEnable = new MutableLiveData<>();
        phoneNumberFocus = new MutableLiveData<>();

        verifyCode = new MutableLiveData<>();
        verifyCodeError = new MutableLiveData<>();
        verifyCodeErrorEnable = new MutableLiveData<>();
        verifyCodeFocus = new MutableLiveData<>();
        verifyCodeEnable = new MutableLiveData<>();

        newPassword = new MutableLiveData<>();
        newPasswordError = new MutableLiveData<>();
        newPasswordErrorEnable = new MutableLiveData<>();
        newPasswordFocus = new MutableLiveData<>();

        confirmPassword = new MutableLiveData<>();
        confirmPasswordError = new MutableLiveData<>();
        confirmPasswordErrorEnable = new MutableLiveData<>();
        confirmPasswordFocus = new MutableLiveData<>();

        oldPassword = new MutableLiveData<>();
        oldPasswordError = new MutableLiveData<>();
        oldPasswordErrorEnable = new MutableLiveData<>();
        oldPasswordFocus = new MutableLiveData<>();

        resendCode = new MutableLiveData<>();
        resendCodeShow = new MutableLiveData<>();

        birthday = new MutableLiveData<>();
        gender = new MutableLiveData<>();
        requestCodeEnable = new MutableLiveData<>();
        changePasswordEnable = new MutableLiveData<>();
        updateEnable = new MutableLiveData<>();

        changeInfoHide = new MutableLiveData<>();
        changeInfoFocus = new MutableLiveData<>();

        genderAdapter = new MutableLiveData<>();

        cmd = new MultableLiveEvent<>();

        genderAdapter.setValue(new GenderAdapter());

        isInfoChanged = false;
    }

    public void changeBirthday() {
        hideKeyBoard();
        if (account == null) return;
        long birthday = selectBirthday != null ? selectBirthday.getTimeInMillis() : account.getBirthDayLong();
        InfoEvent<Long> event = new InfoEvent(InfoEvent.SHOW_DATE_PICKER);
        event.setData(birthday);
        cmd.call(event);
    }

    public void sendCode() {
        hideKeyBoard();
        if (!Inputs.validatePhoneNumber(phoneNumber.getValue(), phoneNumberError, phoneNumberErrorEnable, phoneNumberFocus))
            return;

        requestVerifyPhoneNumber();
    }

    public void update() {
        hideKeyBoard();
        String msg = String.format("%s %s", EappsApplication.getInstance().getString(R.string.err_text_empty), EappsApplication.getInstance().getString(R.string.name));
        if (!Inputs.validateText(fullName.getValue(), fullNameError, fullNameErrorEnable, fullNameFocus, msg))
            return;

        if (!Inputs.validatePhoneNumber(phoneNumber.getValue(), phoneNumberError, phoneNumberErrorEnable, phoneNumberFocus))
            return;

        requestUpdateInfo();
    }

    public void changePassword() {
        hideKeyBoard();
        if (!Inputs.validatePassword(newPassword.getValue(), newPasswordError, newPasswordErrorEnable, newPasswordFocus))
            return;

        if (!Inputs.validatePassword(confirmPassword.getValue(), confirmPasswordError, confirmPasswordErrorEnable, confirmPasswordFocus))
            return;

        if (!confirmPassword())
            return;

        requestChangePassword();
    }


    private boolean confirmPassword() {
        if (!TextUtils.equals(newPassword.getValue(), confirmPassword.getValue())) {
            confirmPasswordError.setValue(EappsApplication.getInstance().getString(R.string.err_password_confirm));
            confirmPasswordFocus.setValue(true);
            return false;
        }

        confirmPasswordErrorEnable.setValue(false);
        return true;
    }

    public void afterFullNameChanged(Editable s) {
        if (account == null) return;
        isInfoChanged = !s.toString().equals(account.getFullName());
        fullNameErrorEnable.setValue(false);
        updateEnable.setValue(isInfoChanged);
    }

    private void afterBirthdayChanged() {
        if (account == null) return;
        isInfoChanged = selectBirthday.getTimeInMillis() != account.getBirthDayLong();
        updateEnable.setValue(isInfoChanged);
    }

    public void onItemSelected(int position) {
        if (account == null || gender.getValue() == null) return;
        if (!genderFirstSelect) {
            genderFirstSelect = true;
            gender.setValue(account.getGender());
            return;
        }
        isInfoChanged = position != account.getGender();
        if (position != gender.getValue())
            gender.setValue(position);
        updateEnable.setValue(isInfoChanged);
    }

    public void afterPhoneNumberChanged(Editable s) {
        if (account == null) return;
        isInfoChanged = !s.toString().equals(account.getPhoneNumber());
        requestCodeEnable.setValue(isInfoChanged);
        phoneNumberErrorEnable.setValue(false);
        updateEnable.setValue(isInfoChanged);
    }

    public void afterPasswordChanged() {
        oldPasswordErrorEnable.setValue(false);
        newPasswordErrorEnable.setValue(false);
        confirmPasswordErrorEnable.setValue(false);

        if (Strings.isNullOrEmpty(oldPassword.getValue())
                || Strings.isNullOrEmpty(newPassword.getValue())
                || Strings.isNullOrEmpty(confirmPassword.getValue()))
            changePasswordEnable.setValue(false);

        else
            changePasswordEnable.setValue(true);

        updateEnable.setValue(isInfoChanged);
    }

    private void startCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        resendCodeShow.setValue(true);
        requestCodeEnable.setValue(false);

        verifyCodeEnable.setValue(true);
        verifyCodeFocus.setValue(true);

        long millisInFuture = 180 * 1000;
        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                resendCode.setValue(DateTimes.combinationFormatter(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                requestCodeEnable.setValue(true);
            }
        }.start();
    }

    private void hideKeyBoard() {
        cmd.call(new InfoEvent(InfoEvent.HIDE_KEYBOARD));
    }

    void setBirthday(Calendar selectBirthday) {
        this.selectBirthday = selectBirthday;
        birthday.setValue(DateTimes.toString(selectBirthday.getTime(), GomiConstants.INFO_DATE_FORMAT));
        afterBirthdayChanged();
    }

    void requestAccountInformation() {
        showProgressing();
        final AccountRequest request = new AccountRequest();
        mAccountRepository.findbyid(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                hideProgressing();
                if (result.getCode() == ResultCode.CODE_OK) {
                    account = result.getResult();
                    mAppPreferences.setAccount(result.getResult());
                    updateInfo();
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

    private void updateInfo() {
        fullName.setValue(account.getFullName());
        birthday.setValue(account.getBirthDay());
        gender.setValue(account.getGender());
        email.setValue(account.getEmail());
        phoneNumber.setValue(account.getPhoneNumber());

        resetFormChangePasswword();

        isInfoChanged = false;
        updateEnable.setValue(isInfoChanged);
    }

    private void resetFormChangePasswword() {
        oldPassword.setValue(null);
        newPassword.setValue(null);
        confirmPassword.setValue(null);
    }

    private void requestVerifyPhoneNumber() {

    }

    private void requestUpdateInfo() {
        showProgressing();
        changeInfoHide.setValue(true);
        AccountUpdateRequest request = new AccountUpdateRequest();
        if (selectBirthday != null) {
            request.setBirthDayLong(selectBirthday.getTimeInMillis());
        } else {
            request.setBirthDayLong(account.getBirthDayLong());
        }
        request.setFullName(fullName.getValue());
        request.setGender(gender.getValue());
        mAccountRepository.updateinfo(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                hideProgressing();
                changeInfoHide.setValue(false);
                changeInfoFocus.setValue(true);
                if (result.getCode() == ResultCode.CODE_OK) {
                    account = result.getResult();
                    mAppPreferences.setAccount(result.getResult());
                    updateInfo();
                    showToast(EappsApplication.getInstance().getString(R.string.account_update_success));
                } else {
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                showToast(error);
                changeInfoHide.setValue(false);
                changeInfoFocus.setValue(true);
            }
        });
    }

    private void requestChangePassword() {
        showProgressing();
        AccountChangePasswordRequest request = new AccountChangePasswordRequest();
        request.setPassword(oldPassword.getValue());
        request.setNewPassword(newPassword.getValue());
        mAccountRepository.changepassword(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                hideProgressing();
                if (result.getCode() == ResultCode.CODE_OK) {
                    showToast(EappsApplication.getInstance().getString(R.string.change_password_success));
                    resetFormChangePasswword();
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

    MutableLiveData<InfoEvent> getCmd() {
        return cmd;
    }
}
