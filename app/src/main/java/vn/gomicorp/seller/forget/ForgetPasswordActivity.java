package vn.gomicorp.seller.forget;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.source.model.data.Account;
import vn.gomicorp.seller.databinding.ActivityForgetPasswordBinding;
import vn.gomicorp.seller.forget.reset.ResetPasswordActivity;
import vn.gomicorp.seller.utils.Utils;

public class ForgetPasswordActivity extends AppCompatActivity {

    public ActivityForgetPasswordBinding binding;
    public ForgetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setupToolbar();
        setupCmd();
    }

    private void setupCmd() {
        viewModel.getCmd().observe(this, new Observer<ForgetEvent>() {
            @Override
            public void onChanged(ForgetEvent event) {
                switch (event.code) {
                    case ForgetEvent.USERNAME_ERROR:
                        usernameError();
                        break;
                    case ForgetEvent.USERNAME_SUCCESS:
                        usernameSuccess();
                        break;
                    case ForgetEvent.FORGER_EEROR:
                        forgetError(event.message);
                        break;
                    case ForgetEvent.FORGER_SUCCESS:
                        fogetSuccess((Account) event.getData());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + event.code);
                }
            }
        });
    }

    private void forgetError(String error) {
        Toast.makeText(this, "Forget error: " + error, Toast.LENGTH_SHORT).show();
    }

    private void fogetSuccess(Account account) {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.putExtra("UserId", account.getUserId());
        startActivity(intent);
        finish();
    }

    private void usernameSuccess() {
        binding.inputLayoutPhoneNumber.setErrorEnabled(false);
    }

    private void usernameError() {
        binding.inputLayoutPhoneNumber.setError(getString(R.string.err_input_username));
        Utils.playVibrate(this);
        Utils.requestFocus(this, binding.inputUserName);
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password);
        viewModel = ViewModelProviders.of(this).get(ForgetPasswordViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.forget_password));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
