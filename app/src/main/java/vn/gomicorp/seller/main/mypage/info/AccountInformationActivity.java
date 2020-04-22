package vn.gomicorp.seller.main.mypage.info;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.ActivityAccountInformationBinding;

public class AccountInformationActivity extends BaseActivity<AccountInformationViewModel, ActivityAccountInformationBinding> implements AccountInfoListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initToolbar(getString(R.string.account_change_info));

        getBinding().pager.setAdapter(new AccountInformationAdapter(this));
        setupTab();
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account_information);
        viewModel = ViewModelProviders.of(this).get(AccountInformationViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
    }

    private void setupTab() {
        new TabLayoutMediator(getBinding().tabLayout, getBinding().pager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == AccountInformationAdapter.INFO) {
                            tab.setText(R.string.account_change_info);
                        } else {
                            tab.setText(R.string.change_password);
                        }
                    }
                }
        ).attach();
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
    public void showLoading() {
        getViewModel().showLoading();
    }

    @Override
    public void hideLoading() {
        getViewModel().hideLoading();
    }

    @Override
    public void done() {
        finish();
    }
}
