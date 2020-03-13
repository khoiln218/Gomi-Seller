package vn.gomicorp.seller.signup;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.data.AppRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.data.AccountInfo;
import vn.gomicorp.seller.data.source.model.data.Contry;
import vn.gomicorp.seller.event.MultibleLiveEvent;
import vn.gomicorp.seller.utils.Inputs;
import vn.gomicorp.seller.utils.Utils;

public class SignUpViewModel extends ViewModel {
    private AppRepository mAppRepository = AppRepository.getInstance();

    public MutableLiveData<String> fullName = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> contryId = new MutableLiveData<>();
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Boolean> loadding = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableBtnSigup = new MutableLiveData<>();

    public int selectContry = -1;
    private List<Contry> contries = new ArrayList<>();

    public final MultibleLiveEvent<SignUpEvent> mSignUpCommand = new MultibleLiveEvent<>();

    public void signUp() {
        submitForm();
    }

    private void submitForm() {
        if (TextUtils.isEmpty(fullName.getValue())) {
            fullNameError();
            return;
        } else {
            fullNameSuccess();
        }

        if (TextUtils.isEmpty(email.getValue()) || !Inputs.validateEmail(email.getValue())) {
            emailError();
            return;
        } else {
            emailSuccess();
        }

        if (TextUtils.isEmpty(phoneNumber.getValue()) || !Inputs.validatePhoneNumber(phoneNumber.getValue())) {
            phoneNumberError();
            return;
        } else {
            phoneNumberSuccess();
        }

        if (TextUtils.isEmpty(password.getValue()) || !Inputs.validatePassword(password.getValue())) {
            passwordError();
            return;
        } else {
            passwordSuccess();
        }

        requestSignUp();
    }

    private void contryIdSuccess() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.CONTRYID_SUCCESS));
    }

    private void contryIdError() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.CONTRYID_ERROR));
    }

    private void passwordSuccess() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.PWD_SUCCESS));
    }

    private void passwordError() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.PWD_ERROR));
    }

    private void phoneNumberSuccess() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.PHONENUMBER_SUCCESS));
    }

    private void phoneNumberError() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.PHONENUMBER_ERROR));
    }

    private void emailSuccess() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.EMAIL_SUCCESS));
    }

    private void emailError() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.EMAIL_ERROR));
    }

    private void fullNameSuccess() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.FULLNAME_SUCCESS));
    }

    private void fullNameError() {
        mSignUpCommand.call(new SignUpEvent(SignUpEvent.FULLNAME_ERROR));
    }

    private void showProgressing() {
        loadding.setValue(true);
    }

    private void hideProgressing() {
        loadding.setValue(false);
    }

    public void signIn() {
        gotoSignIn();
    }

    private void requestSignUp() {
        showProgressing();
        SignUpRequest request = new SignUpRequest();
        request.setCountryId(contries.get(selectContry).code);
        request.setEmail(email.getValue());
        request.setFullName(fullName.getValue());
        request.setPhoneNumber(phoneNumber.getValue());
        request.setPassword(password.getValue());
        request.setDeviceToken(Utils.getDeviceToken());
        request.setDeviceVersion(Utils.getDeviceVersion());
        mAppRepository.signup(request, new ResultListener<AccountInfo>() {
            @Override
            public void onLoaded(AccountInfo result) {
                hideProgressing();
                signUpSuccess();
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                signUpFalse(error);
            }
        });
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

    public MultibleLiveEvent<SignUpEvent> getSignInCommand() {
        return mSignUpCommand;
    }

    public void afterTextChanged() {
        if (checkLengthName() && checkLengthEmail() && checkLengthPhoneNumber() && checkLengthPwd() && selectContry != -1/*&& checkLengthContryId()*/) {
            enableBtnSigup.setValue(true);
        } else {
            enableBtnSigup.setValue(false);
        }
    }

    public void getContry(final ResultListener<List<Contry>> listener) {
        mAppRepository.getContry(new ResultListener<List<Contry>>() {
            @Override
            public void onLoaded(List<Contry> result) {
                contries = result;
                listener.onLoaded(result);
            }

            @Override
            public void onDataNotAvailable(String error) {
                listener.onDataNotAvailable(error);
            }
        });
    }

    private boolean checkLengthContryId() {
        return !TextUtils.isEmpty(contryId.getValue());
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
}
