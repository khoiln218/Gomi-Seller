package vn.gomicorp.seller.main.mypage;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.gomicorp.seller.EappsApplication;
import vn.gomicorp.seller.data.source.model.data.Account;

/**
 * Created by KHOI LE on 3/31/2020.
 */
public class MyPageViewModel extends ViewModel {
    public MutableLiveData<Account> account = new MutableLiveData<>();

    public MyPageViewModel() {
        Account account = new Account();
        account.setFullName(EappsApplication.getPreferences().getFullName());
        account.setUserName(EappsApplication.getPreferences().getUserName());
        account.setAvatar(EappsApplication.getPreferences().getAvatar());
        this.account.setValue(account);
    }
}
