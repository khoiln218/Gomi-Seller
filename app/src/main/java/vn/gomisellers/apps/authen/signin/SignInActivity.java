package vn.gomisellers.apps.authen.signin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomisellers.apps.BaseActivity;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.authen.forget.ForgetPasswordActivity;
import vn.gomisellers.apps.authen.forget.reset.ResetPasswordActivity;
import vn.gomisellers.apps.authen.signup.SignUpActivity;
import vn.gomisellers.apps.databinding.ActivitySignInBinding;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.utils.Intents;
import vn.gomisellers.apps.utils.Utils;

public class SignInActivity extends BaseActivity<SignInViewModel, ActivitySignInBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        subscribeToNavigationChanges();
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        viewModel = ViewModelProviders.of(this).get(SignInViewModel.class);
        getBinding().setSignInViewModel(getViewModel());
        binding.setLifecycleOwner(this);
    }

    private void subscribeToNavigationChanges() {
        getViewModel().getCmd().observe(this, new Observer<SignInEvent>() {
            @Override
            public void onChanged(SignInEvent event) {
                switch (event.getCode()) {
                    case SignInEvent.RESET_PASSWORD:
                        Intent intent = new Intent(SignInActivity.this, ResetPasswordActivity.class);
                        intent.putExtra(GomiConstants.EXTRA_ID, (String) event.getData());
                        startActivityForResult(intent, GomiConstants.REQUEST_RESET_PASSWORD);
                        break;
                    case SignInEvent.FORGET_PASSWORD:
                        startActivityForResult(new Intent(SignInActivity.this, ForgetPasswordActivity.class), GomiConstants.REQUEST_FORGET_PASSWORD);
                        break;
                    case SignInEvent.GOTO_SIGN_UP:
                        startActivityForResult(new Intent(SignInActivity.this, SignUpActivity.class), GomiConstants.REQUEST_SIGN_UP);
                        break;
                    case SignInEvent.LOG_IN_SUCCESS:
                        Intents.directToMainActivity(SignInActivity.this);
                        break;
                    case SignInEvent.HIDE_KEYBOARD:
                        Utils.hideSoftKeyboard(SignInActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getViewModel().onActivityResult(requestCode, resultCode, data);
    }
}
