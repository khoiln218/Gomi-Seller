package vn.gomisellers.apps.main.market.collection.cate;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomisellers.apps.BaseActivity;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.databinding.ActivityCategoryBinding;
import vn.gomisellers.apps.event.OnSelectedListener;
import vn.gomisellers.apps.main.market.collection.subcate.CategoryItem;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.utils.Intents;
import vn.gomisellers.apps.widgets.dialog.SelectProductDialogFragment;

public class CategoryActivity extends BaseActivity<CategoryViewModel, ActivityCategoryBinding> {

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null)
            finish();

        id = getIntent().getIntExtra(GomiConstants.EXTRA_ID, 0);
        String name = getIntent().getStringExtra(GomiConstants.EXTRA_TITLE);
        initBinding();
        initToolbar(TextUtils.isEmpty(name) ? "" : name);
        initCmd();
        loadData();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<CategoryEvent>() {
            @Override
            public void onChanged(CategoryEvent event) {
                switch (event.getCode()) {
                    case CategoryEvent.OPEN_SUB_CATEGORY: {
                        CategoryItem categoryItem = (CategoryItem) event.getData();
                        Intents.startSubCategoryActivity(CategoryActivity.this, categoryItem.getType(), categoryItem.getId(), categoryItem.getName());
                        break;
                    }
                    case CategoryEvent.PICK_PRODUCT: {
                        Product product = (Product) event.getData();
                        showDialogPickProduct(product);
                        break;
                    }
                    case CategoryEvent.SHOW_DETAIL: {
                        Product product = (Product) event.getData();
                        Intents.startProductDetailActivity(CategoryActivity.this, product.getId());
                        break;
                    }
                }
            }
        });
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
        getViewModel().showLoading();
        getViewModel().requestCategory();
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
