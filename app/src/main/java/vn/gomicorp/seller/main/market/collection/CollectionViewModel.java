package vn.gomicorp.seller.main.market.collection;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.adapter.MarketListAdapter;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.event.OnLoadMoreListener;
import vn.gomicorp.seller.event.OnLoadTabListener;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 3/26/2020.
 */
public class CollectionViewModel extends ViewModel implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private int type;
    private int id;
    private String name;
    private int page = 0;

    public MutableLiveData<List<Product>> products = new MutableLiveData<>();
    public MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    public MutableLiveData<Boolean> refreshing = new MutableLiveData<>();

    private MultableLiveEvent<CollectionEvent> cmd = new MultableLiveEvent<>();

    public CollectionViewModel() {
        refreshed();
    }

    public ProductHandler productHandler = new ProductHandler() {
        @Override
        public void onShow(Product product) {

        }

        @Override
        public void onPick(Product product) {

        }
    };

    public OnLoadTabListener onLoadTabListener = new OnLoadTabListener() {
        @Override
        public void onLoaded(Category selectedCate) {
            id = selectedCate.getId();
            name = selectedCate.getName();
            updateToolbar(name);
            onRefresh();
        }

        @Override
        public void onLoadFails() {
            products.setValue(new ArrayList<Product>());
        }
    };

    private void updateToolbar(String name) {
        CollectionEvent event = new CollectionEvent(CollectionEvent.UPDATE_TOOLBAR);
        event.setData(name);
        cmd.call(event);
    }

    @Override
    public void onRefresh() {
        page = 0;
        switch (type) {
            case MarketListAdapter.CollectionType.CATAGORY:
                initCategory();
                break;
            case MarketListAdapter.CollectionType.NEW_PRODUCT:
            case MarketListAdapter.CollectionType.RECOMEND_PRODUCT:
                initCollection();
                break;
            case MarketListAdapter.CollectionType.SEEN_PRODUCT:
                initProductSeen();
                break;
            default:
                initCollection();
                break;
        }
    }

    @Override
    public void onLoadMore() {
        page++;
        switch (type) {
            case MarketListAdapter.CollectionType.CATAGORY:
                loadMoreCategory();
                break;
            case MarketListAdapter.CollectionType.NEW_PRODUCT:
            case MarketListAdapter.CollectionType.RECOMEND_PRODUCT:
                loadMoreCollection();
                break;
            case MarketListAdapter.CollectionType.SEEN_PRODUCT:
                loadMoreProductSeen();
                break;
            default:
                initCollection();
                break;
        }
    }

    private void loadMoreProductSeen() {
        requestProductSeen();
    }

    private void loadMoreCollection() {
        requestProductListByCollectionId(type);
    }

    private void loadMoreCategory() {
        requestProductListByCategoryId(id);
    }

    private void initProductSeen() {
        requestProductSeen();
    }

    private void requestProductSeen() {
        refreshed();
        updateProductList();
    }

    private void initCollection() {
        requestProductListByCollectionId(type);
    }

    private void requestProductListByCollectionId(int id) {
        refreshed();
        updateProductList();
    }

    private void initCategory() {
        requestSubCategory(id);
    }

    private void requestSubCategory(int id) {
        updateCategory();
        requestProductListByCategoryId(id);
    }

    private void updateCategory() {
    }

    private void requestProductListByCategoryId(int id) {
        refreshed();
        updateProductList();
    }

    private void updateProductList() {
    }

    public MultableLiveEvent<CollectionEvent> getCmd() {
        return cmd;
    }

    private void refreshed() {
        refreshing.setValue(false);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
