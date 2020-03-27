package vn.gomicorp.seller.main.market;

import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.adapter.MarketListAdapter;
import vn.gomicorp.seller.data.ProductRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.source.model.api.IntroduceRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.ToggleProductRequest;
import vn.gomicorp.seller.data.source.model.data.Banner;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.Collection;
import vn.gomicorp.seller.data.source.model.data.Introduce;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.event.CategoryHandler;
import vn.gomicorp.seller.event.CollectionHandler;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class MarketViewModel extends ViewModel {
    private final int CODE_OK = 200;

    public ProductHandler productHandler = new ProductHandler() {
        @Override
        public void onShow(Product product) {
            select(product);
        }

        @Override
        public void onPick(Product product) {
            pick(product);
        }
    };

    public CategoryHandler categoryHandler = new CategoryHandler() {
        @Override
        public void onClick(Category category) {
            MarketEvent event = new MarketEvent(MarketEvent.ONCLICK_CATEGORY);
            event.setData(category);
            cmd.call(event);
        }
    };

    public CollectionHandler collectionHandler = new CollectionHandler() {
        @Override
        public void onSelect(Collection collection) {
            MarketEvent event = new MarketEvent(MarketEvent.ONCLICK_COLLECTION);
            collection.getData().clear();
            event.setData(collection);
            cmd.call(event);
        }
    };

    private ProductRepository mProductRepository = ProductRepository.getInstance();
    public MutableLiveData<List<Collection>> collections = new MutableLiveData<>();

    public MultableLiveEvent<MarketEvent> getCmd() {
        return cmd;
    }

    public MultableLiveEvent<MarketEvent> cmd = new MultableLiveEvent<>();

    void select(Product product) {
        requestSelectProduct(product);
    }

    private void requestSelectProduct(Product product) {
        Log.e("TAG", "requestSelectProduct: " + product.getId());
    }

    private void pick(Product product) {
        onPick(product);
    }

    private void onPick(Product product) {
        MarketEvent event = new MarketEvent(MarketEvent.ON_PICK);
        event.setData(product);
        cmd.call(event);
    }

    //TODO: refactor
    public void requestPickProduct(Product product) {
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
        cmd.call(new MarketEvent(MarketEvent.SELECT_ERROR, message));
    }

    //TODO: update item
    private void updateProduct(Product product) {
        for (Collection collection : collections.getValue()) {
            for (Parcelable parcelable : collection.getData()) {
                if (parcelable instanceof Product) {
                    Product item = (Product) parcelable;
                    if (TextUtils.equals(product.getId(), item.getId())) {
                        item.setIsSelling(product.getIsSelling());
                        collections.setValue(collections.getValue());
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    //TODO: refactor
    void requestCollections() {
        final IntroduceRequest request = new IntroduceRequest();
        mProductRepository.introduce(request, new ResultListener<ResponseData<Introduce>>() {
            @Override
            public void onLoaded(ResponseData<Introduce> result) {
                if (result.getCode() == CODE_OK) {
                    List<Collection> collectionList = new ArrayList<>();

                    //banner
                    List<Parcelable> banners = new ArrayList<>();
                    for (Banner banner : result.getResult().getBannerList()) {
                        banners.add(banner);
                    }
                    collectionList.add(new Collection(MarketListAdapter.CollectionType.BANNER, "", banners));

                    //category
                    List<Parcelable> categorys = new ArrayList<>();
                    for (Category category : result.getResult().getMegaCateList()) {
                        categorys.add(category);
                    }
                    collectionList.add(new Collection(MarketListAdapter.CollectionType.CATAGORY, "", categorys));

                    //collectionlist
                    for (Introduce.CollectionListBean collectionListBean : result.getResult().getCollectionList()) {
                        List<Parcelable> productParcelableList = new ArrayList<>();
                        for (Product product : collectionListBean.getProductList()) {
                            productParcelableList.add(product);
                        }
                        collectionList.add(new Collection(collectionListBean.getId(), collectionListBean.getName(), productParcelableList));
                    }

                    //product seen
                    List<Parcelable> productList = new ArrayList<>();
                    for (Product product : result.getResult().getProductSeen().getProductList()) {
                        productList.add(product);
                    }
                    collectionList.add(new Collection(MarketListAdapter.CollectionType.SEEN_PRODUCT, "Sản phẩm đã xem", productList));

                    //update collection
                    collections.setValue(collectionList);
                } else {
                    Log.d("reqCollections", "onLoaded-Fails: " + result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                Log.d("reqCollections", "onDataNotAvailable: " + error);
            }
        });
    }
}
