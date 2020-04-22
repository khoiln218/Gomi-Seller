package vn.gomicorp.seller.main.market.collection;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.databinding.ActivityCollectionBinding;
import vn.gomicorp.seller.event.OnSelectedListener;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.widgets.dialog.SelectProductDialogFragment;

public class CollectionActivity extends BaseActivity<CollectionViewModel, ActivityCollectionBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() == null)
            return;
        String name = getIntent().getStringExtra(GomiConstants.EXTRA_TITLE);
        int id = getIntent().getIntExtra(GomiConstants.EXTRA_ID, 0);
        initBinding();
        initToolbar(TextUtils.isEmpty(name) ? "" : name);
        setupCmd();

        getViewModel().setCollectionId(id);
    }

    private void setupCmd() {
        getViewModel().getCmd().observe(this, new Observer<CollectionEvent>() {
            @Override
            public void onChanged(CollectionEvent event) {
                switch (event.getCode()) {
                    case CollectionEvent.ON_PICK:
                        showDialogPickProduct((Product) event.getData());
                        break;
                    case CollectionEvent.ON_SHOW:
                        Product product = (Product) event.getData();
                        Intents.startProductDetailActivity(CollectionActivity.this, product.getId());
                        break;
                }
            }
        });
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection);
        viewModel = ViewModelProviders.of(this).get(CollectionViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        getViewModel().showLoading();
        getViewModel().onRefresh();
    }
}
