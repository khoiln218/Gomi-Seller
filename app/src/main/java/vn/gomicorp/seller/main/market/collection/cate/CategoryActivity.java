package vn.gomicorp.seller.main.market.collection.cate;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.databinding.ActivityCategoryBinding;
import vn.gomicorp.seller.event.OnSelectedListener;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.widgets.dialog.SelectProductDialogFragment;

public class CategoryActivity extends BaseActivity<CategoryViewModel, ActivityCategoryBinding> implements CategoryListener {

    private int id;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null)
            finish();

        id = getIntent().getIntExtra(GomiConstants.EXTRA_ID, 0);
        name = getIntent().getStringExtra(GomiConstants.EXTRA_TITLE);
        initBinding();
        initToolbar();
        loadData();
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewModel().onRefresh();
    }

    protected void loadData() {
        getViewModel().setCategoryId(id);
        getViewModel().setListener(this);
        getViewModel().showLoading();
        getViewModel().requestCategory();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(TextUtils.isEmpty(name) ? "" : name);
    }

    @Override
    public void openCategory(int type, int id, String name) {
        Intents.startSubCategoryActivity(this, type, id, name);
    }

    @Override
    public void pick(Product product) {
        showDialogPickProduct(product);
    }

    @Override
    public void show(Product product) {
        Intents.startProductDetailActivity(this, product.getId());
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
