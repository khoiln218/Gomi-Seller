package vn.gomicorp.seller.main.mypage.info;

import vn.gomicorp.seller.BaseViewModel;

/**
 * Created by KHOI LE on 4/16/2020.
 */
public class AccountInformationViewModel extends BaseViewModel {
    void hideLoading() {
        hideProgressing();
    }

    void showLoading() {
        showProgressing();
    }
}
