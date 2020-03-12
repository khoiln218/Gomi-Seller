package vn.gomicorp.seller.signup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.gomicorp.seller.event.MultibleLiveEvent;
import vn.gomicorp.seller.data.AppRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.data.AccountInfo;
import vn.gomicorp.seller.utils.Utils;

public class SignUpViewModel extends ViewModel {
    private SignUpRequest signUpRequest = new SignUpRequest();
    private AppRepository mAppRepository = AppRepository.getInstance();

    public MutableLiveData<String> fullName = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> contryId = new MutableLiveData<>();
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

    public final MultibleLiveEvent<SignUpEvent> mSignUpCommand = new MultibleLiveEvent<>();

    public void signUp() {
        signUpRequest.setCountryId(Integer.parseInt(contryId.getValue()));
        signUpRequest.setEmail(email.getValue());
        signUpRequest.setFullName(fullName.getValue());
        signUpRequest.setPhoneNumber(phoneNumber.getValue());
        signUpRequest.setPassword(password.getValue());
        signUpRequest.setDeviceToken(Utils.getDeviceToken());
        signUpRequest.setDeviceVersion(Utils.getDeviceVersion());
        requestSignUp(signUpRequest);
    }

    public void signIn() {
        gotoSignIn();
    }

    private void requestSignUp(SignUpRequest request) {
        mAppRepository.signup(request, new ResultListener<AccountInfo>() {
            @Override
            public void onLoaded(AccountInfo result) {
                signUpSuccess();
            }

            @Override
            public void onDataNotAvailable(String error) {
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
}
