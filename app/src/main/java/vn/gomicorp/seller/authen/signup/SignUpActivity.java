package vn.gomicorp.seller.authen.signup;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivitySignUpBinding;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.utils.Utils;

public class SignUpActivity extends BaseActivity<SignUpViewModel, ActivitySignUpBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        subscribeToNavigationChanges();
        getViewModel().requestCountryId();
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        viewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        getBinding().setSignUpViewModel(getViewModel());
        binding.setLifecycleOwner(this);
    }

    private void subscribeToNavigationChanges() {
        getViewModel().getSignInCommand().observe(this, new Observer<SignUpEvent>() {
            @Override
            public void onChanged(SignUpEvent event) {
                switch (event.code) {
                    case SignUpEvent.GOTO_LOGIN:
                        finish();
                        break;
                    case SignUpEvent.SIGN_UP_SUCCESS:
                        Intents.directToMainActivity(SignUpActivity.this);
                        break;
                    case SignUpEvent.HIDE_KEYBOARD:
                        Utils.hideSoftKeyboard(SignUpActivity.this);
                        break;
                }
            }
        });
    }
}
