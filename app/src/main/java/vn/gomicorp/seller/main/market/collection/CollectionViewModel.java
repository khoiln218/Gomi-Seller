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
import vn.gomicorp.seller.data.ShopRepository;
import vn.gomicorp.seller.data.source.model.api.CategoryByIdRequest;
import vn.gomicorp.seller.data.source.model.api.CollectionByIdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.ToggleProductRequest;
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
public class CollectionViewModel extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private final int CODE_OK = 200;
    private final int ALL = 0;
    private final int INIT_PAGE = 1;
    private int collectionType;
    private int page = INIT_PAGE;
    private int totalPage = 0;
    private int categoryType;
    private int categoryId;

    private int selectCategoryType;
    private int selectCategoryId;

    private List<Product> products = new ArrayList<>();
    private ProductItemAdapter adapter;

    private ProductRepository mProductRepository = ProductRepository.getInstance();
    private ShopRepository mShopRepository = ShopRepository.getInstance();

    public MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    public MutableLiveData<ProductItemAdapter> productItemAdapter = new MutableLiveData<>();

    private MultableLiveEvent<CollectionEvent> cmd = new MultableLiveEvent<>();

    public CollectionViewModel() {
        loaded();
        adapter = new ProductItemAdapter(products, productHandler, this);
        productItemAdapter.setValue(adapter);
    }

    public ProductHandler productHandler = new ProductHandler() {
        @Override
        public void onShow(Product product) {

        }

        @Override
        public void onPick(Product product) {
            pick(product);
        }
    };

    public OnLoadTabListener onLoadTabListener = new OnLoadTabListener() {
        @Override
        public void onLoaded(Category selectedCate) {
            if (selectedCate.getId() == ALL) {
                showProgressing();
                selectCategoryType = categoryType;
                selectCategoryId = categoryId;
                onRefresh();
            } else if (categoryType == CategoryType.MEGA_CATEGORY) {
                openSubCategory(selectedCate);
            }/* else if (categoryType == CategoryType.CATEGORY) {
                showProgressing();
                selectCategoryType = categoryType + 1;
                selectCategoryId = selectedCate.getId();
                onRefresh();
            }*/
        }

        @Override
        public void onLoadFails() {
            products.clear();
            updateProductList();
            checkEmpty(products);
        }
    };

    private void openSubCategory(Category selectedCate) {
        CollectionEvent event = new CollectionEvent(CollectionEvent.OPEN_SUB_CATEGORY);
        event.setData(selectedCate);
        cmd.call(event);
    }

    private void pick(Product product) {
        CollectionEvent event = new CollectionEvent(CollectionEvent.ON_PICK);
        event.setData(product);
        cmd.call(event);
    }

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
        switch (collectionType) {
            case MarketListAdapter.CollectionType.MEGA_CATAGORY:
            case MarketListAdapter.CollectionType.CATAGORY:
            case MarketListAdapter.CollectionType.SUB_CATAGORY:
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

    private void initProductSeen() {
        requestProductSeen();
    }

    private void initCollection() {
        requestProductListByCollectionId(collectionType);
    }

    private void initCategory() {
        requestProductListByCategoryId(selectCategoryId);
    }

    @Override
    public void onLoadMore() {
        if (page >= totalPage) return;
        page++;
        switch (collectionType) {
            case MarketListAdapter.CollectionType.MEGA_CATAGORY:
            case MarketListAdapter.CollectionType.CATAGORY:
            case MarketListAdapter.CollectionType.SUB_CATAGORY:
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
        requestProductListByCollectionId(collectionType);
    }

    private void loadMoreCategory() {
        requestProductListByCategoryId(selectCategoryId);
    }

    private void requestProductSeen() {
        adapter.setLoading();
        CollectionByIdRequest request = new CollectionByIdRequest();
        mProductRepository.findbyseen(request, page, new ResultListener<ResponseData<List<Product>>>() {
            @Override
            public void onLoaded(ResponseData<List<Product>> result) {
                loaded();
                if (result.getCode() == CODE_OK) {
                    products.addAll(result.getResult());
                    totalPage = result.getResult().size() > 0 ? result.getResult().get(0).getTotalPage() : 0;
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

    private void requestProductListByCollectionId(int id) {
        adapter.setLoading();
        CollectionByIdRequest request = new CollectionByIdRequest();
        request.setFindById(id);
        mProductRepository.findbycollection(request, page, new ResultListener<ResponseData<List<Product>>>() {
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
            public void onDataNotAvailable(String error) {
                loaded();
                checkConnection(error);
            }
        });
    }

    void requestCategory() {
        CategoryByIdRequest request = new CategoryByIdRequest();
        request.setCategoryType(categoryType);
        request.setFindById(selectCategoryId);
        mShopRepository.findcatebytype(request, new ResultListener<ResponseData<List<Category>>>() {
            @Override
            public void onLoaded(ResponseData<List<Category>> result) {
                loaded();
                if (result.getCode() == CODE_OK) {
                    categories.setValue(result.getResult());
                    updateCategory();
                    if (result.getResult().size() < 2)
                        requestProductListByCategoryId(selectCategoryId);
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
            }
        });
    }

    private void requestProductListByCategoryId(int id) {
        adapter.setLoading();
        CategoryByIdRequest request = new CategoryByIdRequest();
        request.setCategoryType(selectCategoryType);
        request.setFindById(id);
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

    private void updateCategory() {
    }

    private void updateProductList() {
        adapter.setProductList(products);
    }

    public MultableLiveEvent<CollectionEvent> getCmd() {
        return cmd;
    }

    private void loaded() {
        hideProgressing();
        refreshed();
    }

    public void setCollectionType(int collectionType) {
        this.collectionType = collectionType;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        this.selectCategoryId = categoryId;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    void showLoading() {
        showProgressing();
    }
}
