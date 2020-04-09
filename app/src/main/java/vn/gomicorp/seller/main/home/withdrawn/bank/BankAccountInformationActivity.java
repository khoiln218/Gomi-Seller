package vn.gomicorp.seller.main.home.withdrawn.bank;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivityBankAccoutInfomationBinding;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Utils;

public class BankAccountInformationActivity extends BaseActivity implements BankAccountInformationListener {

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
        ((ActivityBankAccoutInfomationBinding) binding).setViewModel((BankAccountInformationViewModel) viewModel);
        ((BankAccountInformationViewModel) viewModel).setListener(this);
        binding.setLifecycleOwner(this);
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

    @Override
    public void selectBank() {
        Utils.hideSoftKeyboard(this);
        Intent intent = new Intent(this, BankListActivity.class);
//        intent.putExtra(GomiConstants.EXTRA_ID, bankAccount.getBankId());
        startActivityForResult(intent, GomiConstants.RC_SELLER_BANK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ((BankAccountInformationViewModel) viewModel).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((BankAccountInformationViewModel) viewModel).setListener(null);
    }
}
