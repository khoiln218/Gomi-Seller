package vn.gomisellers.apps.shopinfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.theartofdev.edmodo.cropper.CropImage;

import java.util.List;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.EappsApplication;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.LocationRepository;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.ShopRepository;
import vn.gomisellers.apps.data.source.local.prefs.AppPreferences;
import vn.gomisellers.apps.data.source.model.api.CreateShopRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.ShopRequest;
import vn.gomisellers.apps.data.source.model.api.VerifyUrlRequest;
import vn.gomisellers.apps.data.source.model.data.Location;
import vn.gomisellers.apps.data.source.model.data.Shop;
import vn.gomisellers.apps.data.source.remote.ResultCode;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.utils.MediaHelper;

/**
 * Created by KHOI LE on 3/19/2020.
 */
public class ShopInformationViewModel extends BaseViewModel<ShopInfoEvent> {
    private static final int NONE_SELECT = -1;

    private ShopRepository mShopRepository;
    private LocationRepository mLocationRepository;
    private AppPreferences mAppPreferences;

    public MutableLiveData<Uri> coverUri;
    public MutableLiveData<String> shopName;
    public MutableLiveData<String> sellerUrl;
    public MutableLiveData<String> description;
    public MutableLiveData<String> countryName;
    public MutableLiveData<String> provinceName;
    public MutableLiveData<String> districtName;

    public MutableLiveData<String> coverUrl;
    public MutableLiveData<String> fullSellerUrl;
    public MutableLiveData<String> countDesc;

    public MutableLiveData<Boolean> enableBtnSubmit;
    public MutableLiveData<Boolean> verifyIsShow;
    public MutableLiveData<Boolean> checkOkIsShow;
    public MutableLiveData<String> errorShopName;
    public MutableLiveData<Boolean> enableErrorShopName;
    public MutableLiveData<String> errorUrl;
    public MutableLiveData<Boolean> enableErrorUrl;
    public MutableLiveData<Boolean> requestFocusUrl;
    public MutableLiveData<Boolean> requestFocusDes;
    public MutableLiveData<Boolean> isUpdateInfo;

    private List<Location> countries;
    private List<Location> provinces;
    private List<Location> districts;
    private Shop mShop;
    private Uri imageUri;
    private int selectCountry;
    private int selectProvince;
    private int selectDistrict;
    private boolean isUpdate;

    public ShopInformationViewModel() {
        mShopRepository = ShopRepository.getInstance();
        mLocationRepository = LocationRepository.getInstance();
        mAppPreferences = EappsApplication.getPreferences();

        coverUri = new MutableLiveData<>();
        shopName = new MutableLiveData<>();
        sellerUrl = new MutableLiveData<>();
        description = new MutableLiveData<>();
        countryName = new MutableLiveData<>();
        districtName = new MutableLiveData<>();
        provinceName = new MutableLiveData<>();

        coverUrl = new MutableLiveData<>();
        countDesc = new MutableLiveData<>();
        fullSellerUrl = new MutableLiveData<>();

        enableBtnSubmit = new MutableLiveData<>();
        checkOkIsShow = new MutableLiveData<>();
        verifyIsShow = new MutableLiveData<>();
        errorShopName = new MutableLiveData<>();
        enableErrorShopName = new MutableLiveData<>();
        errorUrl = new MutableLiveData<>();
        enableErrorUrl = new MutableLiveData<>();
        requestFocusUrl = new MutableLiveData<>();
        requestFocusDes = new MutableLiveData<>();
        isUpdateInfo = new MutableLiveData<>();

        showVerifyBtn();
        getFullSellerUrl();
        countDesc.setValue(String.format("%s/%s", 0, GomiConstants.MAX_CHAR));
        selectCountry = NONE_SELECT;
        selectProvince = NONE_SELECT;
        selectDistrict = NONE_SELECT;
    }

    public void verifyUrl() {
        submitUrlForm();
    }

    public void submit() {
        if (isUpdate) {
            submitUpdateShop();
        } else {
            submitCreateShop();
        }
    }

    private void submitUpdateShop() {
        if (sellerUrlEror())
            return;
        if (LengthDesOver())
            return;
        requestUpdateShop();
    }

    private void submitCreateShop() {
        if (sellerUrlEror())
            return;
        if (LengthDesOver())
            return;
        requestCreateShop();
    }

    private boolean LengthDesOver() {
        if (description.getValue() != null && description.getValue().length() > GomiConstants.MAX_CHAR) {
            requestFocusDes.setValue(true);
            return true;
        }
        return false;
    }

    private boolean sellerUrlEror() {
        String url = fullSellerUrl.getValue();
        if (!TextUtils.isEmpty(url) && !Patterns.WEB_URL.matcher(url).matches()) {
            urlError();
            return true;
        }
        return false;
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
                if (result.getCode() == ResultCode.CODE_OK) {
                    createSuccess(result.getResult());
                } else {
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                showToast(error);
            }
        });
    }

    private void requestUpdateShop() {
        updateSuccess();
    }

    private void updateSuccess() {
        getCmd().call(new ShopInfoEvent(ShopInfoEvent.UPDATE_SUCCESS));
    }

    private void getFullSellerUrl() {
        fullSellerUrl.setValue(String.format("%s%s", EappsApplication.getPreferences().getSellerUrl(), sellerUrl.getValue() == null ? "" : sellerUrl.getValue()));
    }

    public void changeCover() {
        requestPermission();
    }

    private void requestPermission() {
        getCmd().call(new ShopInfoEvent(ShopInfoEvent.REQUEST_PERMISSION));
    }

    private void saveShopInfo(Shop shop) {
        mAppPreferences.setShop(shop);
    }

    private void createSuccess(Shop shop) {
        saveShopInfo(shop);
        getCmd().call(new ShopInfoEvent(ShopInfoEvent.CREATE_SUCCESS));
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

    void requestShopInformation() {
        showProgressing();
        ShopRequest request = new ShopRequest();
        mShopRepository.findbyid(request, new ResultListener<ResponseData<Shop>>() {
            @Override
            public void onLoaded(ResponseData<Shop> result) {
                if (result.getCode() == ResultCode.CODE_OK) {
                    mShop = result.getResult();
                    saveShopInfo(mShop);
                    updateShopInformation();
                    requestLocationCountryId();
                } else {
                    loaded();
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                checkConnection(error);
            }
        });
    }

    private void updateShopInformation() {
        coverUrl.setValue(mShop.getCover());
        shopName.setValue(mShop.getShopName());
        sellerUrl.setValue(mShop.getWebAddress());
        description.setValue(mShop.getDescription());
        countryName.setValue(mShop.getCountryName());
        provinceName.setValue(mShop.getProvinceName());
        districtName.setValue(mShop.getDistrictName());
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
        this.coverUri.setValue(imageUri);
    }

    private void cropImage(Uri uri) {
        ShopInfoEvent<Uri> event = new ShopInfoEvent<>(ShopInfoEvent.START_CROPPER);
        event.setData(uri);
        getCmd().call(event);
    }

    private void requestShopVerifyUrl() {
        showProgressing();
        VerifyUrlRequest request = new VerifyUrlRequest();
        request.setWebAddress(sellerUrl.getValue());
        mShopRepository.verifySellerUrl(request, new ResultListener<ResponseData>() {
            @Override
            public void onLoaded(ResponseData result) {
                loaded();
                if (result.getCode() == ResultCode.CODE_OK) {
                    showCheckOk();
                } else {
                    urlError();
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                urlError();
                showToast(error);
            }
        });
    }

    public void afterUrlChanged() {
        showVerifyBtn();
        getFullSellerUrl();
        afterTextChanged();
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

    void setUpdate(boolean update) {
        isUpdate = update;
        isUpdateInfo.setValue(isUpdate);
    }

    void requestLocationCountryId() {
        showProgressing();
        clearCountry();
        mLocationRepository.getLocationCountry(new ResultListener<ResponseData<List<Location>>>() {
            @Override
            public void onLoaded(ResponseData<List<Location>> result) {
                if (result.getCode() == ResultCode.CODE_OK) {
                    countries = result.getResult();
                    if (selectCountry == NONE_SELECT && isUpdate) {
                        selectCountry = getPositionById(countries, mShop.getCountryId());
                    } else {
                        selectCountry = 0;
                    }
                    updateCountry();
                    requestLocationProvinceId(countries.get(selectCountry).getId());
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
        countries = null;
        countryName.setValue("");
    }

    private void updateCountry() {
        countryName.setValue(countries.get(selectCountry).getName());
    }

    private void requestLocationProvinceId(int countryId) {
        showProgressing();
        clearProvince();
        mLocationRepository.getLocationProvince(countryId, new ResultListener<ResponseData<List<Location>>>() {
            @Override
            public void onLoaded(ResponseData<List<Location>> result) {
                if (result.getCode() == ResultCode.CODE_OK) {
                    provinces = result.getResult();
                    if (selectProvince == NONE_SELECT && isUpdate) {
                        selectProvince = getPositionById(provinces, mShop.getProvinceId());
                    } else {
                        selectProvince = 0;
                    }
                    updateProvince();
                    requestLocationDistrictId(provinces.get(selectProvince).getId());
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
        provinces = null;
        provinceName.setValue("");
    }

    private void updateProvince() {
        provinceName.setValue(provinces.get(selectProvince).getName());
    }

    private void requestLocationDistrictId(int provinceId) {
        showProgressing();
        clearDistrict();
        mLocationRepository.getLocationDistrict(provinceId, new ResultListener<ResponseData<List<Location>>>() {
            @Override
            public void onLoaded(ResponseData<List<Location>> result) {
                loaded();
                if (result.getCode() == ResultCode.CODE_OK) {
                    districts = result.getResult();
                    if (selectDistrict == NONE_SELECT && isUpdate) {
                        selectDistrict = getPositionById(districts, mShop.getDistrictId());
                    } else {
                        selectDistrict = 0;
                    }
                    updateDistrict();
                } else {
                    clearDistrict();
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                loaded();
                clearDistrict();
            }
        });
    }

    private void clearDistrict() {
        districts = null;
        districtName.setValue("");
    }

    private void updateDistrict() {
        districtName.setValue(districts.get(selectDistrict).getName());
    }

    private int getPositionById(List<Location> locations, int countryId) {
        for (int i = 0; i < locations.size(); i++) {
            if (countryId == locations.get(i).getId()) {
                return i;
            }
        }
        return 0;
    }

    public void showCountryDialog() {
        if (countries == null || countries.size() == 0) return;
        ShopInfoEvent<LocationList> event = new ShopInfoEvent<>(ShopInfoEvent.SHOW_COUNTRY_DIALOG);
        event.setData(new LocationList(countries, selectCountry));
        getCmd().call(event);
    }

    public void showProvinceDialog() {
        if (provinces == null || provinces.size() == 0) return;
        ShopInfoEvent<LocationList> event = new ShopInfoEvent<>(ShopInfoEvent.SHOW_PROVINCE_DIALOG);
        event.setData(new LocationList(provinces, selectProvince));
        getCmd().call(event);
    }

    public void showDistrictDialog() {
        if (districts == null || districts.size() == 0) return;
        ShopInfoEvent<LocationList> event = new ShopInfoEvent<>(ShopInfoEvent.SHOW_DISTRICT_DIALOG);
        event.setData(new LocationList(districts, selectDistrict));
        getCmd().call(event);
    }

    void selectCountry(int position) {
        if (position != selectCountry) {
            selectCountry = position;
            requestLocationProvinceId(countries.get(selectCountry).getId());
            updateCountry();
            afterTextChanged();
        }
    }

    void selectProvince(int position) {
        if (position != selectProvince) {
            selectProvince = position;
            requestLocationDistrictId(provinces.get(selectProvince).getId());
            updateProvince();
            afterTextChanged();
        }
    }

    void selectDistrict(int position) {
        if (position != selectDistrict) {
            selectDistrict = position;
            updateDistrict();
            afterTextChanged();
        }
    }
}
