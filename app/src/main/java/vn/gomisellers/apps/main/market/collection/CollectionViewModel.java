package vn.gomisellers.apps.main.market.collection;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.adapter.MarketListAdapter;
import vn.gomisellers.apps.adapter.ProductItemAdapter;
import vn.gomisellers.apps.data.ProductRepository;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.model.api.CollectionByIdRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.ToggleProductRequest;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.data.source.remote.ResultCode;
import vn.gomisellers.apps.event.OnLoadMoreListener;
import vn.gomisellers.apps.event.ProductHandler;

/**
 * Created by KHOI LE on 3/26/2020.
 */
public class CollectionViewModel extends BaseViewModel<CollectionEvent> implements ProductHandler, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    private ProductRepository mProductRepository;

    public MutableLiveData<ProductItemAdapter> productItemAdapter;

    private List<Product> products;
    private ProductItemAdapter adapter;

    private int page;
    private int totalPage;
    private int collectionId;

    public CollectionViewModel() {
        mProductRepository = ProductRepository.getInstance();
        productItemAdapter = new MutableLiveData<>();
        products = new ArrayList<>();
        adapter = new ProductItemAdapter(products, this, this);
        productItemAdapter.setValue(adapter);
    }

    private void pick(Product product) {
        CollectionEvent<Product> event = new CollectionEvent<>(CollectionEvent.ON_PICK);
        event.setData(product);
        getCmd().call(event);
    }

    private void showDetail(Product product) {
        CollectionEvent<Product> event = new CollectionEvent<>(CollectionEvent.ON_SHOW);
        event.setData(product);
        getCmd().call(event);
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
                if (result.getCode() == ResultCode.CODE_OK)
                    updateProduct(result.getResult());
                else
                    showToast(result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                showToast(error);
            }
        });
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
        page = 1;
        products.clear();
        updateProductList();
        if (collectionId == MarketListAdapter.CollectionType.SEEN_PRODUCT) {
            initProductSeen();
        } else {
            initCollection();
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
        if (collectionId == MarketListAdapter.CollectionType.SEEN_PRODUCT) {
            loadMoreProductSeen();
        } else {
            loadMoreCollection();
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
                if (result.getCode() == ResultCode.CODE_OK) {
                    products.addAll(result.getResult());
                    totalPage = result.getResult().size() > 0 ? result.getResult().get(0).getTotalPage() : 0;
                    products.remove(null);
                    updateProductList();
                    checkProductEmpty(products);
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
                if (result.getCode() == ResultCode.CODE_OK) {
                    products.addAll(result.getResult());
                    totalPage = result.getResult().size() > 0 ? result.getResult().get(0).getTotalPage() : 0;
                    products.remove(null);
                    updateProductList();
                    checkProductEmpty(products);
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

    void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    void showLoading() {
        showProgressing();
    }

    @Override
    public void onShow(Product product) {
        showDetail(product);
    }

    @Override
    public void onPick(Product product) {
        pick(product);
    }
}
