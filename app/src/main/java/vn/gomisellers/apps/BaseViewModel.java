package vn.gomisellers.apps;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import vn.gomisellers.apps.data.source.model.data.Notification;
import vn.gomisellers.apps.data.source.model.data.Order;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.event.BaseEvent;
import vn.gomisellers.apps.event.MultableLiveEvent;
import vn.gomisellers.apps.utils.ConnectionHelper;
import vn.gomisellers.apps.utils.ToastUtils;

/**
 * Created by KHOI LE on 4/6/2020.
 */
public class BaseViewModel<E extends BaseEvent> extends ViewModel {

    private MultableLiveEvent<E> cmd = new MultableLiveEvent<>();
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

    protected void checkProductEmpty(List<Product> products) {
        setErrorMessage(products.size() > 0 ? null : EappsApplication.getInstance().getString(R.string.empty));
    }

    protected void checkOrderEmpty(List<Order> orders) {
        setErrorMessage(orders.size() > 0 ? null : EappsApplication.getInstance().getString(R.string.order_empty));
    }

    protected void checkNotifyEmpty(List<Notification> notifications) {
        setErrorMessage(notifications.size() > 0 ? null : EappsApplication.getInstance().getString(R.string.notify_empty));
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

    public MultableLiveEvent<E> getCmd() {
        return cmd;
    }
}
