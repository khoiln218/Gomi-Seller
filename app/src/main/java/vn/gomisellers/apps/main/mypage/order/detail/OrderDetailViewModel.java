package vn.gomisellers.apps.main.mypage.order.detail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.data.source.model.data.Order;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.event.BaseEvent;
import vn.gomisellers.apps.event.ProductHandler;

/**
 * Created by KHOI LE on 4/29/2020.
 */
public class OrderDetailViewModel extends BaseViewModel<BaseEvent> implements ProductHandler {

    public MutableLiveData<OrderDetailAdapter> orderAdapterMutableLiveData;

    private Order mOrder;
    private OrderDetailAdapter adapter;

    public OrderDetailViewModel() {
        orderAdapterMutableLiveData = new MutableLiveData<>();
        adapter = new OrderDetailAdapter(this);
        orderAdapterMutableLiveData.setValue(adapter);
    }

    void requestOrderInformation(String id) {
        adapter.setOrder(mOrder);
    }

    @Override
    public void onShow(Product product) {
        Log.e("TAG", "onShow: " + product.getId());
    }

    @Override
    public void onPick(Product product) {

    }
}
