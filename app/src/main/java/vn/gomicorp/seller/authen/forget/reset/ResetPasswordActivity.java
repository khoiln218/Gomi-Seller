package vn.gomicorp.seller.authen.forget.reset;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivityResetPaswordBinding;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Utils;

public class ResetPasswordActivity extends BaseActivity<ResetPasswordViewModel, ActivityResetPaswordBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || getIntent().getStringExtra(GomiConstants.EXTRA_ID) == null)
            finish();
        String userId = getIntent().getStringExtra(GomiConstants.EXTRA_ID);
        initBinding();
        setupToolbar();
        setupCmd();

        getViewModel().setUserId(userId);
    }

    private void setupCmd() {
        getViewModel().getCmd().observe(this, new Observer<ResetEvent>() {
            @Override
            public void onChanged(ResetEvent event) {
                switch (event.code) {
                    case ResetEvent.RESET_SUCCESS:
                        setResult(RESULT_OK);
                        finish();
                        break;
                    case ResetEvent.HIDE_KEYBOARD:
                        Utils.hideSoftKeyboard(ResetPasswordActivity.this);
                        break;
                }
            }
        });
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_pasword);
        viewModel = ViewModelProviders.of(this).get(ResetPasswordViewModel.class);
        getBinding().setViewModel(getViewModel());
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
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
