package vn.gomicorp.seller;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.utils.ConnectionHelper;
import vn.gomicorp.seller.utils.ToastUtils;

/**
 * Created by KHOI LE on 4/6/2020.
 */
public class BaseViewModel extends ViewModel {

    public MutableLiveData<Boolean> isProgressing = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public MutableLiveData<Boolean> refreshing = new MutableLiveData<>();

    protected void showProgressing() {
        isProgressing.setValue(true);
    }

    protected void hideProgressing() {
        isProgressing.setValue(false);
    }

    protected void setErrorMessage(String error) {
        errorMessage.setValue(error);
    }

    protected void checkEmpty(List<Product> products) {
        setErrorMessage(products.size() > 0 ? null : EappsApplication.getInstance().getString(R.string.empty));
    }

    private void refreshed() {
        refreshing.setValue(false);
    }

    protected void loaded() {
        hideProgressing();
        refreshed();
    }

    protected void checkConnection(final String error) {
        ConnectionHelper.getInstance().checkNetwork(new ConnectionHelper.OnCheckNetworkListener() {
            @Override
            public void onCheck(boolean isOnline) {
                if (!isOnline)
                    errorMessage.setValue(EappsApplication.getInstance().getString(R.string.network_error));
                else errorMessage.setValue(error);
            }
        });
    }

    protected void showToast(String msg) {
        ToastUtils.showToast(msg);
    }
}
