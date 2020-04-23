package vn.gomisellers.apps.main.mypage.info.password;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.EappsApplication;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.AccountRepository;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.model.api.AccountChangePasswordRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.Account;
import vn.gomisellers.apps.data.source.remote.ResultCode;
import vn.gomisellers.apps.event.MultableLiveEvent;
import vn.gomisellers.apps.utils.Inputs;
import vn.gomisellers.apps.utils.Strings;

public class ChangePasswordViewModel extends BaseViewModel {

    private AccountRepository mAccountRepository;

    public MutableLiveData<String> oldPassword;
    public MutableLiveData<String> oldPasswordError;
    public MutableLiveData<Boolean> oldPasswordErrorEnable;
    public MutableLiveData<Boolean> oldPasswordFocus;

    public MutableLiveData<String> newPassword;
    public MutableLiveData<String> newPasswordError;
    public MutableLiveData<Boolean> newPasswordErrorEnable;
    public MutableLiveData<Boolean> newPasswordFocus;

    public MutableLiveData<String> confirmPassword;
    public MutableLiveData<String> confirmPasswordError;
    public MutableLiveData<Boolean> confirmPasswordErrorEnable;
    public MutableLiveData<Boolean> confirmPasswordFocus;

    public MutableLiveData<Boolean> changePasswordEnable;

    private MultableLiveEvent<ChangePasswordEvent> cmd;

    public ChangePasswordViewModel() {
        mAccountRepository = AccountRepository.getInstance();

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

        changePasswordEnable = new MutableLiveData<>();

        cmd = new MultableLiveEvent<>();
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

    private void hideKeyBoard() {
        cmd.call(new ChangePasswordEvent(ChangePasswordEvent.HIDE_KEY_BOARD));
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
    }

    private void resetFormChangePassword() {
        oldPassword.setValue(null);
        newPassword.setValue(null);
        confirmPassword.setValue(null);

        oldPasswordErrorEnable.setValue(false);
        newPasswordErrorEnable.setValue(false);
        confirmPasswordErrorEnable.setValue(false);
    }

    private void requestChangePassword() {
        cmd.call(new ChangePasswordEvent(ChangePasswordEvent.SHOW_LOADING));
        AccountChangePasswordRequest request = new AccountChangePasswordRequest();
        request.setPassword(oldPassword.getValue());
        request.setNewPassword(newPassword.getValue());
        mAccountRepository.changepassword(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                cmd.call(new ChangePasswordEvent(ChangePasswordEvent.HIDE_LOADING));
                if (result.getCode() == ResultCode.CODE_OK) {
                    showToast(EappsApplication.getInstance().getString(R.string.change_password_success));
                    resetFormChangePassword();
                    cmd.call(new ChangePasswordEvent(ChangePasswordEvent.CHANGE_PASSWORD_DONE));
                } else {
                    oldPasswordError.setValue(result.getMessage());
                    oldPasswordFocus.setValue(true);
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                cmd.call(new ChangePasswordEvent(ChangePasswordEvent.HIDE_LOADING));
                showToast(error);
            }
        });
    }

    MultableLiveEvent<ChangePasswordEvent> getCmd() {
        return cmd;
    }
}
