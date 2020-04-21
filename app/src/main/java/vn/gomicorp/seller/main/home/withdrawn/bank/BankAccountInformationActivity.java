package vn.gomicorp.seller.main.home.withdrawn.bank;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivityBankAccoutInfomationBinding;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Utils;

public class BankAccountInformationActivity extends BaseActivity<BankAccountInformationViewModel, ActivityBankAccoutInfomationBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_accout_infomation);
        initBinding();
        initToolbar(getString(R.string.bank_management));
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank_accout_infomation);
        viewModel = ViewModelProviders.of(this).get(BankAccountInformationViewModel.class);
        getBinding().setViewModel(getViewModel());
        initCmd();
        binding.setLifecycleOwner(this);
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<BankAccountEvent>() {
            @Override
            public void onChanged(BankAccountEvent event) {
                if (event.getCode() == BankAccountEvent.SELECT_BANK) {
                    Utils.hideSoftKeyboard(BankAccountInformationActivity.this);
                    Intent intent = new Intent(BankAccountInformationActivity.this, BankListActivity.class);
//        intent.putExtra(GomiConstants.EXTRA_ID, bankAccount.getBankId());
                    startActivityForResult(intent, GomiConstants.RC_SELLER_BANK);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getViewModel().onActivityResult(requestCode, resultCode, data);
    }
}
