package vn.gomicorp.seller.main.market.collection.cate;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.databinding.ActivityCategoryBinding;
import vn.gomicorp.seller.event.OnSelectedListener;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.widgets.dialog.SelectProductDialogFragment;

public class CategoryActivity extends AppCompatActivity implements CategoryListener {
    ActivityCategoryBinding binding;
    CategoryViewModel viewModel;

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
    protected void onResume() {
        super.onResume();
        viewModel.onRefresh();
    }

    protected void loadData() {
        viewModel.setCategoryId(id);
        viewModel.setListener(this);
        viewModel.showLoading();
        viewModel.requestCategory();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(TextUtils.isEmpty(name) ? "" : name);
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        viewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
    }

    @Override
    public void openCategory(int type, int id, String name) {
        Intents.startSubCategoryActivity(this, type, id, name);
    }

    @Override
    public void pick(Product product) {
        showDialogPickProduct(product);
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
}
