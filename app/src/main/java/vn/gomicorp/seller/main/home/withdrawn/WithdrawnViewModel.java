package vn.gomicorp.seller.main.home.withdrawn;

import vn.gomicorp.seller.BaseViewModel;

/**
 * Created by KHOI LE on 4/9/2020.
 */
public class WithdrawnViewModel extends BaseViewModel {
    private WithdrawnListener listener;

    public void setListener(WithdrawnListener listener) {
        this.listener = listener;
    }

    public void onBankClick() {
        requestBankAccount();
    }

    public void onCouponClick() {
        if (listener != null)
            listener.coupon();
    }

    void requestBankAccount() {
        if (listener != null)
            listener.bank();
    }
}
