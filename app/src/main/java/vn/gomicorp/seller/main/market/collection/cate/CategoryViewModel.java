package vn.gomicorp.seller.main.market.collection.cate;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.adapter.ProductItemAdapter;
import vn.gomicorp.seller.data.ProductRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.ShopRepository;
import vn.gomicorp.seller.data.source.model.api.CategoryByIdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.ToggleProductRequest;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.CategoryType;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.event.CategoryHandler;
import vn.gomicorp.seller.event.OnLoadMoreListener;
import vn.gomicorp.seller.event.ProductHandler;
import vn.gomicorp.seller.utils.ToastUtils;

/**
 * Created by KHOI LE on 4/7/2020.
 */
public class CategoryViewModel extends BaseViewModel implements CategoryHandler, ProductHandler, SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {
    private final int CODE_OK = 200;
    private final int ALL = 0;
    private final int INIT_PAGE = 1;

    private int page;
    private int totalPage;
    private int categoryId;
    private int selectCategoryType;
    private int selectCategoryId;
    private String selectCategoryName;

    private CategoryListener listener;

    private ProductRepository mProductRepository = ProductRepository.getInstance();
    private ShopRepository mShopRepository = ShopRepository.getInstance();

    public MutableLiveData<CategoryAdapter> categoryAdapterLiveData = new MutableLiveData<>();
    public MutableLiveData<ProductItemAdapter> productItemAdapter = new MutableLiveData<>();

    private ProductItemAdapter adapter;
    private List<Product> products = new ArrayList<>();

    private CategoryAdapter categoryAdapter;
    private List<Category> categories = new ArrayList<>();

    public CategoryViewModel() {
        loaded();
        page = INIT_PAGE;
        totalPage = 0;
        selectCategoryType = CategoryType.MEGA_CATEGORY;
        adapter = new ProductItemAdapter(products, this, this);
        productItemAdapter.setValue(adapter);
        categoryAdapter = new CategoryAdapter(categories, this);
        categoryAdapterLiveData.setValue(categoryAdapter);
    }

    @Override
    public void onClick(Category category) {
        categoryAdapter.selectItem(category.getId());
        if (category.getId() == ALL) {
            selectCategoryType = CategoryType.MEGA_CATEGORY;
            selectCategoryId = categoryId;
            showLoading();
            onRefresh();
        } else {
            selectCategoryType = CategoryType.CATEGORY;
            selectCategoryId = category.getId();
            selectCategoryName = category.getName();
            openSubCategory();
        }
    }

    private void openSubCategory() {
        listener.openCategory(selectCategoryType, selectCategoryId, selectCategoryName);
    }

    @Override
    public void onRefresh() {
        page = INIT_PAGE;
        products.clear();
        updateProductList();
        initCategory();
    }

    private void initCategory() {
        requestProductListByCategoryId();
    }

    @Override
    public void onLoadMore() {
        if (page >= totalPage) return;
        page++;
        loadMoreCategory();
    }

    private void loadMoreCategory() {
        requestProductListByCategoryId();
    }

    void requestCategory() {
        CategoryByIdRequest request = new CategoryByIdRequest();
        request.setCategoryType(CategoryType.MEGA_CATEGORY);
        request.setFindById(selectCategoryId);
        mShopRepository.findcatebytype(request, new ResultListener<ResponseData<List<Category>>>() {
            @Override
            public void onLoaded(ResponseData<List<Category>> result) {
                loaded();
                if (result.getCode() == CODE_OK) {
                    categories = result.getResult();
                    updateCategory();
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
            }
        });
    }

    void requestProductListByCategoryId() {
        adapter.setLoading();
        CategoryByIdRequest request = new CategoryByIdRequest();
        request.setCategoryType(selectCategoryType);
        request.setFindById(selectCategoryId);
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
        categoryAdapter.setCategoryList(categories);
        if (categories.size() > 0)
            categoryAdapter.selectItem(categories.get(0).getId());
    }

    private void updateProductList() {
        adapter.setProductList(products);
    }

    void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        this.selectCategoryId = categoryId;
    }

    public void setListener(CategoryListener listener) {
        this.listener = listener;
    }

    void showLoading() {
        showProgressing();
    }

    @Override
    public void onShow(Product product) {

    }

    @Override
    public void onPick(Product product) {
        pick(product);
    }


    private void pick(Product product) {
        listener.pick(product);
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
                if (result.getCode() == CODE_OK)
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
        ToastUtils.showToast(message);
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
}
