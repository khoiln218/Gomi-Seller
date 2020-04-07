package vn.gomicorp.seller.main.market.collection;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.adapter.MarketListAdapter;
import vn.gomicorp.seller.adapter.ProductItemAdapter;
import vn.gomicorp.seller.data.ProductRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.CollectionByIdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.ToggleProductRequest;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.event.OnLoadMoreListener;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 3/26/2020.
 */
public class CollectionViewModel extends BaseViewModel implements ProductHandler, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private final int CODE_OK = 200;
    private final int INIT_PAGE = 1;
    private int page = INIT_PAGE;
    private int totalPage = 0;

    private int collectionId;

    private List<Product> products = new ArrayList<>();
    private ProductItemAdapter adapter;

    private ProductRepository mProductRepository = ProductRepository.getInstance();

    public MutableLiveData<ProductItemAdapter> productItemAdapter = new MutableLiveData<>();

    private MultableLiveEvent<CollectionEvent> cmd = new MultableLiveEvent<>();

    public CollectionViewModel() {
        loaded();
        adapter = new ProductItemAdapter(products, this, this);
        productItemAdapter.setValue(adapter);
    }

    private void pick(Product product) {
        CollectionEvent event = new CollectionEvent(CollectionEvent.ON_PICK);
        event.setData(product);
        cmd.call(event);
    }

    void requestPickProduct(Product product) {
        showLoading();
        ToggleProductRequest request = new ToggleProductRequest();
        request.setIsSelling(product.getIsSelling());
        request.setProductId(product.getId());
        mProductRepository.select(request, new ResultListener<ResponseData<Product>>() {
            @Override
            public void onLoaded(ResponseData<Product> result) {
                loaded();
                if (result.getCode() == CODE_OK)
                    updateProduct(result.getResult());
                else
                    updateFail(result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                updateFail(error);
            }
        });
    }

    private void updateFail(String message) {
        cmd.call(new CollectionEvent(CollectionEvent.SELECT_ERROR, message));
    }

    private void updateProduct(Product product) {
        for (Product item : products) {
            if (TextUtils.equals(product.getId(), item.getId())) {
                item.setIsSelling(product.getIsSelling());
                adapter.notifyItemChanged(product);
                break;
            }
        }
    }

    @Override
    public void onRefresh() {
        page = INIT_PAGE;
        products.clear();
        updateProductList();
        switch (collectionId) {
            case MarketListAdapter.CollectionType.NEW_PRODUCT:
            case MarketListAdapter.CollectionType.RECOMEND_PRODUCT:
                initCollection();
                break;
            case MarketListAdapter.CollectionType.SEEN_PRODUCT:
                initProductSeen();
                break;
        }
    }

    private void initProductSeen() {
        requestProductSeen();
    }

    private void initCollection() {
        requestProductListByCollectionId();
    }

    @Override
    public void onLoadMore() {
        if (page >= totalPage) return;
        page++;
        products.add(null);
        updateProductList();
        switch (collectionId) {
            case MarketListAdapter.CollectionType.NEW_PRODUCT:
            case MarketListAdapter.CollectionType.RECOMEND_PRODUCT:
                loadMoreCollection();
                break;
            case MarketListAdapter.CollectionType.SEEN_PRODUCT:
                loadMoreProductSeen();
                break;
        }
    }

    private void loadMoreProductSeen() {
        requestProductSeen();
    }

    private void loadMoreCollection() {
        requestProductListByCollectionId();
    }

    private void requestProductSeen() {
        adapter.setLoading();
        CollectionByIdRequest request = new CollectionByIdRequest();
        mProductRepository.findbyseen(request, page, new ResultListener<ResponseData<List<Product>>>() {
            @Override
            public void onLoaded(ResponseData<List<Product>> result) {
                loaded();
                if (result.getCode() == CODE_OK) {
                    products.addAll(result.getResult());
                    totalPage = result.getResult().size() > 0 ? result.getResult().get(0).getTotalPage() : 0;
                    products.remove(null);
                    updateProductList();
                    checkEmpty(products);
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                checkConnection(error);
            }
        });
    }

    private void requestProductListByCollectionId() {
        adapter.setLoading();
        CollectionByIdRequest request = new CollectionByIdRequest();
        request.setFindById(collectionId);
        mProductRepository.findbycollection(request, page, new ResultListener<ResponseData<List<Product>>>() {
            @Override
            public void onLoaded(ResponseData<List<Product>> result) {
                loaded();
                if (result.getCode() == CODE_OK) {
                    products.addAll(result.getResult());
                    totalPage = result.getResult().size() > 0 ? result.getResult().get(0).getTotalPage() : 0;
                    products.remove(null);
                    updateProductList();
                    checkEmpty(products);
                } else {
                    setErrorMessage(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                checkConnection(error);
            }
        });
    }

    private void updateProductList() {
        adapter.setProductList(products);
    }

    public MultableLiveEvent<CollectionEvent> getCmd() {
        return cmd;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    void showLoading() {
        showProgressing();
    }

    @Override
    public void onShow(Product product) {

    }

    @Override
    public void onPick(Product product) {
        pick(product);
    }
}
