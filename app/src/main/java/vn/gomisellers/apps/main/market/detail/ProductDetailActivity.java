package vn.gomisellers.apps.main.market.detail;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import vn.gomisellers.apps.BaseActivity;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.databinding.ActivityProductDetailBinding;
import vn.gomisellers.apps.event.OnSelectedListener;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.widgets.dialog.SelectProductDialogFragment;

public class ProductDetailActivity extends BaseActivity<ProductDetailViewModel, ActivityProductDetailBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null || getIntent().getStringExtra(GomiConstants.EXTRA_ID) == null)
            finish();
        initBinding();
        initToolbar();
        initCmd();
        getViewModel().setProductId(getIntent().getStringExtra(GomiConstants.EXTRA_ID));
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<ProductDetailEvent>() {
            @Override
            public void onChanged(ProductDetailEvent event) {
                if (event.getCode() == ProductDetailEvent.SHOW_DETAIL) {
                    Product product = event.getData();
                    showDialogPickProduct(product);
                }
            }
        });
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);
        viewModel = ViewModelProviders.of(this).get(ProductDetailViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewModel().requestProductById();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void showDialogPickProduct(Product product) {
        final SelectProductDialogFragment selectProductDialogFragment = SelectProductDialogFragment.getInstance(product);
        selectProductDialogFragment.setListener(new OnSelectedListener() {
            @Override
            public void onSelected(Product product) {
                getViewModel().requestPickProduct(product);
                selectProductDialogFragment.dismiss();
            }
        });
        selectProductDialogFragment.show(getSupportFragmentManager(), selectProductDialogFragment.getTag());
    }
}
