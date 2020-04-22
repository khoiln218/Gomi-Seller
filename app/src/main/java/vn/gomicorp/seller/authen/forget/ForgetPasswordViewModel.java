package vn.gomicorp.seller.authen.forget;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.AccountRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.ForgetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.data.source.remote.ResultCode;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.utils.Inputs;

/**
 * Created by KHOI LE on 3/16/2020.
 */
public class ForgetPasswordViewModel extends BaseViewModel {
    private AccountRepository mAppRepository = AccountRepository.getInstance();

    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> usernameError = new MutableLiveData<>();
    public MutableLiveData<Boolean> usernameEnableError = new MutableLiveData<>();
    public MutableLiveData<Boolean> usernameRequestFocus = new MutableLiveData<>();

    public MutableLiveData<Boolean> enableBtnForget = new MutableLiveData<>();

    private MultableLiveEvent<ForgetEvent<Account>> cmd = new MultableLiveEvent<>();

    public ForgetPasswordViewModel() {
    }

    public void forgot() {
        hideKeyboard();
        submitForm();
    }

    private void submitForm() {
        if (!Inputs.validateEmail(username.getValue()) && !Inputs.validatePhoneNumber(username.getValue())) {
            usernameError();
            return;
        } else {
            usernameSuccess();
        }

        requestForgotPwd();
    }

    private void usernameSuccess() {
        usernameEnableError.setValue(false);
    }

    private void usernameError() {
        usernameError.setValue(EappsApplication.getInstance().getString(R.string.err_input_username));
        usernameRequestFocus.setValue(true);
    }

    private void requestForgotPwd() {
        showProgressing();
        final ForgetPwdRequest request = new ForgetPwdRequest();
        request.setUserName(username.getValue());
        mAppRepository.forgetPwd(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                loaded();
                if (result.getCode() == ResultCode.CODE_OK) {
                    forgetSuccess(result.getResult());
                } else {
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                showToast(error);
            }
        });
    }

    private void forgetSuccess(Account account) {
        ForgetEvent<Account> event = new ForgetEvent<>(ForgetEvent.FORGER_SUCCESS);
        event.setData(account);
        cmd.call(event);
    }

    private void hideKeyboard() {
        cmd.call(new ForgetEvent(ForgetEvent.HIDE_KEYBOARD));
    }

    public void afterTextChanged() {
        if (!TextUtils.isEmpty(username.getValue()) && username.getValue().length() > 3)
            enableForgetPwd();
        else
            disableForgetPwd();
    }

    private void enableForgetPwd() {
        enableBtnForget.setValue(true);
    }

    private void disableForgetPwd() {
        enableBtnForget.setValue(false);
    }

    MultableLiveEvent<ForgetEvent<Account>> getCmd() {
        return cmd;
    }
}
