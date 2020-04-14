package vn.gomicorp.seller.authen.forget.reset;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.gomicorp.seller.data.AccountRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.ResetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.utils.Inputs;

/**
 * Created by KHOI LE on 3/16/2020.
 */
public class ResetPasswordViewModel extends ViewModel {
    private final int RESET_SUCCESS = 200;

    private AccountRepository mAccountRepository = AccountRepository.getInstance();

    public MutableLiveData<String> verifyCode = new MutableLiveData<>();
    public MutableLiveData<String> newPassword = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableBtn = new MutableLiveData<>();
    public MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;

    private MultableLiveEvent<ResetEvent> cmd = new MultableLiveEvent<>();

    public void reset() {
        submitForm();
    }

    private void submitForm() {
        if (!Inputs.validatePassword(newPassword.getValue())) {
            newPasswordError();
            return;
        } else {
            newPasswordSuccess();
        }
        requestResetPwd();
    }

    private void newPasswordError() {
        cmd.call(new ResetEvent(ResetEvent.NEW_PASSWORD_ERROR));
    }

    private void newPasswordSuccess() {
        cmd.call(new ResetEvent(ResetEvent.NEW_PASSWORD_SUCCESS));
    }

    private void requestResetPwd() {
        showProccessing();
        ResetPwdRequest request = new ResetPwdRequest();
        request.setVerifyCode(verifyCode.getValue());
        request.setNewPassword(newPassword.getValue());
        request.setUserId(userId);
        mAccountRepository.resetPwd(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                hideProcessing();
                if (result.getCode() == RESET_SUCCESS)
                    resetPwdSuccess();
                else
                    resetPwdError(result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProcessing();
                resetPwdError(error);
            }
        });
    }

    private void resetPwdError(String error) {
        cmd.call(new ResetEvent(ResetEvent.RESET_EEROR, error));
    }

    private void resetPwdSuccess() {
        cmd.call(new ResetEvent(ResetEvent.RESET_SUCCESS));
    }

    private void hideProcessing() {
        loading.setValue(false);
    }

    private void showProccessing() {
        loading.setValue(true);
    }

    public void afterTextChanged() {
        if (checklengthVerifyCode() && checkLengthNewPwd())
            enableBtn.setValue(true);
        else enableBtn.setValue(false);
    }

    private boolean checklengthVerifyCode() {
        return !TextUtils.isEmpty(verifyCode.getValue()) && verifyCode.getValue().length() > 3;
    }

    private boolean checkLengthNewPwd() {
        return !TextUtils.isEmpty(newPassword.getValue()) && newPassword.getValue().length() > 3;
    }

    public MultableLiveEvent<ResetEvent> getCmd() {
        return cmd;
    }
}
