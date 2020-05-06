package vn.gomisellers.apps.main.mypage.info;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.event.BaseEvent;

/**
 * Created by KHOI LE on 4/16/2020.
 */
public class AccountInformationViewModel extends BaseViewModel<BaseEvent> {
    void hideLoading() {
        hideProgressing();
    }

    void showLoading() {
        showProgressing();
    }
}
