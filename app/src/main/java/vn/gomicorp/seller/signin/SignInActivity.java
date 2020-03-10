package vn.gomicorp.seller.signin;

import android.content.Intent;
import android.os.Bundle;

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
        viewModel.getSignUpCommand().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void _) {
                SignInActivity.this.onStartSignUp();
            }
        });
        viewModel.getForgetPasswordCommand().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void _) {
                SignInActivity.this.onStartForgetPassword();
            }
        });
    }

    private void onStartForgetPassword() {
        //TODO: start forget password screen
    }

    private void onStartSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
