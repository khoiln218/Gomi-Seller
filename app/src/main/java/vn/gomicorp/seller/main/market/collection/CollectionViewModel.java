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
import vn.gomicorp.seller.data.source.remote.ResultCode;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.event.OnLoadMoreListener;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 3/26/2020.
 */
public class CollectionViewModel extends BaseViewModel implements ProductHandler, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private final int INIT_PAGE = 1;

    private ProductRepository mProductRepository;

    public MutableLiveData<ProductItemAdapter> productItemAdapter;
    private MultableLiveEvent<CollectionEvent> cmd;

    private List<Product> products;
    private ProductItemAdapter adapter;

    private int page;
    private int totalPage;
    private int collectionId;

    public CollectionViewModel() {
        mProductRepository = ProductRepository.getInstance();
        productItemAdapter = new MutableLiveData<>();
        cmd = new MultableLiveEvent<>();
        products = new ArrayList<>();
        adapter = new ProductItemAdapter(products, this, this);
        productItemAdapter.setValue(adapter);
    }

    private void pick(Product product) {
        CollectionEvent<Product> event = new CollectionEvent(CollectionEvent.ON_PICK);
        event.setData(product);
        cmd.call(event);
    }

    private void showDetail(Product product) {
        CollectionEvent<Product> event = new CollectionEvent(CollectionEvent.ON_SHOW);
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
                if (result.getCode() == ResultCode.CODE_OK)
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
                if (result.getCode() == ResultCode.CODE_OK) {
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
