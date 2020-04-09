package vn.gomicorp.seller.main.home;

import androidx.lifecycle.MutableLiveData;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.adapter.ProductItemAdapter;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.data.source.model.data.Shop;
import vn.gomicorp.seller.event.OnLoadMoreListener;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 3/30/2020.
 */
public class HomeViewModel extends BaseViewModel implements ProductHandler, OnLoadMoreListener, TabLayout.OnTabSelectedListener {
    public MutableLiveData<Shop> shop = new MutableLiveData<>();
    public MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    public MutableLiveData<ProductItemAdapter> productItemAdapter = new MutableLiveData<>();

    private ProductItemAdapter adapter;
    private List<Product> products;
    private List<Category> categoryList;

    private HomeListener listener;

    public void setListener(HomeListener listener) {
        this.listener = listener;
    }

    private int categoryId;
    private Shop mShop;

    public HomeViewModel() {
        products = new ArrayList<>();
        categoryList = new ArrayList<>();
        adapter = new ProductItemAdapter(products, this, this);
        productItemAdapter.setValue(adapter);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() > categoryList.size())
            return;

        selectCategory(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        if (tab.getPosition() > categoryList.size())
            return;

        selectCategory(tab.getPosition());
    }

    private void selectCategory(int position) {
        Category selectedCategory = categoryList.get(position);
        categoryId = selectedCategory.getId();
        requestProduct();
    }

    @Override
    public void onShow(Product product) {

    }

    @Override
    public void onPick(Product product) {

    }

    public void withdrawn() {
        if (listener != null)
            listener.withdrawn();
    }

    //TODO: debug
    public void shareSNS() {
        if (listener != null) {
            listener.shareSNS(String.format("%s%s", EappsApplication.getPreferences().getSellerUrl(), "khoile000"));
        }
    }

    void requestShopInfomation() {
        String shopId = EappsApplication.getPreferences().getShopId();
        updateShopInformation();
        requestMegaCategory();
    }

    private void updateShopInformation() {
//        EappsApplication.getPreferences().setShop(mShop);
        shop.setValue(mShop);
    }

    private void requestMegaCategory() {
        updateMegaCategory();
    }

    private void updateMegaCategory() {
        categories.setValue(categoryList);
    }

    private void requestProduct() {
        updateProductList();
    }

    private void updateProductList() {
        adapter.setProductList(products);
    }
}
