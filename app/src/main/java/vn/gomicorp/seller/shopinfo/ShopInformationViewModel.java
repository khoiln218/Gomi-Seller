package vn.gomicorp.seller.shopinfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.R;
import vn.gomicorp.seller.adapter.LocationAdapter;
import vn.gomicorp.seller.data.LocationRepository;
import vn.gomicorp.seller.data.ResultListener;
import vn.gomicorp.seller.data.ShopRepository;
import vn.gomicorp.seller.data.source.local.prefs.AppPreferences;
import vn.gomicorp.seller.data.source.model.api.CreateShopRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.VerifyUrlRequest;
import vn.gomicorp.seller.data.source.model.data.Location;
import vn.gomicorp.seller.data.source.model.data.Shop;
import vn.gomicorp.seller.event.MultableLiveEvent;
import vn.gomicorp.seller.utils.GomiConstants;
import vn.gomicorp.seller.utils.MediaHelper;
import vn.gomicorp.seller.utils.Utils;

/**
 * Created by KHOI LE on 3/19/2020.
 */
public class ShopInformationViewModel extends ViewModel {
    private static final int CODE_OK = 200;
    private static final int NONE_SELECT = -1;

    private ShopRepository mShopRepository = ShopRepository.getInstance();
    private LocationRepository mLocationRepository = LocationRepository.getInstance();
    private AppPreferences mAppPreferences = EappsApplication.getPreferences();

    public MutableLiveData<List<Location>> countries = new MutableLiveData<>();
    public MutableLiveData<List<Location>> provinces = new MutableLiveData<>();
    public MutableLiveData<List<Location>> districts = new MutableLiveData<>();

    @SuppressLint("StaticFieldLeak")
    private static LocationAdapter countryAdapter;
    @SuppressLint("StaticFieldLeak")
    private static LocationAdapter provinceAdapter;
    @SuppressLint("StaticFieldLeak")
    private static LocationAdapter districtAdapter;

    public MutableLiveData<String> sellerUrl = new MutableLiveData<>();
    public MutableLiveData<Boolean> verifyIsShow = new MutableLiveData<>();
    public MutableLiveData<Boolean> checkOkIsShow = new MutableLiveData<>();

    public MutableLiveData<String> shopName = new MutableLiveData<>();
    public MutableLiveData<String> description = new MutableLiveData<>();
    private Uri imageUri;

    MultableLiveEvent<ShopInfoEvent> getCmd() {
        return cmd;
    }

    private MultableLiveEvent<ShopInfoEvent> cmd = new MultableLiveEvent<>();

    public ShopInformationViewModel() {
        showVerifyBtn();
    }

    public void verifyUrl() {
        submitUrlForm();
    }

    public void createShop() {
        submitCreateShop();
    }

    private void submitCreateShop() {
        requestCreateShop();
    }

    private void requestCreateShop() {
        final CreateShopRequest request = new CreateShopRequest();
        request.setShopName(shopName.getValue());
        request.setCountryId(countryId);
        request.setProvinceId(provinceId);
        request.setDistrictId(districtId);
        request.setWebAddress(sellerUrl.getValue());
        String des = description.getValue();
        request.setDescription(des == null ? "" : des);
        if (imageUri != null)
            request.setCover(MediaHelper.getBase64FromImageUri(EappsApplication.getInstance(), imageUri, 1024, 1024));
        mShopRepository.create(request, new ResultListener<ResponseData<Shop>>() {
            @Override
            public void onLoaded(ResponseData<Shop> result) {
                if (result.getCode() == CODE_OK) {
                    createSuccess(result.getResult());
                } else {
                    createShopError(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                createShopError(error);
            }
        });
    }

    public void changeCover() {
        requestPermission();
    }

    //TODO: implement permission
    private void requestPermission() {
        cmd.call(new ShopInfoEvent(ShopInfoEvent.SHOW_IMAGE_OPTION));
    }

    private void saveShopInfo(Shop shop) {
        mAppPreferences.setShop(shop);
    }

    private void createShopError(String error) {
        cmd.call(new ShopInfoEvent(ShopInfoEvent.CREATE_ERROR, error));
    }

    private void createSuccess(Shop shop) {
        saveShopInfo(shop);
        cmd.call(new ShopInfoEvent(ShopInfoEvent.CREATE_SUCCESS));
    }

    private void submitUrlForm() {
        if (!TextUtils.isEmpty(sellerUrl.getValue())) {
            requestShopVerifyUrl();
            enableErrorUrl.setValue(false);
        } else
            urlError();
    }

    private void urlError() {
        errorUrl.setValue(EappsApplication.getInstance().getString(R.string.err_input_url));
        requestFocusUrl.setValue(true);
    }

    private void showVerifyBtn() {
        verifyIsShow.setValue(true);
        checkOkIsShow.setValue(false);
    }

    private void showCheckOk() {
        verifyIsShow.setValue(false);
        checkOkIsShow.setValue(true);
    }

    void requestLocationCountryId() {
        clearCountry();
        mLocationRepository.getLocationCountry(new ResultListener<ResponseData<List<Location>>>() {
            @Override
            public void onLoaded(ResponseData<List<Location>> result) {
                if (result.getCode() == CODE_OK) {
                    int id = result.getResult().get(0).getId();
                    updateCountry(result.getResult());
                    requestLocationProvinceId(id);
                } else
                    clearCountry();
            }

            @Override
            public void onDataNotAvailable(String error) {
                clearCountry();
            }
        });
    }

    private void clearCountry() {
        clearProvince();
        countryId = NONE_SELECT;
        updateCountry(new ArrayList<Location>());
    }

    private void updateCountry(List<Location> result) {
        countries.postValue(result);
    }

    private void requestLocationProvinceId(int countryId) {
        clearProvince();
        mLocationRepository.getLocationProvince(countryId, new ResultListener<ResponseData<List<Location>>>() {
            @Override
            public void onLoaded(ResponseData<List<Location>> result) {
                if (result.getCode() == CODE_OK) {
                    int id = result.getResult().get(0).getId();
                    updateProvince(result.getResult());
                    requestLocationDistrictId(id);
                } else
                    clearProvince();
            }

            @Override
            public void onDataNotAvailable(String error) {
                clearProvince();
            }
        });
    }

    private void clearProvince() {
        clearDistrict();
        provinceId = NONE_SELECT;
        updateProvince(new ArrayList<Location>());
    }

    private void updateProvince(List<Location> result) {
        provinces.postValue(result);
    }

    private void requestLocationDistrictId(int provinceId) {
        clearDistrict();
        mLocationRepository.getLocationDistrict(provinceId, new ResultListener<ResponseData<List<Location>>>() {
            @Override
            public void onLoaded(ResponseData<List<Location>> result) {
                if (result.getCode() == CODE_OK) {
                    updateDistrict(result.getResult());
                } else
                    clearDistrict();
            }

            @Override
            public void onDataNotAvailable(String error) {
                clearDistrict();
            }
        });
    }

    private void clearDistrict() {
        districtId = NONE_SELECT;
        updateDistrict(new ArrayList<Location>());
    }

    private void updateDistrict(List<Location> result) {
        districts.postValue(result);
    }

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GomiConstants.REQUEST_CAMERA:

                    cropImage(imageUri);
                    break;

                case GomiConstants.REQUEST_GALLERY:

                    if (data != null)
                        cropImage(data.getData());
                    break;

                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:

                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    if (result != null && result.getUri() != null) {
                        imageUri = result.getUri();
                        setCover(imageUri);
                    }
                    break;
            }
        }
    }

    void setCover(Uri coverUrl) {
        this.coverUrl.setValue(coverUrl);
    }

    public MutableLiveData<Uri> coverUrl = new MutableLiveData<>();

    private void cropImage(Uri uri) {
        ShopInfoEvent<Uri> event = new ShopInfoEvent<>(ShopInfoEvent.START_CROPPER);
        event.setData(uri);
        cmd.call(event);
    }

    private void requestShopVerifyUrl() {
        VerifyUrlRequest request = new VerifyUrlRequest();
        request.setWebAddress(sellerUrl.getValue());
        mShopRepository.verifySellerUrl(request, new ResultListener<ResponseData>() {
            @Override
            public void onLoaded(ResponseData result) {
                if (result.getCode() == CODE_OK) {
                    showCheckOk();
                } else {
                    urlError();
                    verifyError(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                urlError();
                verifyError(error);
            }
        });
    }

    private void verifyError(String error) {
        cmd.call(new ShopInfoEvent(ShopInfoEvent.VERIFY_ERROR, error));
    }

    public void afterUrlChanged() {
        showVerifyBtn();
        afterTextChanged();
    }

    public MutableLiveData<Boolean> enableBtnSubmit = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestFocusUrl = new MutableLiveData<>();

    public void afterTextChanged() {
        if (validateShopName() && validateUrl() && validateLocation())
            enableBtnSubmit.setValue(true);
        else
            enableBtnSubmit.setValue(false);
    }

    private boolean validateLocation() {
        return countryId != NONE_SELECT && provinceId != NONE_SELECT && districtId != NONE_SELECT;
    }

    private boolean validateUrl() {
        return !TextUtils.isEmpty(sellerUrl.getValue());
    }

    private boolean validateShopName() {
        return !TextUtils.isEmpty(shopName.getValue());
    }

    private int countryId;
    private int provinceId;
    private int districtId;

    public OnItemSelectedListener countryItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(int position) {
            if (countries.getValue().size() == 0) return;
            countryId = countries.getValue().get(position).getId();
            requestLocationProvinceId(countryId);
            afterTextChanged();
        }
    };

    public OnTouchListener countryTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (countries.getValue() == null || countries.getValue().size() == 0)
                    requestLocationCountryId();
            }
            return false;
        }
    };

    public OnItemSelectedListener provinceItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(int position) {
            if (provinces.getValue().size() == 0) return;
            provinceId = provinces.getValue().get(position).getId();
            requestLocationDistrictId(provinceId);
            afterTextChanged();
        }
    };

    public OnTouchListener provinceTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (provinces.getValue() == null || provinces.getValue().size() == 0)
                    requestLocationProvinceId(countryId);
            }
            return false;
        }
    };

    public OnItemSelectedListener districtItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(int position) {
            if (districts.getValue().size() == 0) return;
            districtId = districts.getValue().get(position).getId();
            afterTextChanged();
        }
    };

    public OnTouchListener districtTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (districts.getValue() == null || districts.getValue().size() == 0)
                    requestLocationDistrictId(provinceId);
            }
            return false;
        }
    };

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    void releaseAdapter() {
        countryAdapter = null;
        provinceAdapter = null;
        districtAdapter = null;
    }

    public interface OnTouchListener {
        boolean onTouch(MotionEvent event);
    }

    @BindingAdapter("countries")
    public static void setAdapterCountry(Spinner spinner, List<Location> locations) {
        if (countryAdapter == null) {
            countryAdapter = new LocationAdapter(spinner.getContext(), new ArrayList<Location>());
            spinner.setAdapter(countryAdapter);
        } else {
            countryAdapter.setData(locations);
            countryAdapter.notifyDataSetChanged();
        }
    }

    @BindingAdapter("provinces")
    public static void setAdapterProvince(Spinner spinner, List<Location> locations) {
        if (provinceAdapter == null) {
            provinceAdapter = new LocationAdapter(spinner.getContext(), new ArrayList<Location>());
            spinner.setAdapter(provinceAdapter);
        } else {
            provinceAdapter.setData(locations);
            provinceAdapter.notifyDataSetChanged();
        }
    }

    @BindingAdapter("districts")
    public static void setAdapterDistrict(Spinner spinner, List<Location> locations) {
        if (districtAdapter == null) {
            districtAdapter = new LocationAdapter(spinner.getContext(), new ArrayList<Location>());
            spinner.setAdapter(districtAdapter);
        } else {
            districtAdapter.setData(locations);
            districtAdapter.notifyDataSetChanged();
        }
    }

    public MutableLiveData<Boolean> enableErrorUrl = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableErrorShopName = new MutableLiveData<>();

    public MutableLiveData<String> errorUrl = new MutableLiveData<>();
    public MutableLiveData<String> errorShopName = new MutableLiveData<>();

    @BindingAdapter("setImageView")
    public static void loadImage(ImageView view, Uri imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.img_home_banner)
                        .override(Utils.getScreenWidth())
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true))
                .into(view);
    }
}
