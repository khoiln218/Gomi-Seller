package vn.gomicorp.seller.main.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.Collection;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.databinding.FragmentMarketBinding;
import vn.gomicorp.seller.event.OnSelectedListener;
import vn.gomicorp.seller.main.MainActivity;
import vn.gomicorp.seller.utils.Intents;
import vn.gomicorp.seller.utils.ToastUtils;
import vn.gomicorp.seller.widgets.dialog.SelectProductDialogFragment;

public class MarketFragment extends Fragment {

    private FragmentMarketBinding binding;
    private MarketViewModel viewModel;

    public static MarketFragment INSTANCE;

    public static MarketFragment getInstance() {
        if (INSTANCE == null)
            INSTANCE = new MarketFragment();
        return INSTANCE;
    }

    private MarketFragment() {
    }

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
        viewModel = (MarketViewModel) MainActivity.obtainViewModel(getActivity(), MainActivity.MARKET);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getActivity());
        initCmd();

        return binding.getRoot();
    }

    private void initCmd() {
        viewModel.getCmd().observe(this, new Observer<MarketEvent>() {
            @Override
            public void onChanged(MarketEvent event) {
                switch (event.code) {
                    case MarketEvent.SELECT_ERROR:
                        ToastUtils.showToast(event.message);
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

    @Override
    public void onResume() {
        super.onResume();
        viewModel.requestCollections();
    }
}
