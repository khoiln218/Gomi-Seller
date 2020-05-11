package vn.gomisellers.apps.main.mypage.order.detail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.data.OrderRepository;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.model.api.OrderDetailRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.Order;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.data.source.remote.ResultCode;
import vn.gomisellers.apps.event.BaseEvent;
import vn.gomisellers.apps.event.OrderHandler;
import vn.gomisellers.apps.event.ProductHandler;

/**
 * Created by KHOI LE on 4/29/2020.
 */
public class OrderDetailViewModel extends BaseViewModel<BaseEvent> implements OrderHandler {

    private OrderRepository mOrderRepository;

    public MutableLiveData<OrderDetailAdapter> orderAdapterMutableLiveData;

    private Order mOrder;
    private OrderDetailAdapter adapter;

    public OrderDetailViewModel() {
        mOrderRepository = OrderRepository.getInstance();
        orderAdapterMutableLiveData = new MutableLiveData<>();
        adapter = new OrderDetailAdapter(this);
        orderAdapterMutableLiveData.setValue(adapter);
    }

    void requestOrderInformation(String id) {
        OrderDetailRequest request = new OrderDetailRequest();
        request.setId(id);
        mOrderRepository.findbyid(request, new ResultListener<ResponseData<Order>>() {
            @Override
            public void onLoaded(ResponseData<Order> result) {
                loaded();
                if (result.getCode() == ResultCode.CODE_OK) {
                    mOrder = result.getResult();
                    updateOrder();
                } else {
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                checkConnection(error);
            }
        });
    }

    private void updateOrder() {
        adapter.setOrder(mOrder);
    }

    void showLoading() {
        showProgressing();
    }

    @Override
    public void onShowProduct(String id) {

    }
}
