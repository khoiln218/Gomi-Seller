package vn.gomisellers.apps;

import android.net.ConnectivityManager;
import android.net.Network;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import vn.gomisellers.apps.utils.ConnectionHelper;
import vn.gomisellers.apps.utils.ToastUtils;

/**
 * Created by KHOI LE on 4/9/2020.
 */
public abstract class BaseActivity<VM extends ViewModel, V extends ViewDataBinding> extends AppCompatActivity {
    protected ViewDataBinding binding;
    protected ViewModel viewModel;

    private boolean isLost = false;

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            if (isLost)
                networkConnected();
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            isLost = true;
            networkLost();
        }
    };

    protected abstract void initBinding();

    protected void initToolbar(String name) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(name);
        }
    }

    protected VM getViewModel() {
        return (VM) viewModel;
    }

    protected V getBinding() {
        return (V) binding;
    }

    void networkConnected() {
        ToastUtils.showToast(getString(R.string.network_connected));
    }

    void networkLost() {
        ToastUtils.showToast(getString(R.string.network_lost));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.getInstance().registerNetworkCallback(networkCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ConnectionHelper.getInstance().unregisterNetworkCallback(networkCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
