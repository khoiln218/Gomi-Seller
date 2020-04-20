package vn.gomicorp.seller.main.mypage.setting;

import androidx.lifecycle.MutableLiveData;

import vn.gomicorp.seller.BaseViewModel;
import vn.gomicorp.seller.utils.GomiConstants;

/**
 * Created by KHOI LE on 4/20/2020.
 */
public class AccountSettingViewModel extends BaseViewModel {
    public MutableLiveData<String> version;

    public AccountSettingViewModel() {
        version = new MutableLiveData<>();
        version.setValue(GomiConstants.VERSION);
    }

    public void signOut() {

    }
}
