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
import vn.gomicorp.seller.databinding.ActivityCollectionBinding;
import vn.gomicorp.seller.utils.GomiConstants;

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
        viewModel.setName(name);
        viewModel.setId(id);
        viewModel.setType(type);
    }

    private void setupCmd() {
        viewModel.getCmd().observe(this, new Observer<CollectionEvent>() {
            @Override
            public void onChanged(CollectionEvent event) {
                if (event.code == CollectionEvent.UPDATE_TOOLBAR) {
                    getSupportActionBar().setTitle((String) event.data);
                }
            }
        });
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
        viewModel.onRefresh();
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
