package vn.gomicorp.seller;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.gomicorp.seller.utils.ConnectionHelper;

/**
 * Created by KHOI LE on 4/6/2020.
 */
public class BaseViewModel extends ViewModel {
    public MutableLiveData<Boolean> isProgressing = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    protected void showProgressing() {
        isProgressing.setValue(true);
    }

    protected void hideProgressing() {
        isProgressing.setValue(false);
    }

    protected void setErrorMessage(String error) {
        errorMessage.setValue(error);
    }

    protected void checkConnection(final String error) {
        ConnectionHelper.checkNetwork(new ConnectionHelper.OnCheckNetworkListener() {
            @Override
            public void onCheck(boolean isOnline) {
                if (!isOnline) errorMessage.setValue("Network Not Available");
                else errorMessage.setValue(error);
            }
        });
    }
}
