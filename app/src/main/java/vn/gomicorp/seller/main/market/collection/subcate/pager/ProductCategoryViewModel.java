package vn.gomicorp.seller.main.market.collection.subcate.pager;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.adapter.ProductItemAdapter;
import vn.gomicorp.seller.data.ProductRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.CategoryByIdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.ToggleProductRequest;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.event.OnLoadMoreListener;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 4/6/2020.
 */
public class ProductCategoryViewModel extends BaseViewModel implements ProductHandler, OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int CODE_OK = 200;
    private static final int INIT_PAGE = 1;

    private ProductRepository mProductRepository = ProductRepository.getInstance();

    public MutableLiveData<ProductItemAdapter> productItemAdapter = new MutableLiveData<>();
    private MultableLiveEvent<ProductCategoryEvent> cmd = new MultableLiveEvent<>();

    private ProductItemAdapter adapter;
    private List<Product> products;
    private int categoryType;
    private int categoryId;
    private int page;
    private int totalPage;

    ProductCategoryViewModel() {
        products = new ArrayList<>();
        adapter = new ProductItemAdapter(products, this, this);
        productItemAdapter.setValue(adapter);
    }

    @Override
    public void onShow(Product product) {

    }

    @Override
    public void onPick(Product product) {
        pick(product);
    }

    private void pick(Product product) {
        ProductCategoryEvent event = new ProductCategoryEvent(ProductCategoryEvent.ON_PICK);
        event.setData(product);
        cmd.call(event);
    }

    void requestPickProduct(Product product) {
        ToggleProductRequest request = new ToggleProductRequest();
        request.setIsSelling(product.getIsSelling());
        request.setProductId(product.getId());
        mProductRepository.select(request, new ResultListener<ResponseData<Product>>() {
            @Override
            public void onLoaded(ResponseData<Product> result) {
                if (result.getCode() == CODE_OK)
                    updateProduct(result.getResult());
                else
                    updateFail(result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                updateFail(error);
            }
        });
    }

    private void updateFail(String message) {
        cmd.call(new ProductCategoryEvent(ProductCategoryEvent.SELECT_ERROR, message));
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
        requestProductByCategory();
    }

    @Override
    public void onLoadMore() {
        if (page >= totalPage) return;
        page++;
        requestProductByCategory();
    }

    private void requestProductByCategory() {
        adapter.setLoading();
        CategoryByIdRequest request = new CategoryByIdRequest();
        request.setCategoryType(categoryType);
        request.setFindById(categoryId);
        mProductRepository.findbycategory(request, page, new ResultListener<ResponseData<List<Product>>>() {
            @Override
            public void onLoaded(ResponseData<List<Product>> result) {
                loaded();
                if (result.getCode() == CODE_OK) {
                    products.addAll(result.getResult());
                    totalPage = result.getResult().size() > 0 ? result.getResult().get(0).getTotalPage() : 0;
                    updateProductList();
                    checkEmpty(products);
                } else {
                    setErrorMessage(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(final String error) {
                loaded();
                checkConnection(error);
            }
        });
    }

    private void loaded() {
        hideProgressing();
        refreshed();
    }

    private void updateProductList() {
        adapter.setProductList(products);
    }

    MultableLiveEvent<ProductCategoryEvent> getCmd() {
        return cmd;
    }

    void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    void showLoading() {
        showProgressing();
    }
}
