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
import vn.gomicorp.seller.signup.SignUpActivity;

public class SignInActivity extends AppCompatActivity {

    private SignInViewModel signInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        subscribeToNavigationChanges(signInViewModel);
    }

    private void initDataBinding() {
        ActivitySignInBinding activitySignInBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
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
                    case SignInEvent.LOG_IN_FALSE:
                        loginError(event.message);
                        break;
                }
            }
        });
    }

    private void loginError(String error) {
        Toast.makeText(this, "Login Error: " + error, Toast.LENGTH_SHORT).show();
    }

    private void loginSuccess() {
        Toast.makeText(this, "Login Success!", Toast.LENGTH_SHORT).show();
    }

    private void onStartForgetPassword() {
        //TODO: start forget password screen
    }

    private void onStartSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
