package vn.gomicorp.seller.main.market.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.material.appbar.AppBarLayout;

import vn.gomicorp.seller.data.source.model.data.Product;

/**
 * Created by KHOI LE on 3/27/2020.
 */
public class ProductDetailViewModel extends ViewModel implements AppBarLayout.OnOffsetChangedListener {
    private int scrollRange;

    public MutableLiveData<Product> product = new MutableLiveData<>();
    public MutableLiveData<Boolean> isShow = new MutableLiveData<>();

    public ProductDetailViewModel() {
        scrollRange = -1;
        isShow.setValue(true);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (scrollRange == -1)
            scrollRange = appBarLayout.getTotalScrollRange();

        if (scrollRange + verticalOffset == 0 && product != null) {
            isShow.setValue(true);
        } else if (isShow.getValue() != null && isShow.getValue()) {
            isShow.setValue(false);
        }
    }

    public void setProduct(Product product) {
        this.product.postValue(product);
    }

    void requestProductById(String productId) {
        //TODO: get product infomation
    }
}
