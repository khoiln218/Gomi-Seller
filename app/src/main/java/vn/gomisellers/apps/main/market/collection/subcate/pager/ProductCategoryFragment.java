package vn.gomisellers.apps.main.market.collection.subcate.pager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import vn.gomisellers.apps.BaseFragment;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.databinding.FragmentProductCategoryBinding;
import vn.gomisellers.apps.event.OnSelectedListener;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.utils.Intents;
import vn.gomisellers.apps.widgets.dialog.SelectProductDialogFragment;

public class ProductCategoryFragment extends BaseFragment<ProductCategoryViewModel, FragmentProductCategoryBinding> {
    private int categoryType;
    private int categoryId;

    public static ProductCategoryFragment newInstance(int categoryType, int categoryId) {
        ProductCategoryFragment fragment = new ProductCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(GomiConstants.EXTRA_TYPE, categoryType);
        args.putInt(GomiConstants.EXTRA_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryType = getArguments().getInt(GomiConstants.EXTRA_TYPE);
            categoryId = getArguments().getInt(GomiConstants.EXTRA_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product_category, container, false);
        if (binding == null)
            binding = FragmentProductCategoryBinding.bind(root);
        viewModel = new ProductCategoryViewModel();
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(getActivity());
        initCmd();

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        getViewModel().setCategoryId(categoryId);
        getViewModel().setCategoryType(categoryType);
        getViewModel().showLoading();
        getViewModel().onRefresh();
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onMessageEvent(ProductCategoryEvent event) {
        if (event.getCode() == ProductCategoryEvent.ON_PICK) {
            Product product = (Product) event.getData();
            if (getViewModel() != null)
                getViewModel().updateProduct(product);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<ProductCategoryEvent>() {
            @Override
            public void onChanged(ProductCategoryEvent event) {
                switch (event.getCode()) {
                    case ProductCategoryEvent.ON_PICK:
                        showDialogPickProduct((Product) event.getData());
                        break;
                    case ProductCategoryEvent.ON_SHOW:
                        Product product = (Product) event.getData();
                        Intents.startProductDetailActivity(getActivity(), product.getId());
                        break;
                }
            }
        });
    }

    private void showDialogPickProduct(Product product) {
        if (getFragmentManager() == null) return;
        final SelectProductDialogFragment selectProductDialogFragment = SelectProductDialogFragment.getInstance(product);
        selectProductDialogFragment.setListener(new OnSelectedListener() {
            @Override
            public void onSelected(Product product) {
                getViewModel().requestPickProduct(product);
                selectProductDialogFragment.dismiss();
            }
        });
        selectProductDialogFragment.show(getFragmentManager(), getTag());
    }
}
