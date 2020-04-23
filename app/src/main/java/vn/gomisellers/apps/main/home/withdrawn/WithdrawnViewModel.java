package vn.gomisellers.apps.main.home.withdrawn;

import vn.gomisellers.apps.BaseViewModel;
import vn.gomisellers.apps.event.MultableLiveEvent;

/**
 * Created by KHOI LE on 4/9/2020.
 */
public class WithdrawnViewModel extends BaseViewModel {

    private MultableLiveEvent<WithdrawEvent> cmd;

    public WithdrawnViewModel() {
        cmd = new MultableLiveEvent<>();
    }

    public void onBankClick() {
        requestBankAccount();
    }

    public void onCouponClick() {
        cmd.call(new WithdrawEvent(WithdrawEvent.WITHDRAW_COUPON));
    }

    private void requestBankAccount() {
        cmd.call(new WithdrawEvent(WithdrawEvent.WITHDRAW_BANK));
    }

    MultableLiveEvent<WithdrawEvent> getCmd() {
        return cmd;
    }
}
