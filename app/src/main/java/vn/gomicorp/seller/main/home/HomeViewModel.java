package vn.gomicorp.seller.main.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.data.source.model.data.Shop;
import vn.gomicorp.seller.event.OnLoadMoreListener;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 3/30/2020.
 */
public class HomeViewModel extends ViewModel implements OnLoadMoreListener, TabLayout.OnTabSelectedListener {
    public MutableLiveData<Shop> shop = new MutableLiveData<>();
    public MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    public MutableLiveData<List<Product>> products = new MutableLiveData<>();

    public MutableLiveData<Boolean> processing = new MutableLiveData<>();

    public HomeViewModel() {
        processing(false);
    }

    public ProductHandler productHandler = new ProductHandler() {
        @Override
        public void onShow(Product product) {

        }

        @Override
        public void onPick(Product product) {

        }
    };

    @Override
    public void onLoadMore() {

    }

    private void processing(boolean isShow) {
        processing.setValue(isShow);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
