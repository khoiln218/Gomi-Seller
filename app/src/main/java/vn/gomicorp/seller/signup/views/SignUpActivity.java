package vn.gomicorp.seller.signup.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivitySignUpBinding;
import vn.gomicorp.seller.signup.viewmodels.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
    }

    private void initDataBinding() {
        ActivitySignUpBinding activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
        activitySignUpBinding.setSignUpViewModel(signUpViewModel);
    }
}
