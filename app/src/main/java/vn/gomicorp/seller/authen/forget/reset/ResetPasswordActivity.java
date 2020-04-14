package vn.gomicorp.seller.authen.forget.reset;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivityResetPaswordBinding;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.utils.ToastUtils;
import vn.gomicorp.seller.utils.Utils;

public class ResetPasswordActivity extends AppCompatActivity {

    private ActivityResetPaswordBinding binding;
    private ResetPasswordViewModel viewModel;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            userId = getIntent().getStringExtra("UserId");
        } catch (Exception e) {
            e.printStackTrace();
        }

        initDataBinding();
        setupToolbar();
        setupCmd();

        viewModel.setUserId(userId);
    }

    private void setupCmd() {
        viewModel.getCmd().observe(this, new Observer<ResetEvent>() {
            @Override
            public void onChanged(ResetEvent event) {
                switch (event.code) {
                    case ResetEvent.NEW_PASSWORD_ERROR:
                        newPwdError();
                        break;
                    case ResetEvent.NEW_PASSWORD_SUCCESS:
                        newPwdSuccess();
                        break;
                    case ResetEvent.RESET_EEROR:
                        resetError(event.message);
                        break;
                    case ResetEvent.RESET_SUCCESS:
                        resetSuccess();
                        break;
                }
            }
        });
    }

    private void resetSuccess() {
        Intents.directToMainActivity(this);
    }

    private void resetError(String error) {
        ToastUtils.showToast(error);
    }

    private void newPwdSuccess() {
        binding.inputLayoutNewPassword.setErrorEnabled(false);
    }

    private void newPwdError() {
        binding.inputLayoutNewPassword.setError(getString(R.string.err_input_password));
        Utils.requestFocus(this, binding.inputNewPasword);
        Utils.playVibrate(this);
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_pasword);
        viewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.reset_password));
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
