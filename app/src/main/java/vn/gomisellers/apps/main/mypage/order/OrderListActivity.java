package vn.gomisellers.apps.main.mypage.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomisellers.apps.BaseActivity;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.Order;
import vn.gomisellers.apps.databinding.ActivityOrderListBinding;
import vn.gomisellers.apps.main.mypage.order.detail.OrderDetailActivity;
import vn.gomisellers.apps.utils.GomiConstants;

public class OrderListActivity extends BaseActivity<OrderListViewModel, ActivityOrderListBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initToolbar(getString(R.string.order_title));
        initCmd();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<OrderListEvent>() {
            @Override
            public void onChanged(OrderListEvent event) {
                if (event.getCode() == OrderListEvent.SHOW_DETAIL) {
                    Order order = event.getData();
                    Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
                    intent.putExtra(GomiConstants.EXTRA_ID, order.getId());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_list);
        viewModel = ViewModelProviders.of(this).get(OrderListViewModel.class);
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
