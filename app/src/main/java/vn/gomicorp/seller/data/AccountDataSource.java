package vn.gomicorp.seller.data;

import vn.gomicorp.seller.data.source.model.api.ForgetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.data.Account;

/**
 * Created by KHOI LE on 3/12/2020.
 */
public interface AccountDataSource {
    void signin(SignInRequest request, ResultListener<Account> callback);

    void signup(SignUpRequest request, ResultListener<Account> callback);

    void forgetPwd(ForgetPwdRequest request, ResultListener<Account> callback);

    void resetPwd(ResetPwdRequest request, ResultListener<Void> callback);
}
