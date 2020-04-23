package vn.gomisellers.apps.main.mypage.setting;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomisellers.apps.BaseActivity;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.databinding.ActivityAcountSettingBinding;
import vn.gomisellers.apps.utils.AlertDialogs;

public class AccountSettingActivity extends BaseActivity<AccountSettingViewModel, ActivityAcountSettingBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initToolbar(getString(R.string.setting_title));
        initCmd();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<AccountSettingEvent>() {
            @Override
            public void onChanged(AccountSettingEvent event) {
                if (event.getCode() == AccountSettingEvent.SIGN_OUT) {
                    confirmSignOut();
                }
            }
        });
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_acount_setting);
        viewModel = ViewModelProviders.of(this).get(AccountSettingViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmSignOut() {

        AlertDialogs.show(this, "", getString(R.string.logout_msg), getString(R.string.btn_cancel), getString(R.string.btn_confirm), new AlertDialogs.OnClickListener() {
            @Override
            public void onNegativeButtonClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveButtonClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getViewModel().requestSignOut();
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
