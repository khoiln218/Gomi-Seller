package vn.gomicorp.seller.main.home.withdrawn;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivityWithdrawnBinding;
import vn.gomicorp.seller.data.source.model.data.BankAccount;
import vn.gomicorp.seller.main.home.withdrawn.bank.BankAccountInformationActivity;
import vn.gomicorp.seller.main.home.withdrawn.coupon.CouponActivity;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.widgets.dialog.WithdrawnBankDialogFragment;

public class WithdrawnActivity extends BaseActivity implements WithdrawnListener {

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
        ((ActivityWithdrawnBinding) binding).setViewModel((WithdrawnViewModel) viewModel);
        ((WithdrawnViewModel) viewModel).setListener(this);
        binding.setLifecycleOwner(this);
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
    protected void onDestroy() {
        super.onDestroy();
        ((WithdrawnViewModel) viewModel).setListener(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GomiConstants.RC_SELLER_BANK && resultCode == RESULT_OK) {
            BankAccount bankAccount = data.getParcelableExtra(GomiConstants.EXTRA_PARCELABLE);
            showEnterAmountDialog(bankAccount);
        }
    }

    private void showEnterAmountDialog(BankAccount bankAccount) {
        WithdrawnBankDialogFragment fragment = WithdrawnBankDialogFragment.newInstance(bankAccount);
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }

    @Override
    public void bank() {
        Intent intent = new Intent(WithdrawnActivity.this, BankAccountInformationActivity.class);
        startActivityForResult(intent, GomiConstants.RC_SELLER_BANK);
    }

    @Override
    public void coupon() {
        startActivity(new Intent(this, CouponActivity.class));
    }
}
