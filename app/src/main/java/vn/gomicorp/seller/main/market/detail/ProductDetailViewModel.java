package vn.gomicorp.seller.main.market.detail;

import androidx.lifecycle.MutableLiveData;

import com.google.android.material.appbar.AppBarLayout;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.adapter.ProductDetailAdapter;
import vn.gomicorp.seller.data.ProductRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.ProductDetailRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.ToggleProductRequest;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.data.source.remote.ResultCode;
import vn.gomicorp.seller.event.ProductHandler;
import vn.gomicorp.seller.utils.ToastUtils;

/**
 * Created by KHOI LE on 3/27/2020.
 */
public class ProductDetailViewModel extends BaseViewModel implements ProductHandler, AppBarLayout.OnOffsetChangedListener {
    private ProductRepository mProductRepository = ProductRepository.getInstance();

    public MutableLiveData<ProductDetailAdapter> adapter = new MutableLiveData<>();
    public MutableLiveData<Product> product = new MutableLiveData<>();
    public MutableLiveData<Boolean> isShow = new MutableLiveData<>();

    private Product mProduct;
    private ProductDetailAdapter productDetailAdapter;
    private String productId;
    private int scrollRange;

    private ProductDetialListener listener;

    public ProductDetailViewModel() {
        scrollRange = -1;
        isShow.setValue(true);
        productDetailAdapter = new ProductDetailAdapter(new Product(), this);
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
                    ToastUtils.showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                ToastUtils.showToast(error);
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
        ToastUtils.showToast(message);
    }

    private void updateProduct() {
        productDetailAdapter.setProduct(mProduct);
        product.setValue(mProduct);
    }

    void setProductId(String productId) {
        this.productId = productId;
    }

    void setListener(ProductDetialListener listener) {
        this.listener = listener;
    }

    @Override
    public void onShow(Product product) {

    }

    @Override
    public void onPick(Product product) {
        pick(product);
    }

    private void pick(Product product) {
        if (listener != null)
            listener.pick(product);
    }
}
