package vn.gomicorp.seller.forget;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.gomicorp.seller.data.AccountRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.ForgetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.utils.Inputs;

/**
 * Created by KHOI LE on 3/16/2020.
 */
public class ForgetPasswordViewModel extends ViewModel {
    private final int FORGET_SUCCESS = 200;
    private final int EMAIL_NOT_EXITS = 1004;
    private final int PHONE_NUMBER_NOT_EXITS = 1005;

    private AccountRepository mAppRepository = AccountRepository.getInstance();

    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableBtnForget = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadding = new MutableLiveData<>();

    private MultableLiveEvent<ForgetEvent<Account>> cmd = new MultableLiveEvent<>();

    public ForgetPasswordViewModel() {
    }

    public void forgot() {
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
        cmd.call(new ForgetEvent<Account>(ForgetEvent.USERNAME_SUCCESS));
    }

    private void usernameError() {
        cmd.call(new ForgetEvent<Account>(ForgetEvent.USERNAME_ERROR));
    }

    private void requestForgotPwd() {
        showProccessing();
        final ForgetPwdRequest request = new ForgetPwdRequest();
        request.setUserName(username.getValue());
        mAppRepository.forgetPwd(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                hideProccessing();
                switch (result.getCode()) {
                    case FORGET_SUCCESS:
                        forgetSuccess(result.getResult());
                        break;
                    case EMAIL_NOT_EXITS:
                    case PHONE_NUMBER_NOT_EXITS:
                    default:
                        forgetError(result.getMessage());

                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProccessing();
                forgetError(error);
            }
        });
    }

    private void forgetError(String error) {
        cmd.call(new ForgetEvent<Account>(ForgetEvent.FORGER_EEROR, error));
    }

    private void forgetSuccess(Account account) {
        ForgetEvent<Account> event = new ForgetEvent<>(ForgetEvent.FORGER_SUCCESS);
        event.setData(account);
        cmd.call(event);
    }

    private void hideProccessing() {
        loadding.setValue(false);
    }

    private void showProccessing() {
        loadding.setValue(true);
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

    public MultableLiveEvent<ForgetEvent<Account>> getCmd() {
        return cmd;
    }
}
