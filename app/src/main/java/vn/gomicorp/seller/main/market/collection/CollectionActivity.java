package vn.gomicorp.seller.main.market.collection;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.adapter.MarketListAdapter;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.CategoryType;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.databinding.ActivityCollectionBinding;
import vn.gomicorp.seller.event.OnSelectedListener;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.utils.ToastUtils;
import vn.gomicorp.seller.widgets.dialog.SelectProductDialogFragment;

public class CollectionActivity extends AppCompatActivity {
    CollectionViewModel viewModel;
    ActivityCollectionBinding binding;
    int type;
    int id;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra(GomiConstants.EXTRA_TYPE, MarketListAdapter.CollectionType.VIEW_LOADING);
        name = getIntent().getStringExtra(GomiConstants.EXTRA_TITLE);
        id = getIntent().getIntExtra(GomiConstants.EXTRA_ID, 0);
        initBinding();
        setupToolbar();
        setupCmd();
        viewModel.setCategoryId(id);
        viewModel.setCollectionType(type);

        loadData();
    }

    private void loadData() {
        viewModel.showLoading();
        switch (type) {
            case MarketListAdapter.CollectionType.MEGA_CATAGORY:
                viewModel.setCategoryType(CategoryType.MEGA_CATEGORY);
                viewModel.requestCategory();
                break;
            case MarketListAdapter.CollectionType.CATAGORY:
                viewModel.setCategoryType(CategoryType.CATEGORY);
                viewModel.requestCategory();
                break;
            case MarketListAdapter.CollectionType.SUB_CATAGORY:
                viewModel.setCategoryType(CategoryType.SUB_CATEGORY);
                viewModel.requestCategory();
                break;
            default:
                viewModel.onRefresh();
                break;
        }
    }

    private void setupCmd() {
        viewModel.getCmd().observe(this, new Observer<CollectionEvent>() {
            @Override
            public void onChanged(CollectionEvent event) {
                switch (event.code) {
                    case CollectionEvent.OPEN_SUB_CATEGORY:
                        Category category = (Category) event.getData();
                        Intents.startCategoryActivity(CollectionActivity.this, type + 1, category.getId(), category.getName());
                        break;
                    case CollectionEvent.ON_PICK:
                        showDialogPickProduct((Product) event.getData());
                        break;
                    case CollectionEvent.SELECT_ERROR:
                        ToastUtils.showToast(event.message);
                        break;
                }
            }
        });
    }

    private void showDialogPickProduct(Product product) {
        final SelectProductDialogFragment selectProductDialogFragment = SelectProductDialogFragment.getInstance(product);
        selectProductDialogFragment.setListener(new OnSelectedListener() {
            @Override
            public void onSelected(Product product) {
                viewModel.requestPickProduct(product);
                selectProductDialogFragment.dismiss();
            }
        });
        selectProductDialogFragment.show(getSupportFragmentManager(), selectProductDialogFragment.getTag());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(TextUtils.isEmpty(name) ? "" : name);
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_collection);
        viewModel = ViewModelProviders.of(this).get(CollectionViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }
}
