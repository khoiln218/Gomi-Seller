package vn.gomicorp.seller.main.market.collection.subcate;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vn.gomicorp.seller.BaseActivity;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.CategoryType;
import vn.gomicorp.seller.databinding.ActivitySubCategoryBinding;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.ToastUtils;

public class SubCategoryActivity extends BaseActivity<SubCategoryViewModel, ActivitySubCategoryBinding> {
    private List<CategoryItem> categoryItemList;
    private SubCategoryAdapter adapter;
    private int id;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            id = getIntent().getIntExtra(GomiConstants.EXTRA_ID, 0);
            name = getIntent().getStringExtra(GomiConstants.EXTRA_TITLE);
        }

        initBinding();
        setupToolbar();
        categoryItemList = new ArrayList<>();
    }

    private void setupTab() {
        new TabLayoutMediator(getBinding().tabLayout, getBinding().pager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(categoryItemList.get(position).getName());
                    }
                }
        ).attach();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(TextUtils.isEmpty(name) ? "" : name);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_category);
        viewModel = ViewModelProviders.of(this).get(SubCategoryViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoryItemList.clear();
        getViewModel().requestCategory(id, new ResultListener<List<Category>>() {
            @Override
            public void onLoaded(List<Category> result) {
                for (Category category : result) {
                    int cateType = CategoryType.SUB_CATEGORY;
                    int cateId = category.getId();
                    if (category.getId() == 0) {
                        cateType = CategoryType.CATEGORY;
                        cateId = id;
                    }
                    categoryItemList.add(new CategoryItem(cateType, cateId, category.getName()));
                }
                adapter = new SubCategoryAdapter(SubCategoryActivity.this, categoryItemList);
                getBinding().pager.setAdapter(adapter);
                setupTab();
            }

            @Override
            public void onDataNotAvailable(String error) {
                ToastUtils.showToast(error);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
