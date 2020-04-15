package vn.gomicorp.seller.authen.signin;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.authen.forget.ForgetPasswordActivity;
import vn.gomicorp.seller.authen.signup.SignUpActivity;
import vn.gomicorp.seller.databinding.ActivitySignInBinding;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.utils.Utils;

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
        getViewModel().getLoginCommand().observe(this, new Observer<SignInEvent>() {
            @Override
            public void onChanged(SignInEvent event) {
                switch (event.code) {
                    case SignInEvent.FORGET_PASSWORD:
                        startActivity(new Intent(SignInActivity.this, ForgetPasswordActivity.class));
                        break;
                    case SignInEvent.GOTO_SIGN_UP:
                        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
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
}
