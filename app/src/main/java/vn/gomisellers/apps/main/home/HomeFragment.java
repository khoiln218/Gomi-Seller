package vn.gomisellers.apps.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import vn.gomisellers.apps.BaseFragment;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.databinding.FragmentHomeBinding;
import vn.gomisellers.apps.event.OnSelectedListener;
import vn.gomisellers.apps.main.home.withdrawn.WithdrawnActivity;
import vn.gomisellers.apps.utils.Intents;
import vn.gomisellers.apps.widgets.dialog.SelectProductDialogFragment;

public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (binding == null)
            binding = FragmentHomeBinding.bind(view);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        getBinding().setViewModel(getViewModel());
        binding.setLifecycleOwner(this);
        initCmd();
        return binding.getRoot();
    }

    private void initCmd() {
        getViewModel().getCmd().observe(this, new Observer<HomeEvent>() {
            @Override
            public void onChanged(HomeEvent event) {
                switch (event.getCode()) {
                    case HomeEvent.SHOW_DETAIL:
                        Product product = (Product) event.getData();
                        Intents.startProductDetailActivity(getActivity(), product.getId());
                        break;
                    case HomeEvent.REMOVE_PRODUCT:
                        showDialogPickProduct((Product) event.getData());
                        break;
                    case HomeEvent.WITHDRAW:
                        startActivity(new Intent(getActivity(), WithdrawnActivity.class));
                        break;
                    case HomeEvent.SHARE_SNS:
                        if (getActivity() != null) {
                            String content = (String) event.getData();
                            Intents.startActionSend(getActivity(), getString(R.string.share), getString(R.string.share_sub), content);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().requestShopInformation();
    }

    private void showDialogPickProduct(Product product) {
        if (getFragmentManager() == null) return;
        final SelectProductDialogFragment selectProductDialogFragment = SelectProductDialogFragment.getInstance(product);
        selectProductDialogFragment.setListener(new OnSelectedListener() {
            @Override
            public void onSelected(Product product) {
                getViewModel().requestRemoveProduct(product);
                selectProductDialogFragment.dismiss();
            }
        });
        selectProductDialogFragment.show(getFragmentManager(), getTag());
    }
}
