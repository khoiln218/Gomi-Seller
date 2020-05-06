package vn.gomisellers.apps.main.mypage.info;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import vn.gomisellers.apps.BaseActivity;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.databinding.ActivityAccountInformationBinding;

public class AccountInformationActivity extends BaseActivity<AccountInformationViewModel, ActivityAccountInformationBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initToolbar(getString(R.string.account_change_info));

        getBinding().pager.setAdapter(new AccountInformationAdapter(this));
        setupTab();
        EventBus.getDefault().register(this);
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onMessageEvent(AccountEvent event) {
        if (event.getCode() == AccountEvent.SHOW_LOADDING) {
            getViewModel().showLoading();
        } else if (event.getCode() == AccountEvent.HIDE_LOADDING) {
            getViewModel().hideLoading();
        } else if (event.getCode() == AccountEvent.UPDATE_DONE) {
            finish();
        }
    }
}
