package vn.gomisellers.apps.main.market.detail;

import androidx.lifecycle.MutableLiveData;

import com.google.android.material.appbar.AppBarLayout;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.EappsApplication;
import vn.gomisellers.apps.adapter.ProductDetailAdapter;
import vn.gomisellers.apps.data.ProductRepository;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.model.api.ProductDetailRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.ToggleProductRequest;
import vn.gomisellers.apps.data.source.model.data.Product;
import vn.gomisellers.apps.data.source.remote.ResultCode;
import vn.gomisellers.apps.event.ProductDetailHandler;
import vn.gomisellers.apps.event.ProductHandler;

/**
 * Created by KHOI LE on 3/27/2020.
 */
public class ProductDetailViewModel extends BaseViewModel<ProductDetailEvent> implements ProductHandler, ProductDetailHandler, AppBarLayout.OnOffsetChangedListener {
    private ProductRepository mProductRepository;

    public MutableLiveData<ProductDetailAdapter> adapter;
    public MutableLiveData<Product> product;
    public MutableLiveData<Boolean> isShow;

    private Product mProduct;
    private ProductDetailAdapter productDetailAdapter;
    private String productId;
    private int scrollRange;

    public ProductDetailViewModel() {
        mProductRepository = ProductRepository.getInstance();
        adapter = new MutableLiveData<>();
        product = new MutableLiveData<>();
        isShow = new MutableLiveData<>();
        scrollRange = -1;
        isShow.setValue(true);
        productDetailAdapter = new ProductDetailAdapter(new Product(), this, this);
        adapter.setValue(productDetailAdapter);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1)
            scrollRange = appBarLayout.getTotalScrollRange();

        if (scrollRange + verticalOffset == 0 && mProduct != null) {
            isShow.setValue(true);
        } else if (isShow.getValue() != null && isShow.getValue()) {
            isShow.setValue(false);
        }
    }

    void shareSNS() {
        String shopUrl = String.format("%s%s", EappsApplication.getPreferences().getSellerUrl(), EappsApplication.getPreferences().getWebAddress());
        String contentShare = String.format("%s/%s", shopUrl, mProduct.getUrl());
        ProductDetailEvent<String> event = new ProductDetailEvent<>(ProductDetailEvent.SHARE_SNS);
        event.setData(contentShare);
        getCmd().call(event);
    }

    void requestProductById() {
        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductId(productId);
        mProductRepository.findbyid(request, new ResultListener<ResponseData<Product>>() {
            @Override
            public void onLoaded(ResponseData<Product> result) {
                if (result.getCode() == ResultCode.CODE_OK) {
                    mProduct = result.getResult();
                    updateProduct();
                } else {
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                showToast(error);
            }
        });
    }

    void requestPickProduct(final Product product) {
        showProgressing();
        final ToggleProductRequest request = new ToggleProductRequest();
        request.setIsSelling(product.getIsSelling());
        request.setProductId(product.getId());
        mProductRepository.select(request, new ResultListener<ResponseData<Product>>() {
            @Override
            public void onLoaded(ResponseData<Product> result) {
                loaded();
                if (result.getCode() == ResultCode.CODE_OK) {
                    mProduct.setIsSelling(result.getResult().getIsSelling());
                    updateProduct();
                } else
                    showToast(result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                showToast(error);
            }
        });
    }

    private void updateProduct() {
        productDetailAdapter.setProduct(mProduct);
        product.setValue(mProduct);
    }

    void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public void onShow(Product product) {

    }

    @Override
    public void onPick(Product product) {
        pick(product);
    }

    private void pick(Product product) {
        ProductDetailEvent<Product> event = new ProductDetailEvent<>(ProductDetailEvent.SHOW_DETAIL);
        event.setData(product);
        getCmd().call(event);
    }

    @Override
    public void onViewDescription(String description) {
        ProductDetailEvent event = new ProductDetailEvent(ProductDetailEvent.VIEW_DESCRIPTION);
        event.setData(description);
        getCmd().call(event);
    }
}
