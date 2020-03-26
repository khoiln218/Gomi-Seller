package vn.gomicorp.seller.signup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivitySignUpBinding;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.utils.ToastUtils;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        subscribeToNavigationChanges(signUpViewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        signUpViewModel.requestCountryId();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Objects.requireNonNull(signUpViewModel).releaseAdapter();
    }

    private void initDataBinding() {
        ActivitySignUpBinding activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        activitySignUpBinding.setSignUpViewModel(signUpViewModel);
        activitySignUpBinding.setLifecycleOwner(this);
    }

    private void subscribeToNavigationChanges(SignUpViewModel viewModel) {
        viewModel.getSignInCommand().observe(this, new Observer<SignUpEvent>() {
            @Override
            public void onChanged(SignUpEvent event) {
                switch (event.code) {
                    case SignUpEvent.GOTO_LOGIN:
                        finish();
                        break;
                    case SignUpEvent.SIGN_UP_SUCCESS:
                        signUpSuccess();
                        break;
                    case SignUpEvent.SIGN_UP_FALSE:
                        signUpFalse(event.message);
                        break;
                    case SignUpEvent.VERIFY_ERROR:
                        verifyError(event.message);
                        break;
                    case SignUpEvent.VERIFY_SUCCESS:
                        verifySuccess();
                }
            }
        });
    }

    private void verifySuccess() {
        ToastUtils.showToast("Verify success!");
    }

    private void verifyError(String message) {
        ToastUtils.showToast(message);
    }

    private void signUpFalse(String msg) {
        ToastUtils.showToast(msg);
    }

    private void signUpSuccess() {
        Intents.directToMainActivity(this);
    }
}
