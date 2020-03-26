package vn.gomicorp.seller.main.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.FragmentMarketBinding;
import vn.gomicorp.seller.main.MainActivity;

public class MarketFragment extends Fragment {

    private FragmentMarketBinding binding;
    private MarketViewModel viewModel;

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
        viewModel = MainActivity.obtainViewModel(getActivity());
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getActivity());
        initCmd();

        return binding.getRoot();
    }

    private void initCmd() {
        viewModel.getCmd().observe(this, new Observer<MarketEvent>() {
            @Override
            public void onChanged(MarketEvent event) {
                if (event.code == MarketEvent.SELECT_ERROR)
                    Toast.makeText(getActivity(), "Select Error: " + event.message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.requestCollections();
    }
}
