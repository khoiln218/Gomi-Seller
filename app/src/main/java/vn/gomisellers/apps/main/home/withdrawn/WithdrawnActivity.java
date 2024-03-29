package vn.gomisellers.apps.main.home.withdrawn;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomisellers.apps.BaseActivity;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.BankAccount;
import vn.gomisellers.apps.databinding.ActivityWithdrawnBinding;
import vn.gomisellers.apps.main.home.withdrawn.bank.BankAccountInformationActivity;
import vn.gomisellers.apps.main.home.withdrawn.coupon.CouponActivity;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.widgets.dialog.WithdrawnBankDialogFragment;

public class WithdrawnActivity extends BaseActivity<WithdrawnViewModel, ActivityWithdrawnBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initToolbar(getString(R.string.withdrawn));
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_withdrawn);
        viewModel = ViewModelProviders.of(this).get(WithdrawnViewModel.class);
        getBinding().setViewModel(getViewModel());
        initCmd();
        binding.setLifecycleOwner(this);
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<WithdrawEvent>() {
            @Override
            public void onChanged(WithdrawEvent event) {
                switch (event.getCode()) {
                    case WithdrawEvent.WITHDRAW_BANK:
                        Intent intent = new Intent(WithdrawnActivity.this, BankAccountInformationActivity.class);
                        startActivityForResult(intent, GomiConstants.RC_SELLER_BANK);
                        break;
                    case WithdrawEvent.WITHDRAW_COUPON:
                        startActivity(new Intent(WithdrawnActivity.this, CouponActivity.class));
                        break;
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
        if (requestCode == GomiConstants.RC_SELLER_BANK && resultCode == RESULT_OK && data != null) {
            BankAccount bankAccount = data.getParcelableExtra(GomiConstants.EXTRA_PARCELABLE);
            showEnterAmountDialog(bankAccount);
        }
    }

    private void showEnterAmountDialog(BankAccount bankAccount) {
        WithdrawnBankDialogFragment fragment = WithdrawnBankDialogFragment.newInstance(bankAccount);
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }
}
