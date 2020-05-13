package vn.gomisellers.apps.main.mypage.order.detail;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomisellers.apps.BaseActivity;
import vn.gomisellers.apps.EappsApplication;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.databinding.ActivityOrderDetailBinding;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.utils.Intents;
import vn.gomisellers.apps.utils.LogUtils;

public class OrderDetailActivity extends BaseActivity<OrderDetailViewModel, ActivityOrderDetailBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null)
            finish();

        if (!EappsApplication.getPreferences().isLogin()) {
            LogUtils.d("TAG", "user isn't login");
            Intents.startLoginActivity(this);
            finish();
        }

        String id = getIntent().getStringExtra(GomiConstants.EXTRA_ID);
        initBinding();
        initToolbar(getString(R.string.order_detail_title));
        initCmd();

        getViewModel().showLoading();
        getViewModel().requestOrderInformation(id);
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<OrderDetailEvent>() {
            @Override
            public void onChanged(OrderDetailEvent event) {
                String productId = (String) event.getData();
                Intents.startProductDetailActivity(OrderDetailActivity.this, productId);
            }
        });
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);
        viewModel = ViewModelProviders.of(this).get(OrderDetailViewModel.class);
        getBinding().setViewModel(getViewModel());
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
}
