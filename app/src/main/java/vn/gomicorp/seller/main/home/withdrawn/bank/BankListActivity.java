package vn.gomicorp.seller.main.home.withdrawn.bank;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivityBankListBinding;

public class BankListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initToolbar(getString(R.string.select_bank));
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bank_list);
        viewModel = ViewModelProviders.of(this).get(BankListViewModel.class);
        ((ActivityBankListBinding) binding).setViewModel((BankListViewModel) viewModel);
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((BankListViewModel) viewModel).showLoading();
        ((BankListViewModel) viewModel).onRefresh();
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
