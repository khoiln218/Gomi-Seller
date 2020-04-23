package vn.gomisellers.apps.authen.signup;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomisellers.apps.BaseActivity;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.databinding.ActivitySignUpBinding;
import vn.gomisellers.apps.utils.Utils;

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
                switch (event.getCode()) {
                    case SignUpEvent.GOTO_LOGIN:
                        finish();
                        break;
                    case SignUpEvent.SIGN_UP_SUCCESS:
                        setResult(RESULT_OK);
                        finish();
                        break;
                    case SignUpEvent.HIDE_KEYBOARD:
                        Utils.hideSoftKeyboard(SignUpActivity.this);
                        break;
                }
            }
        });
    }
}
