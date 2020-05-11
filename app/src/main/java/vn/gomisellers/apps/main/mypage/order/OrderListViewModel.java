package vn.gomisellers.apps.main.mypage.order;

import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.data.OrderRepository;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.model.api.OrderRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.data.Order;
import vn.gomisellers.apps.data.source.remote.ResultCode;
import vn.gomisellers.apps.event.OnLoadMoreListener;

/**
 * Created by KHOI LE on 4/28/2020.
 */
public class OrderListViewModel extends BaseViewModel<OrderListEvent> implements OrderHandler, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    private OrderRepository mOrderRepository;

    public MutableLiveData<OrderAdapter> orderAdapterMutableLiveData;

    private List<Order> orderList;
    private OrderAdapter adapter;
    private int page;
    private int totalPage;

    public OrderListViewModel() {
        mOrderRepository = OrderRepository.getInstance();
        orderAdapterMutableLiveData = new MutableLiveData<>();
        orderList = new ArrayList<>();
        adapter = new OrderAdapter(orderList, this, this);
        orderAdapterMutableLiveData.setValue(adapter);

        totalPage = 0;
        page = 1;
    }

    @Override
    public void onRefresh() {
        page = 1;
        orderList.clear();
        updateOrders();
        requestOrderList();
    }

    @Override
    public void onLoadMore() {
        if (page >= totalPage) return;
        page++;
        orderList.add(null);
        updateOrders();
        requestOrderList();
    }

    private void requestOrderList() {
        adapter.setLoading();
        OrderRequest request = new OrderRequest();
        mOrderRepository.findbyshopid(request, page, new ResultListener<ResponseData<List<Order>>>() {
            @Override
            public void onLoaded(ResponseData<List<Order>> result) {
                loaded();
                if (result.getCode() == ResultCode.CODE_OK) {
                    orderList.addAll(result.getResult());
                    totalPage = result.getResult().size() > 0 ? result.getResult().get(0).getTotalPage() : 0;
                    orderList.remove(null);
                    updateOrders();
                    checkOrderEmpty(result.getResult());
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

    private void updateOrders() {
        adapter.setOrderList(orderList);
    }

    void showLoading() {
        showProgressing();
    }

    @Override
    public void onShow(Order order) {
        OrderListEvent event = new OrderListEvent(OrderListEvent.SHOW_DETAIL);
        event.setData(order);
        getCmd().call(event);
    }
}
