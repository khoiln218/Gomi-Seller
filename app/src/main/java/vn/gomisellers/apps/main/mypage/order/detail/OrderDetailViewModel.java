package vn.gomisellers.apps.main.mypage.order.detail;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.data.source.model.data.Order;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.event.ProductHandler;

/**
 * Created by KHOI LE on 4/29/2020.
 */
public class OrderDetailViewModel extends BaseViewModel implements ProductHandler {

    public MutableLiveData<OrderDetailAdapter> orderAdapterMutableLiveData;

    private Order mOrder;
    private OrderDetailAdapter adapter;

    public OrderDetailViewModel() {
        orderAdapterMutableLiveData = new MutableLiveData<>();
        adapter = new OrderDetailAdapter(mOrder, this);
        orderAdapterMutableLiveData.setValue(adapter);
    }

    void requestOrderInformation(String id) {
        dummy(id);
    }

    private void dummy(String id) {
        mOrder = new Order(id,
                "Mặt Nạ Tế Bào Gốc Ayo Premium Cell Essence Mask Pack (1 Box 5 miếng)",
                1588128292000L,
                "http://192.168.1.33:2526/Product/gomi_dd24880d-b637-41c9-98fb-9eca87b33ff3-637207137845215685.jpg",
                599000F, 10);

        //---Order Detail---------
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Product product = new Product();
            product.setId("0000-0000-0000-000" + i);
            product.setThumbnail("http://192.168.1.33:2526/Product/gomi_dd24880d-b637-41c9-98fb-9eca87b33ff3-637207137845215685.jpg");
            OrderDetail orderDetail = new OrderDetail("1", product,
                    "Mặt Nạ Tế Bào Gốc Ayo Premium Cell Essence Mask Pack (1 Box 5 miếng)",
                    599000F, 10);
            orderDetails.add(orderDetail);
        }
        //--------------------------
        mOrder.setOrderDetails(orderDetails);

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
