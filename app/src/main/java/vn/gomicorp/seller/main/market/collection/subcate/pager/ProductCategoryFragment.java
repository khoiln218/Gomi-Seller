package vn.gomicorp.seller.main.market.collection.subcate.pager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.databinding.FragmentProductCategoryBinding;
import vn.gomicorp.seller.event.OnSelectedListener;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.utils.ToastUtils;
import vn.gomicorp.seller.widgets.dialog.SelectProductDialogFragment;

public class ProductCategoryFragment extends Fragment {

    private FragmentProductCategoryBinding binding;
    private ProductCategoryViewModel viewModel;

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
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getActivity());
        initCmd();

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.setCategoryId(categoryId);
        viewModel.setCategoryType(categoryType);
        viewModel.showLoading();
        viewModel.onRefresh();
    }

    private void initCmd() {
        viewModel.getCmd().observe(this, new Observer<ProductCategoryEvent>() {
            @Override
            public void onChanged(ProductCategoryEvent event) {
                switch (event.code) {
                    case ProductCategoryEvent.SELECT_ERROR:
                        ToastUtils.showToast(event.message);
                        break;
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
        final SelectProductDialogFragment selectProductDialogFragment = SelectProductDialogFragment.getInstance(product);
        selectProductDialogFragment.setListener(new OnSelectedListener() {
            @Override
            public void onSelected(Product product) {
                viewModel.requestPickProduct(product);
                selectProductDialogFragment.dismiss();
            }
        });
        selectProductDialogFragment.show(getFragmentManager(), getTag());
    }
}
