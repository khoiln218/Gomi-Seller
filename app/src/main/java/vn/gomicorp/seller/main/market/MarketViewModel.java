package vn.gomicorp.seller.main.market;

import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.adapter.MarketListAdapter;
import vn.gomicorp.seller.adapter.ProductItemAdapter;
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
import vn.gomicorp.seller.event.OnProductAdapterInitListener;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 3/23/2020.
 */
public class MarketViewModel extends BaseViewModel {
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

    private List<ProductItemAdapter> adapters = new ArrayList<>();
    public List<Collection> collections = new ArrayList<>();
    private MarketListAdapter adapter;

    private ProductRepository mProductRepository = ProductRepository.getInstance();
    public MutableLiveData<MarketListAdapter> marketListAdapter = new MutableLiveData<>();

    public MarketViewModel() {
        adapter = new MarketListAdapter(collections, productHandler, categoryHandler, collectionHandler, onProductAdapterInitListener);
        marketListAdapter.setValue(adapter);
    }

    public OnProductAdapterInitListener onProductAdapterInitListener = new OnProductAdapterInitListener() {
        @Override
        public void init(ProductItemAdapter adapter) {
            adapters.add(adapter);
        }
    };

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
        cmd.call(new MarketEvent(MarketEvent.SELECT_ERROR, message));
    }

    private void updateProduct(Product product) {
        for (Collection collection : collections) {
            for (Parcelable parcelable : collection.getData()) {
                if (parcelable instanceof Product) {
                    Product item = (Product) parcelable;
                    if (TextUtils.equals(product.getId(), item.getId())) {
                        item.setIsSelling(product.getIsSelling());
                        updateCollection();
                        for (ProductItemAdapter adapter : adapters)
                            adapter.notifyItemChanged(product);
                        break;
                    }
                } else {
                    break;
                }
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
                if (result.getCode() == CODE_OK) {
                    List<Collection> collectionList = new ArrayList<>();

                    //banner
                    List<Parcelable> banners = new ArrayList<>();
                    //TODO: code demo
                    if (result.getResult().getBannerList().size() == 0) {
                        for (int i = 0; i < 5; i++) {
                            banners.add(new Banner(i, i % 2 == 0 ? "http://192.168.1.37:2526/banner/elravie.jpg" : "http://192.168.1.37:2526/banner/booki.jpg"));
                        }
                    } else {
                        banners.addAll(result.getResult().getBannerList());
                    }
                    collectionList.add(new Collection(MarketListAdapter.CollectionType.BANNER, banners));

                    //category
                    List<Parcelable> categorys = new ArrayList<>();
                    categorys.addAll(result.getResult().getMegaCateList());
                    collectionList.add(new Collection(MarketListAdapter.CollectionType.MEGA_CATAGORY, categorys));

                    //collectionlist
                    for (Introduce.CollectionListBean collectionListBean : result.getResult().getCollectionList()) {
                        List<Parcelable> productParcelableList = new ArrayList<>();
                        productParcelableList.addAll(collectionListBean.getProductList());
                        collectionList.add(new Collection(collectionListBean.getId(), collectionListBean.getName(), productParcelableList));
                    }

                    //product seen
                    List<Parcelable> productList = new ArrayList<>();
                    productList.addAll(result.getResult().getProductSeen().getProductList());
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
        setErrorMessage(collections.size() > 0 ? null : "Not Result");
        adapter.setCollections(collections);
    }

    private void refresh() {
        collections.clear();
        updateCollection();
        adapters.clear();
    }
}
