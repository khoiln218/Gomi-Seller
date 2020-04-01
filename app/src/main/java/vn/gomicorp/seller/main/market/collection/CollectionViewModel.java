package vn.gomicorp.seller.main.market.collection;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.adapter.MarketListAdapter;
import vn.gomicorp.seller.adapter.ProductItemAdapter;
import vn.gomicorp.seller.data.ProductRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.CollectionByIdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
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
    private final int CODE_OK = 200;
    private final int INIT_PAGE = 1;
    private int type;
    private int id;
    private String name;
    private int page = 0;
    private int totalPage = 0;

    public List<Product> products = new ArrayList<>();
    private ProductItemAdapter adapter;

    private ProductRepository mProductRepository = ProductRepository.getInstance();

    public MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    public MutableLiveData<Boolean> refreshing = new MutableLiveData<>();
    public MutableLiveData<ProductItemAdapter> productItemAdapter = new MutableLiveData<>();

    private MultableLiveEvent<CollectionEvent> cmd = new MultableLiveEvent<>();

    public CollectionViewModel() {
        refreshed();
        adapter = new ProductItemAdapter(products, productHandler, this);
        productItemAdapter.setValue(adapter);
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

        }
    };

    private void updateToolbar(String name) {
        CollectionEvent event = new CollectionEvent(CollectionEvent.UPDATE_TOOLBAR);
        event.setData(name);
        cmd.call(event);
    }

    @Override
    public void onRefresh() {
        page = INIT_PAGE;
        products.clear();
        adapter.setProductList(products);
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
        if (page >= totalPage || adapter.isLoading()) return;
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
        adapter.setLoading();
        final CollectionByIdRequest request = new CollectionByIdRequest();
        request.setFindById(id);
        mProductRepository.findbycollection(request, page, new ResultListener<ResponseData<List<Product>>>() {
            @Override
            public void onLoaded(ResponseData<List<Product>> result) {
                if (result.getCode() == CODE_OK) {
                    refreshed();
                    products.addAll(result.getResult());
                    totalPage = result.getResult().size() > 0 ? result.getResult().get(0).getTotalPage() : 0;
                    updateProductList();
                } else {
                    Log.d("TAG", "onLoaded: " + result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                Log.d("TAG", "onDataNotAvailable: " + error);
            }
        });
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
        adapter.setProductList(products);
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
