package vn.gomisellers.apps.main.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomisellers.apps.BaseFragment;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.Category;
import vn.gomisellers.apps.data.source.model.data.Collection;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.databinding.FragmentMarketBinding;
import vn.gomisellers.apps.event.OnSelectedListener;
import vn.gomisellers.apps.utils.Intents;
import vn.gomisellers.apps.utils.ToastUtils;
import vn.gomisellers.apps.widgets.dialog.SelectProductDialogFragment;

public class MarketFragment extends BaseFragment<MarketViewModel, FragmentMarketBinding> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_market, container, false);
        if (binding == null)
            binding = FragmentMarketBinding.bind(root);
        viewModel = ViewModelProviders.of(this).get(MarketViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(getActivity());
        initCmd();

        return binding.getRoot();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<MarketEvent>() {
            @Override
            public void onChanged(MarketEvent event) {
                switch (event.getCode()) {
                    case MarketEvent.SELECT_ERROR:
                        ToastUtils.showToast(event.getMessage());
                        break;
                    case MarketEvent.ON_PICK:
                        showDialogPickProduct((Product) event.getData());
                        break;
                    case MarketEvent.ONCLICK_CATEGORY:
                        Category category = (Category) event.getData();
                        Intents.startCategoryActivity(getActivity(), category.getId(), category.getName());
                        break;
                    case MarketEvent.ONCLICK_COLLECTION:
                        Collection collection = (Collection) event.getData();
                        Intents.startCollectionActivity(getActivity(), collection.getType(), collection.getName());
                        break;
                    case MarketEvent.SHOW_DETAIL:
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

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().requestCollections();
    }
}
