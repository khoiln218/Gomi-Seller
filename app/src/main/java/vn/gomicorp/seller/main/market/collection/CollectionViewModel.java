package vn.gomicorp.seller.main.market.collection;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.adapter.MarketListAdapter;
import vn.gomicorp.seller.adapter.ProductItemAdapter;
import vn.gomicorp.seller.data.ProductRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.ShopRepository;
import vn.gomicorp.seller.data.source.model.api.CategoryByIdRequest;
import vn.gomicorp.seller.data.source.model.api.CollectionByIdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.CategoryType;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.event.OnLoadMoreListener;
import vn.gomicorp.seller.event.OnLoadTabListener;
import vn.gomicorp.seller.event.ProductHandler;

/**
 * Created by KHOI LE on 3/26/2020.
 */
public class CollectionViewModel extends ViewModel implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private final int CODE_OK = 200;
    private final int INIT_PAGE = 1;
    private int type;
    private int id;
    private String name;
    private int page = INIT_PAGE;
    private int totalPage = 0;
    private int categoryType;

    private List<Product> products = new ArrayList<>();
    private ProductItemAdapter adapter;

    private ProductRepository mProductRepository = ProductRepository.getInstance();
    private ShopRepository mShopRepository = ShopRepository.getInstance();

    public MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    public MutableLiveData<Boolean> refreshing = new MutableLiveData<>();
    public MutableLiveData<ProductItemAdapter> productItemAdapter = new MutableLiveData<>();

    private MultableLiveEvent<CollectionEvent> cmd = new MultableLiveEvent<>();

    public CollectionViewModel() {
        refreshed();
        adapter = new ProductItemAdapter(products, productHandler, this);
        productItemAdapter.setValue(adapter);
    }

    public ProductHandler productHandler = new ProductHandler() {
        @Override
        public void onShow(Product product) {

        }

        @Override
        public void onPick(Product product) {

        }
    };

    public OnLoadTabListener onLoadTabListener = new OnLoadTabListener() {
        @Override
        public void onLoaded(Category selectedCate) {
            id = selectedCate.getId();
            categoryType = CategoryType.CATEGORY;
            onRefresh();
        }

        @Override
        public void onLoadFails() {
            products.clear();
            updateProductList();
        }
    };

    private void updateToolbar(String name) {
        CollectionEvent event = new CollectionEvent(CollectionEvent.UPDATE_TOOLBAR);
        event.setData(name);
        cmd.call(event);
    }

    @Override
    public void onRefresh() {
        page = INIT_PAGE;
        products.clear();
        updateProductList();
        switch (type) {
            case MarketListAdapter.CollectionType.CATAGORY:
                initCategory();
                break;
            case MarketListAdapter.CollectionType.NEW_PRODUCT:
            case MarketListAdapter.CollectionType.RECOMEND_PRODUCT:
                initCollection();
                break;
            case MarketListAdapter.CollectionType.SEEN_PRODUCT:
                initProductSeen();
                break;
        }
    }

    @Override
    public void onLoadMore() {
        if (page >= totalPage) return;
        page++;
        switch (type) {
            case MarketListAdapter.CollectionType.CATAGORY:
                loadMoreCategory();
                break;
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
        requestProductListByCollectionId(type);
    }

    private void loadMoreCategory() {
        requestProductListByCategoryId(id);
    }

    private void initProductSeen() {
        requestProductSeen();
    }

    private void requestProductSeen() {
        adapter.setLoading();
        CollectionByIdRequest request = new CollectionByIdRequest();
        mProductRepository.findbyseen(request, page, new ResultListener<ResponseData<List<Product>>>() {
            @Override
            public void onLoaded(ResponseData<List<Product>> result) {
                refreshed();
                if (result.getCode() == CODE_OK) {
                    products.addAll(result.getResult());
                    totalPage = result.getResult().size() > 0 ? result.getResult().get(0).getTotalPage() : 0;
                    updateProductList();
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                refreshed();
            }
        });
    }

    private void initCollection() {
        requestProductListByCollectionId(type);
    }

    private void requestProductListByCollectionId(int id) {
        adapter.setLoading();
        CollectionByIdRequest request = new CollectionByIdRequest();
        request.setFindById(id);
        mProductRepository.findbycollection(request, page, new ResultListener<ResponseData<List<Product>>>() {
            @Override
            public void onLoaded(ResponseData<List<Product>> result) {
                refreshed();
                if (result.getCode() == CODE_OK) {
                    products.addAll(result.getResult());
                    totalPage = result.getResult().size() > 0 ? result.getResult().get(0).getTotalPage() : 0;
                    updateProductList();
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                refreshed();
            }
        });
    }

    private void initCategory() {
        requestProductListByCategoryId(id);
    }

    void requestCategory(final int id) {
        CategoryByIdRequest request = new CategoryByIdRequest();
        request.setCategoryType(categoryType);
        request.setFindById(id);
        mShopRepository.findcatebytype(request, new ResultListener<ResponseData<List<Category>>>() {
            @Override
            public void onLoaded(ResponseData<List<Category>> result) {
                refreshed();
                if (result.getCode() == CODE_OK) {
                    categories.setValue(result.getResult());
                    updateCategory();
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                refreshed();
            }
        });

    }

    private void requestProductListByCategoryId(int id) {
        adapter.setLoading();
        CategoryByIdRequest request = new CategoryByIdRequest();
        request.setCategoryType(categoryType);
        request.setFindById(id);
        mProductRepository.findbycategory(request, page, new ResultListener<ResponseData<List<Product>>>() {
            @Override
            public void onLoaded(ResponseData<List<Product>> result) {
                refreshed();
                if (result.getCode() == CODE_OK) {
                    products.addAll(result.getResult());
                    totalPage = result.getResult().size() > 0 ? result.getResult().get(0).getTotalPage() : 0;
                    updateProductList();
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                refreshed();
            }
        });
    }

    private void updateCategory() {
    }

    private void updateProductList() {
        adapter.setProductList(products);
    }

    public MultableLiveEvent<CollectionEvent> getCmd() {
        return cmd;
    }

    private void refreshed() {
        refreshing.setValue(false);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }
}
