package vn.gomisellers.apps.main.mypage.info;

import vn.gomisellers.apps.BaseViewModel;

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
