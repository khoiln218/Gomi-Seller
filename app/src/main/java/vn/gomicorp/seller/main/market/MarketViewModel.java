package vn.gomicorp.seller.main.market;

import android.os.Parcelable;
import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.R;
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
import vn.gomicorp.seller.data.source.remote.ResultCode;
import vn.gomicorp.seller.event.CategoryHandler;
import vn.gomicorp.seller.event.CollectionHandler;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class MarketViewModel extends BaseViewModel implements ProductHandler, CategoryHandler, CollectionHandler {

    private ProductRepository mProductRepository;
    public MutableLiveData<MarketListAdapter> marketListAdapter;

    private MultableLiveEvent<MarketEvent> cmd;

    private List<Collection> collections;
    private MarketListAdapter adapter;

    public MarketViewModel() {
        mProductRepository = ProductRepository.getInstance();
        marketListAdapter = new MutableLiveData<>();
        cmd = new MultableLiveEvent<>();
        collections = new ArrayList<>();
        adapter = new MarketListAdapter(collections, this);
        marketListAdapter.setValue(adapter);
    }

    void requestPickProduct(Product product) {
        showProgressing();
        ToggleProductRequest request = new ToggleProductRequest();
        request.setIsSelling(product.getIsSelling());
        request.setProductId(product.getId());
        mProductRepository.select(request, new ResultListener<ResponseData<Product>>() {
            @Override
            public void onLoaded(ResponseData<Product> result) {
                hideProgressing();
                if (result.getCode() == ResultCode.CODE_OK)
                    productChange(result.getResult());
                else
                    updateFail(result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                updateFail(error);
            }
        });
    }

    private void updateFail(String message) {
        cmd.call(new MarketEvent(MarketEvent.SELECT_ERROR, message));
    }

    private void productChange(Product product) {
        for (Collection collection : collections) {
            if (collection.getType() == MarketListAdapter.CollectionType.NEW_PRODUCT
                    || collection.getType() == MarketListAdapter.CollectionType.RECOMEND_PRODUCT
                    || collection.getType() == MarketListAdapter.CollectionType.SEEN_PRODUCT)
                updateCollection(collection, product);
        }
        adapter.notifyItemChanged(product);
    }

    private void updateCollection(Collection collection, Product product) {
        for (Parcelable parcelable : collection.getData()) {
            Product item = (Product) parcelable;
            if (TextUtils.equals(item.getId(), product.getId())) {
                item.setIsSelling(product.getIsSelling());
            }
        }
    }

    void requestCollections() {
        showProgressing();
        refresh();
        final IntroduceRequest request = new IntroduceRequest();
        mProductRepository.introduce(request, new ResultListener<ResponseData<Introduce>>() {
            @Override
            public void onLoaded(ResponseData<Introduce> result) {
                hideProgressing();
                if (result.getCode() == ResultCode.CODE_OK) {
                    List<Collection> collectionList = new ArrayList<>();

                    //banner
                    List<Parcelable> banners = new ArrayList<>();
                    //TODO: code demo
                    if (result.getResult().getBannerList().size() == 0) {
                        for (int i = 0; i < 5; i++) {
                            banners.add(new Banner(i, i % 2 == 0 ? "http://192.168.1.33:2526/banner/elravie.jpg" : "http://192.168.1.33:2526/banner/booki.jpg"));
                        }
                    } else {
                        banners.addAll(result.getResult().getBannerList());
                    }
                    collectionList.add(new Collection(MarketListAdapter.CollectionType.BANNER, banners));

                    //category
                    List<Parcelable> categorys = new ArrayList<Parcelable>(result.getResult().getMegaCateList());
                    collectionList.add(new Collection(MarketListAdapter.CollectionType.MEGA_CATEGORY, categorys));

                    //collectionlist
                    for (Introduce.CollectionListBean collectionListBean : result.getResult().getCollectionList()) {
                        List<Parcelable> productParcelableList = new ArrayList<Parcelable>(collectionListBean.getProductList());
                        collectionList.add(new Collection(collectionListBean.getId(), collectionListBean.getName(), productParcelableList));
                    }

                    //product seen
                    List<Parcelable> productList = new ArrayList<Parcelable>(result.getResult().getProductSeen().getProductList());
                    collectionList.add(new Collection(MarketListAdapter.CollectionType.SEEN_PRODUCT, EappsApplication.getInstance().getString(R.string.product_seen), productList));

                    //update collection
                    collections = collectionList;
                    updateCollection();
                } else {
                    setErrorMessage(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                checkConnection(error);
            }
        });
    }

    private void updateCollection() {
        setErrorMessage(collections.size() > 0 ? null : EappsApplication.getInstance().getString(R.string.not_result));
        adapter.setCollections(collections);
    }

    public MultableLiveEvent<MarketEvent> getCmd() {
        return cmd;
    }

    private void refresh() {
        collections.clear();
        updateCollection();
    }

    @Override
    public void onShow(Product product) {
        MarketEvent event = new MarketEvent(MarketEvent.SHOW_DETAIL);
        event.setData(product);
        cmd.call(event);
    }

    @Override
    public void onPick(Product product) {
        MarketEvent event = new MarketEvent(MarketEvent.ON_PICK);
        event.setData(product);
        cmd.call(event);
    }

    @Override
    public void onClick(Category category) {
        MarketEvent event = new MarketEvent(MarketEvent.ONCLICK_CATEGORY);
        event.setData(category);
        cmd.call(event);
    }

    @Override
    public void onSelect(Collection collection) {
        MarketEvent event = new MarketEvent(MarketEvent.ONCLICK_COLLECTION);
        collection.getData().clear();
        event.setData(collection);
        cmd.call(event);
    }
}
