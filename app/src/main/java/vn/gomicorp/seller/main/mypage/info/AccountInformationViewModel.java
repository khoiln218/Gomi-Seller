package vn.gomicorp.seller.main.mypage.info;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.utils.DateTimes;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Inputs;
import vn.gomicorp.seller.utils.Strings;

/**
 * Created by KHOI LE on 4/16/2020.
 */
public class AccountInformationViewModel extends BaseViewModel {
    public MutableLiveData<Account> account;

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

    private MultableLiveEvent<InfoEvent> cmd;

    private boolean isInfoChanged;
    private CountDownTimer countDownTimer;

    private Calendar selectBirthday;

    public AccountInformationViewModel() {
        account = new MutableLiveData<>();
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

        cmd = new MultableLiveEvent<>();

        isInfoChanged = false;
    }

    public void changeBirthday() {
        hideKeyBoard();
        if (account.getValue() == null) return;
        InfoEvent<Long> event = new InfoEvent(InfoEvent.SHOW_DATE_PICKER);
        event.setData(account.getValue().getBirthDayLong());
        cmd.call(event);
    }

    public void sendCode() {
        hideKeyBoard();
        if (!Inputs.validatePhoneNumber(phoneNumber.getValue(), phoneNumberError, phoneNumberErrorEnable, phoneNumberFocus))
            return;

        requestVerifyPhoneNumber();
    }

    public void updateInfo() {
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
        if (account.getValue() == null) return;
        isInfoChanged = !s.toString().equals(account.getValue().getFullName());
        fullNameErrorEnable.setValue(false);
        updateEnable.setValue(isInfoChanged);
    }

    public void afterBirthdayChanged() {
        if (account.getValue() == null) return;
        isInfoChanged = selectBirthday.getTimeInMillis() != account.getValue().getBirthDayLong();
        updateEnable.setValue(isInfoChanged);
    }

    public void afterPhoneNumberChanged(Editable s) {
        if (account.getValue() == null) return;
        isInfoChanged = !s.toString().equals(account.getValue().getPhoneNumber());
        requestCodeEnable.setValue(isInfoChanged);
        phoneNumberErrorEnable.setValue(false);
        updateEnable.setValue(isInfoChanged);
    }

    public void afterPasswordChanged(Editable s) {
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

    }

    private void requestVerifyPhoneNumber() {

    }

    private void requestUpdateInfo() {
        if (selectBirthday != null) {
            long birthday = selectBirthday.getTimeInMillis();
        }

    }

    private void requestChangePassword() {

    }

    MutableLiveData<InfoEvent> getCmd() {
        return cmd;
    }
}
