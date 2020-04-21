package vn.gomicorp.seller.main.home;

import androidx.lifecycle.MutableLiveData;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.adapter.ProductItemAdapter;
import vn.gomicorp.seller.data.ProductRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.ShopRepository;
import vn.gomicorp.seller.data.source.model.api.MegaCategoryRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.ShopRequest;
import vn.gomicorp.seller.data.source.model.api.ToggleProductRequest;
import vn.gomicorp.seller.data.source.model.data.Category;
import vn.gomicorp.seller.data.source.model.data.Product;
import vn.gomicorp.seller.data.source.model.data.Shop;
import vn.gomicorp.seller.data.source.remote.ResultCode;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.event.OnLoadMoreListener;
import vn.gomicorp.seller.event.ProductHandler;
import vn.gomicorp.seller.utils.ToastUtils;

/**
 * Created by KHOI LE on 3/30/2020.
 */
public class HomeViewModel extends BaseViewModel implements ProductHandler, OnLoadMoreListener, TabLayout.OnTabSelectedListener {
    private static final int INIT_PAGE = 1;
    private static final int ALL = 0;

    private ProductRepository mProductRepository;
    private ShopRepository mShopRepository;

    public MutableLiveData<Shop> shop;
    public MutableLiveData<List<Category>> categories;
    public MutableLiveData<ProductItemAdapter> productItemAdapter;

    private MultableLiveEvent<HomeEvent> cmd;

    private ProductItemAdapter adapter;
    private List<Product> products;
    private List<Category> categoryList;

    private int categoryId;
    private Shop mShop;
    private int page;
    private int totalPage;

    public HomeViewModel() {
        mShopRepository = ShopRepository.getInstance();
        mProductRepository = ProductRepository.getInstance();
        shop = new MutableLiveData<>();
        categories = new MutableLiveData<>();
        productItemAdapter = new MutableLiveData<>();
        cmd = new MultableLiveEvent<>();
        products = new ArrayList<>();
        categoryList = new ArrayList<>();
        adapter = new ProductItemAdapter(products, this, this);
        productItemAdapter.setValue(adapter);
        categoryId = ALL;
    }

    void onRefreshProduct() {
        page = INIT_PAGE;
        products.clear();
        updateProductList();

        showProgressing();
        if (mShop == null) {
            requestShopInformation();
        } else if (categoryList == null) {
            requestMegaCategory();
        } else {
            requestProduct();
        }
    }

    @Override
    public void onLoadMore() {
        if (page >= totalPage) return;
        page++;
        requestProduct();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() > categoryList.size())
            return;

        selectCategory(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        if (tab.getPosition() > categoryList.size())
            return;

        selectCategory(tab.getPosition());
    }

    private void selectCategory(int position) {
        Category selectedCategory = categoryList.get(position);
        categoryId = selectedCategory.getId();
        onRefreshProduct();
    }

    @Override
    public void onShow(Product product) {
        HomeEvent<Product> event = new HomeEvent<>(HomeEvent.SHOW_DETAIL);
        event.setData(product);
        cmd.call(event);
    }

    @Override
    public void onPick(Product product) {
        HomeEvent<Product> event = new HomeEvent<>(HomeEvent.REMOVE_PRODUCT);
        event.setData(product);
        cmd.call(event);
    }

    public void withdrawn() {
        cmd.call(new HomeEvent(HomeEvent.WITHDRAW));
    }

    public void shareSNS() {
        String content = String.format("%s%s", EappsApplication.getPreferences().getSellerUrl(), mShop.getWebAddress());
        HomeEvent<String> event = new HomeEvent<>(HomeEvent.SHARE_SNS);
        event.setData(content);
        cmd.call(event);
    }

    private void requestShopInformation() {
        ShopRequest request = new ShopRequest();
        mShopRepository.findbyid(request, new ResultListener<ResponseData<Shop>>() {
            @Override
            public void onLoaded(ResponseData<Shop> result) {
                if (result.getCode() == ResultCode.CODE_OK) {
                    mShop = result.getResult();
                    updateShopInformation();
                    requestMegaCategory();
                } else {
                    loaded();
                    ToastUtils.showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                checkConnection(error);
            }
        });
    }

    void requestRemoveProduct(Product product) {
        showProgressing();
        ToggleProductRequest request = new ToggleProductRequest();
        request.setIsSelling(product.getIsSelling());
        request.setProductId(product.getId());
        mProductRepository.select(request, new ResultListener<ResponseData<Product>>() {
            @Override
            public void onLoaded(ResponseData<Product> result) {
                hideProgressing();
                if (result.getCode() == ResultCode.CODE_OK)
                    removeProduct(result.getResult());
                else
                    removeFailure(result.getMessage());
            }

            @Override
            public void onDataNotAvailable(String error) {
                hideProgressing();
                removeFailure(error);
            }
        });
    }

    private void updateShopInformation() {
        EappsApplication.getPreferences().setShop(mShop);
        shop.setValue(mShop);
    }


    private void removeFailure(String message) {
        ToastUtils.showToast(message);
    }

    private void removeProduct(Product product) {
        adapter.notifyItemRemoved(product);
    }

    private void requestMegaCategory() {
        final MegaCategoryRequest request = new MegaCategoryRequest();
        mShopRepository.megacategory(request, new ResultListener<ResponseData<List<Category>>>() {
            @Override
            public void onLoaded(ResponseData<List<Category>> result) {
                if (result.getCode() == ResultCode.CODE_OK) {
                    categoryList = result.getResult();
                    updateMegaCategory();
                    requestProduct();
                } else {
                    loaded();
                    ToastUtils.showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                checkConnection(error);
            }
        });
    }

    private void updateMegaCategory() {
        categories.setValue(categoryList);
    }

    private void requestProduct() {
        adapter.setLoading();
        final ShopRequest request = new ShopRequest();
        request.setFindById(categoryId);
        mProductRepository.findbyshop(request, page, new ResultListener<ResponseData<List<Product>>>() {
            @Override
            public void onLoaded(ResponseData<List<Product>> result) {
                loaded();
                if (result.getCode() == ResultCode.CODE_OK) {
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

    private void updateProductList() {
        adapter.setProductList(products);
    }

    MultableLiveEvent<HomeEvent> getCmd() {
        return cmd;
    }
}
