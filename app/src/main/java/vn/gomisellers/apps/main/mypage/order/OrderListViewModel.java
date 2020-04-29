package vn.gomisellers.apps.main.mypage.order;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.data.source.model.data.Order;
import vn.gomisellers.apps.event.MultableLiveEvent;

/**
 * Created by KHOI LE on 4/28/2020.
 */
public class OrderListViewModel extends BaseViewModel implements OrderHandler {

    public MutableLiveData<OrderAdapter> orderAdapterMutableLiveData;

    private MultableLiveEvent<OrderListEvent> cmd;

    private List<Order> orderList;
    private OrderAdapter adapter;

    public OrderListViewModel() {
        cmd = new MultableLiveEvent<>();
        orderAdapterMutableLiveData = new MutableLiveData<>();
        orderList = new ArrayList<>();
        adapter = new OrderAdapter(orderList, this);
        orderAdapterMutableLiveData.setValue(adapter);

        dummy();
    }

    private void dummy() {
        for (int i = 0; i < 10; i++) {
            orderList.add(new Order("Đơn hàng 123450000088765",
                    "Xịt trị mụn lưng Aetem CleanBack Mist",
                    1588068563000L,
                    "http://192.168.1.33:2526/Product/gomi_97b8318a-abfb-446b-9908-18facb413d86-637195148088041676.jpg",
                    339000F, 16));
            orderList.add(new Order("Đơn hàng 123450000088766",
                    "Mặt Nạ Tế Bào Gốc Ayo Premium Cell Essence Mask Pack (1 Box 5 miếng)",
                    1588128292000L,
                    "http://192.168.1.33:2526/Product/gomi_dd24880d-b637-41c9-98fb-9eca87b33ff3-637207137845215685.jpg",
                    599000F, 10));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onShow(Order order) {
        OrderListEvent event = new OrderListEvent(OrderListEvent.SHOW_DETAIL);
        event.setData(order);
        cmd.call(event);
    }

    MultableLiveEvent<OrderListEvent> getCmd() {
        return cmd;
    }
}
