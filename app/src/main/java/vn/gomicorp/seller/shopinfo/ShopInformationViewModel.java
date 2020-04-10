package vn.gomicorp.seller.shopinfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.List;

import vn.gomicorp.seller.BaseViewModel;
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

/**
 * Created by KHOI LE on 3/19/2020.
 */
public class ShopInformationViewModel extends BaseViewModel {
    private static final int CODE_OK = 200;
    private static final int NONE_SELECT = -1;

    private ShopRepository mShopRepository = ShopRepository.getInstance();
    private LocationRepository mLocationRepository = LocationRepository.getInstance();
    private AppPreferences mAppPreferences = EappsApplication.getPreferences();

    public MutableLiveData<String> sellerUrl = new MutableLiveData<>();
    public MutableLiveData<String> fullSellerUrl = new MutableLiveData<>();
    public MutableLiveData<Boolean> checkOkIsShow = new MutableLiveData<>();
    public MutableLiveData<String> shopName = new MutableLiveData<>();
    public MutableLiveData<String> description = new MutableLiveData<>();
    public MutableLiveData<String> countDesc = new MutableLiveData<>();

    public MutableLiveData<Boolean> enableBtnSubmit = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestFocusUrl = new MutableLiveData<>();
    public MutableLiveData<Boolean> requestFocusDes = new MutableLiveData<>();
    public MutableLiveData<Boolean> verifyIsShow = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableErrorUrl = new MutableLiveData<>();
    public MutableLiveData<Boolean> enableErrorShopName = new MutableLiveData<>();
    public MutableLiveData<String> errorUrl = new MutableLiveData<>();
    public MutableLiveData<String> errorShopName = new MutableLiveData<>();

    public MutableLiveData<Uri> coverUrl = new MutableLiveData<>();

    public MutableLiveData<LocationAdapter> countryAdapterMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<LocationAdapter> provinceAdapterMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<LocationAdapter> districtAdapterMutableLiveData = new MutableLiveData<>();

    private MultableLiveEvent<ShopInfoEvent> cmd = new MultableLiveEvent<>();

    private List<Location> countries;
    private List<Location> provinces;
    private List<Location> districts;
    private LocationAdapter countryAdapter;
    private LocationAdapter provinceAdapter;
    private LocationAdapter districtAdapter;

    private Uri imageUri;
    private int selectCountry;
    private int selectProvince;
    private int selectDistrict;

    public ShopInformationViewModel() {
        showVerifyBtn();
        countries = new ArrayList<>();
        countryAdapter = new LocationAdapter(countries);
        countryAdapterMutableLiveData.setValue(countryAdapter);
        provinces = new ArrayList<>();
        provinceAdapter = new LocationAdapter(provinces);
        provinceAdapterMutableLiveData.setValue(provinceAdapter);
        districts = new ArrayList<>();
        districtAdapter = new LocationAdapter(districts);
        districtAdapterMutableLiveData.setValue(districtAdapter);
        getFullSellerUrl();
        countDesc.setValue(String.format("%s/%s", 0, GomiConstants.MAX_CHAR));
    }

    public void verifyUrl() {
        submitUrlForm();
    }

    public void createShop() {
        submitCreateShop();
    }

    private void submitCreateShop() {
        if (!validateSellerUrl())
            return;
        if (!checkLenghtDes())
            return;
        requestCreateShop();
    }

    private boolean checkLenghtDes() {
        if (description.getValue() != null && description.getValue().length() > GomiConstants.MAX_CHAR) {
            requestFocusDes.setValue(true);
            return false;
        }
        return true;
    }

    private boolean validateSellerUrl() {
        String url = fullSellerUrl.getValue();
        if (!TextUtils.isEmpty(url) && !Patterns.WEB_URL.matcher(url).matches()) {
            urlError();
            return false;
        }
        return true;
    }

    private void requestCreateShop() {
        showProgressing();
        CreateShopRequest request = new CreateShopRequest();
        request.setShopName(shopName.getValue());
        request.setCountryId(countries.get(selectCountry).getId());
        request.setProvinceId(provinces.get(selectProvince).getId());
        request.setDistrictId(districts.get(selectDistrict).getId());
        request.setWebAddress(sellerUrl.getValue());
        String des = description.getValue();
        request.setDescription(des == null ? "" : des);
        if (imageUri != null)
            request.setCover(MediaHelper.getBase64FromImageUri(EappsApplication.getInstance(), imageUri, 1024, 1024));
        mShopRepository.create(request, new ResultListener<ResponseData<Shop>>() {
            @Override
            public void onLoaded(ResponseData<Shop> result) {
                loaded();
                if (result.getCode() == CODE_OK) {
                    createSuccess(result.getResult());
                } else {
                    createShopError(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                createShopError(error);
            }
        });
    }

    private void getFullSellerUrl() {
        fullSellerUrl.setValue(String.format("%s%s", EappsApplication.getPreferences().getSellerUrl(), sellerUrl.getValue() == null ? "" : sellerUrl.getValue()));
    }

    public void changeCover() {
        requestPermission();
    }

    private void requestPermission() {
        cmd.call(new ShopInfoEvent(ShopInfoEvent.REQUEST_PERMISSION));
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
        showProgressing();
        clearCountry();
        mLocationRepository.getLocationCountry(new ResultListener<ResponseData<List<Location>>>() {
            @Override
            public void onLoaded(ResponseData<List<Location>> result) {
                if (result.getCode() == CODE_OK) {
                    int id = result.getResult().get(0).getId();
                    countries.addAll(result.getResult());
                    updateCountry();
                    requestLocationProvinceId(id);
                } else {
                    loaded();
                    clearCountry();
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                clearCountry();
            }
        });
    }

    private void clearCountry() {
        clearProvince();
        selectCountry = NONE_SELECT;
        countries.clear();
        updateCountry();
    }

    private void updateCountry() {
        countryAdapter.setData(countries);
    }

    private void requestLocationProvinceId(int countryId) {
        clearProvince();
        mLocationRepository.getLocationProvince(countryId, new ResultListener<ResponseData<List<Location>>>() {
            @Override
            public void onLoaded(ResponseData<List<Location>> result) {
                if (result.getCode() == CODE_OK) {
                    int id = result.getResult().get(0).getId();
                    provinces.addAll(result.getResult());
                    updateProvince();
                    requestLocationDistrictId(id);
                } else {
                    loaded();
                    clearProvince();
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                clearProvince();
            }
        });
    }

    private void clearProvince() {
        clearDistrict();
        selectProvince = NONE_SELECT;
        provinces.clear();
        updateProvince();
    }

    private void updateProvince() {
        provinceAdapter.setData(provinces);
    }

    private void requestLocationDistrictId(int provinceId) {
        clearDistrict();
        mLocationRepository.getLocationDistrict(provinceId, new ResultListener<ResponseData<List<Location>>>() {
            @Override
            public void onLoaded(ResponseData<List<Location>> result) {
                loaded();
                if (result.getCode() == CODE_OK) {
                    districts.addAll(result.getResult());
                    updateDistrict();
                } else
                    clearDistrict();
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                clearDistrict();
            }
        });
    }

    private void clearDistrict() {
        selectDistrict = NONE_SELECT;
        districts.clear();
        updateDistrict();
    }

    private void updateDistrict() {
        districtAdapter.setData(districts);
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
                        setCover(result.getUri());
                    } else {
                        setCover(null);
                    }
                    break;
            }
        } else {
            switch (requestCode) {
                case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                case GomiConstants.REQUEST_CAMERA:
                case GomiConstants.REQUEST_GALLERY:
                    setCover(null);
                    break;
            }
        }
    }

    void setCover(Uri coverUrl) {
        imageUri = coverUrl;
        this.coverUrl.setValue(imageUri);
    }

    private void cropImage(Uri uri) {
        ShopInfoEvent<Uri> event = new ShopInfoEvent<>(ShopInfoEvent.START_CROPPER);
        event.setData(uri);
        cmd.call(event);
    }

    private void requestShopVerifyUrl() {
        showProgressing();
        VerifyUrlRequest request = new VerifyUrlRequest();
        request.setWebAddress(sellerUrl.getValue());
        mShopRepository.verifySellerUrl(request, new ResultListener<ResponseData>() {
            @Override
            public void onLoaded(ResponseData result) {
                loaded();
                if (result.getCode() == CODE_OK) {
                    showCheckOk();
                } else {
                    urlError();
                    verifyError(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
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
        getFullSellerUrl();
        afterTextChanged();
    }

    MultableLiveEvent<ShopInfoEvent> getCmd() {
        return cmd;
    }

    public void afterTextChanged() {
        if (validateShopName() && validateUrl() && validateLocation())
            enableBtnSubmit.setValue(true);
        else
            enableBtnSubmit.setValue(false);
    }

    public void descriptionTextChange() {
        if (description.getValue() == null) return;
        countDesc.setValue(String.format("%s/%s", description.getValue().length(), GomiConstants.MAX_CHAR));
    }

    private boolean validateLocation() {
        return selectCountry != NONE_SELECT && selectProvince != NONE_SELECT && selectDistrict != NONE_SELECT;
    }

    private boolean validateUrl() {
        return !TextUtils.isEmpty(sellerUrl.getValue());
    }

    private boolean validateShopName() {
        return !TextUtils.isEmpty(shopName.getValue());
    }

    public void selectCountry(int position) {
        if (countries.size() == 0) return;
        selectCountry = position;
        requestLocationProvinceId(countries.get(selectCountry).getId());
        afterTextChanged();
    }


    public boolean onTouchCountry(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (countries.size() == 0)
                requestLocationCountryId();
        }
        return false;
    }


    public void selectProvince(int position) {
        if (provinces.size() == 0) return;
        selectProvince = position;
        requestLocationDistrictId(provinces.get(selectProvince).getId());
        afterTextChanged();
    }


    public boolean onTouchProvince(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (provinces.size() == 0 && selectCountry != NONE_SELECT)
                requestLocationProvinceId(countries.get(selectCountry).getId());
        }
        return false;
    }


    public void selectDistrict(int position) {
        if (districts.size() == 0) return;
        selectDistrict = position;
        afterTextChanged();
    }

    public boolean onTouchDistrict(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (districts.size() == 0 && selectProvince != NONE_SELECT)
                requestLocationDistrictId(provinces.get(selectProvince).getId());
        }
        return false;
    }
}
