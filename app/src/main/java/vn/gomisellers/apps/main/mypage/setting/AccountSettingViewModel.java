package vn.gomisellers.apps.main.mypage.setting;

import androidx.lifecycle.MutableLiveData;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.EappsApplication;
import vn.gomisellers.apps.R;
import vn.gomisellers.apps.data.AccountRepository;
import vn.gomisellers.apps.data.ResultListener;
import vn.gomisellers.apps.data.source.local.prefs.AppPreferences;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.SignOutRequest;
import vn.gomisellers.apps.data.source.model.data.Account;
import vn.gomisellers.apps.data.source.remote.ResultCode;
import vn.gomisellers.apps.event.MultableLiveEvent;
import vn.gomisellers.apps.utils.GomiConstants;

/**
 * Created by KHOI LE on 4/20/2020.
 */
public class AccountSettingViewModel extends BaseViewModel {

    private AccountRepository mAccountRepository;
    private AppPreferences mAppPreferences;

    public MutableLiveData<String> version;
    public MultableLiveEvent<AccountSettingEvent> cmd;

    public AccountSettingViewModel() {
        version = new MutableLiveData<>();
        mAccountRepository = AccountRepository.getInstance();
        mAppPreferences = EappsApplication.getPreferences();
        cmd = new MultableLiveEvent<>();
        version.setValue(GomiConstants.VERSION);
    }

    public void signOut() {
        cmd.call(new AccountSettingEvent(AccountSettingEvent.SIGN_OUT));
    }

    void requestSignOut() {
        SignOutRequest request = new SignOutRequest();
        mAccountRepository.logout(request, new ResultListener<ResponseData<Account>>() {
            @Override
            public void onLoaded(ResponseData<Account> result) {
                if (result.getCode() == ResultCode.CODE_OK) {
                    showToast(EappsApplication.getInstance().getString(R.string.logout));
                } else {
                    showToast(result.getMessage());
                }
            }

            @Override
            public void onDataNotAvailable(String error) {
                showToast(error);
            }
        });

        mAppPreferences.clear();
    }

    MultableLiveEvent<AccountSettingEvent> getCmd() {
        return cmd;
    }
}