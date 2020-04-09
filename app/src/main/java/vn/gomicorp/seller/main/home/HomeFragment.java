package vn.gomicorp.seller.main.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import vn.gomicorp.seller.R;
import vn.gomicorp.seller.databinding.FragmentHomeBinding;
import vn.gomicorp.seller.main.MainActivity;
import vn.gomicorp.seller.main.home.withdrawn.WithdrawnActivity;

public class HomeFragment extends Fragment implements HomeListener {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    private static HomeFragment INSTANCE;

    public static HomeFragment getInstance() {
        if (INSTANCE == null)
            INSTANCE = new HomeFragment();
        return INSTANCE;
    }

    private HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if (binding == null)
            binding = FragmentHomeBinding.bind(view);
        viewModel = (HomeViewModel) MainActivity.obtainViewModel(getActivity(), MainActivity.HOME);
        viewModel.setListener(this);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.requestShopInfomation();
    }

    @Override
    public void withdrawn() {
        startActivity(new Intent(getActivity(), WithdrawnActivity.class));
    }
}
