package vn.gomicorp.seller.signup;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        subscribeToNavigationChanges(signUpViewModel);
    }

    private void initDataBinding() {
        ActivitySignUpBinding activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        activitySignUpBinding.setSignUpViewModel(signUpViewModel);
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
                }
            }
        });
    }

    private void signUpFalse(String msg) {
        Toast.makeText(this, "SignUp False: " + msg, Toast.LENGTH_SHORT).show();
    }

    private void signUpSuccess() {
        Toast.makeText(this, "SignUp Success", Toast.LENGTH_SHORT).show();
    }
}
