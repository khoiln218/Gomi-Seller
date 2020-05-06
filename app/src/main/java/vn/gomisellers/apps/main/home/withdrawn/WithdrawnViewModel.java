package vn.gomisellers.apps.main.home.withdrawn;

import vn.gomisellers.apps.BaseViewModel;

/**
 * Created by KHOI LE on 4/9/2020.
 */
public class WithdrawnViewModel extends BaseViewModel<WithdrawEvent> {

    public void onBankClick() {
        requestBankAccount();
    }

    public void onCouponClick() {
        getCmd().call(new WithdrawEvent(WithdrawEvent.WITHDRAW_COUPON));
    }

    private void requestBankAccount() {
        getCmd().call(new WithdrawEvent(WithdrawEvent.WITHDRAW_BANK));
    }
}
