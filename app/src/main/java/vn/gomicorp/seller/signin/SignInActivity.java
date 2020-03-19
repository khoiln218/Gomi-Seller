package vn.gomicorp.seller.signin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivitySignInBinding;
import vn.gomicorp.seller.forget.ForgetPasswordActivity;
import vn.gomicorp.seller.signup.SignUpActivity;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.utils.Utils;

public class SignInActivity extends AppCompatActivity {

    private SignInViewModel signInViewModel;
    private ActivitySignInBinding activitySignInBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        subscribeToNavigationChanges(signInViewModel);
    }

    private void initDataBinding() {
        activitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        signInViewModel = ViewModelProviders.of(this).get(SignInViewModel.class);
        activitySignInBinding.setSignInViewModel(signInViewModel);
        activitySignInBinding.setLifecycleOwner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void subscribeToNavigationChanges(SignInViewModel viewModel) {
        // The activity observes the navigation commands in the ViewModel
        viewModel.getLoginCommand().observe(this, new Observer<SignInEvent>() {
            @Override
            public void onChanged(@Nullable SignInEvent event) {
                switch (event.code) {
                    case SignInEvent.FORGET_PASSWORD:
                        onStartForgetPassword();
                        break;
                    case SignInEvent.GOTO_SIGN_UP:
                        onStartSignUp();
                        break;
                    case SignInEvent.LOG_IN_SUCCESS:
                        loginSuccess();
                        break;
                    case SignInEvent.ACCOUNT_LOCK:
                        loginError(event.message);
                        break;
                    case SignInEvent.LOG_IN_FALSE:
                        loginError(event.message);
                        break;
                    case SignInEvent.USERNAME_INPUT_ERROR:
                        inpuUserNameError();
                        break;
                    case SignInEvent.USERNAME_INPUT_SUCCESS:
                        inpuUserSuccess();
                        break;
                    case SignInEvent.PASSWORD_INPUT_ERROR:
                        inputPwdError();
                        break;
                    case SignInEvent.PASSWORD_INPUT_SUCCESS:
                        inputPwdSuccess();
                        break;
                }
            }
        });
    }

    private void loginError(String error) {
        Toast.makeText(this, "Login Error: " + error, Toast.LENGTH_SHORT).show();
    }

    private void loginSuccess() {
        Intents.startMainActivity(this);
    }

    private void onStartForgetPassword() {
        startActivity(new Intent(this, ForgetPasswordActivity.class));
    }

    private void onStartSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    private void inpuUserNameError() {
        activitySignInBinding.inputLayoutUserName.setError(getString(R.string.err_input_username));
        Utils.requestFocus(this, activitySignInBinding.inputUserName);
        Utils.playVibrate(this);
    }

    private void inpuUserSuccess() {
        activitySignInBinding.inputLayoutUserName.setErrorEnabled(false);
    }

    private void inputPwdError() {
        activitySignInBinding.inputLayoutPassword.setError(getString(R.string.err_input_password));
        Utils.requestFocus(this, activitySignInBinding.inputPassword);
        Utils.playVibrate(this);
    }

    private void inputPwdSuccess() {
        activitySignInBinding.inputLayoutPassword.setErrorEnabled(false);
    }
}
