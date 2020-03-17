package vn.gomicorp.seller.signup;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivitySignUpBinding;
import vn.gomicorp.seller.utils.Utils;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel signUpViewModel;
    private ActivitySignUpBinding activitySignUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        subscribeToNavigationChanges(signUpViewModel);
    }

    private void initDataBinding() {
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);
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
                    case SignUpEvent.VERIFY:
                        showPopupVerify();
                        break;
                    case SignUpEvent.SIGN_UP_SUCCESS:
                        signUpSuccess();
                        break;
                    case SignUpEvent.SIGN_UP_FALSE:
                        signUpFalse(event.message);
                        break;
                    case SignUpEvent.FULLNAME_ERROR:
                        fullNameError();
                        break;
                    case SignUpEvent.FULLNAME_SUCCESS:
                        fullNameSuccess();
                        break;
                    case SignUpEvent.EMAIL_ERROR:
                        emailError();
                        break;
                    case SignUpEvent.EMAIL_SUCCESS:
                        emailSuccess();
                        break;
                    case SignUpEvent.PHONENUMBER_ERROR:
                        phoneNumberError();
                        break;
                    case SignUpEvent.PHONENUMBER_SUCCESS:
                        phoneNumberSuccess();
                        break;
                    case SignUpEvent.VERIFY_ERROR:
                        verifyError(event.message);
                        break;
                    case SignUpEvent.VERIFY_SUCCESS:
                        verifySuccess();
                        break;
                    case SignUpEvent.PWD_ERROR:
                        pwdError();
                        break;
                    case SignUpEvent.PWD_SUCCESS:
                        pwdSuccess();
                        break;
                    case SignUpEvent.CONTRYID_ERROR:
                        contryIdError();
                        break;
                    case SignUpEvent.CONTRYID_SUCCESS:
                        contryIdSuccess();
                        break;
                }
            }
        });
    }

    private void verifySuccess() {
        phoneNumberSuccess();
        Toast.makeText(this, "Verify success!", Toast.LENGTH_SHORT).show();
    }

    private void verifyError(String message) {
        phoneNumberError();
        Toast.makeText(this, "Verify error: " + message, Toast.LENGTH_SHORT).show();
    }

    public void showPopupVerify() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(getString(R.string.popup_submit));
        alertDialogBuilder.setPositiveButton(getString(R.string.submit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                signUpViewModel.submitCertificationCode();
            }
        });

        alertDialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void contryIdSuccess() {
        activitySignUpBinding.inputLayoutCountryId.setErrorEnabled(false);
    }

    private void contryIdError() {
        activitySignUpBinding.inputLayoutCountryId.setError(getString(R.string.err_input_contry_id));
        Utils.requestFocus(this, activitySignUpBinding.txtContryid);
        Utils.playVibrate(this);
    }

    private void pwdSuccess() {
        activitySignUpBinding.inputLayoutPassword.setErrorEnabled(false);
    }

    private void pwdError() {
        activitySignUpBinding.inputLayoutPassword.setError(getString(R.string.err_input_password));
        Utils.requestFocus(this, activitySignUpBinding.txtPwd);
        Utils.playVibrate(this);
    }

    private void phoneNumberSuccess() {
        activitySignUpBinding.inputLayoutPhoneNumber.setErrorEnabled(false);
    }

    private void phoneNumberError() {
        activitySignUpBinding.inputLayoutPhoneNumber.setError(getString(R.string.err_input_phone_number));
        Utils.requestFocus(this, activitySignUpBinding.txtPhoneNumber);
        Utils.playVibrate(this);
    }

    private void emailSuccess() {
        activitySignUpBinding.inputLayoutEmail.setErrorEnabled(false);
    }

    private void emailError() {
        activitySignUpBinding.inputLayoutEmail.setError(getString(R.string.err_input_email));
        Utils.requestFocus(this, activitySignUpBinding.txtEmail);
        Utils.playVibrate(this);
    }

    private void fullNameSuccess() {
        activitySignUpBinding.inputLayoutName.setErrorEnabled(false);
    }

    private void fullNameError() {
        activitySignUpBinding.inputLayoutName.setError(getString(R.string.err_input_name));
        Utils.requestFocus(this, activitySignUpBinding.txtName);
        Utils.playVibrate(this);
    }

    private void signUpFalse(String msg) {
        Toast.makeText(this, "SignUp False: " + msg, Toast.LENGTH_SHORT).show();
    }

    private void signUpSuccess() {
        //TODO: goto main activity
        Toast.makeText(this, "SignUp Success", Toast.LENGTH_SHORT).show();
    }
}
