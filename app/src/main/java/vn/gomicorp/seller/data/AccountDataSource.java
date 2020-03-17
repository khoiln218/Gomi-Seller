package vn.gomicorp.seller.data;

import vn.gomicorp.seller.data.source.model.api.ForgetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResetPwdRequest;
import vn.gomicorp.seller.data.source.model.api.ResponseData;
import vn.gomicorp.seller.data.source.model.api.SignInRequest;
import vn.gomicorp.seller.data.source.model.api.SignUpRequest;
import vn.gomicorp.seller.data.source.model.api.VerifyPhoneNumberRequest;
import vn.gomicorp.seller.data.source.model.data.Account;

/**
 * Created by KHOI LE on 3/12/2020.
 */
public interface AccountDataSource {
    void signin(SignInRequest request, ResultListener<ResponseData<Account>> callback);

    void signup(SignUpRequest request, ResultListener<ResponseData<Account>> callback);

    void forgetPwd(ForgetPwdRequest request, ResultListener<ResponseData<Account>> callback);

    void resetPwd(ResetPwdRequest request, ResultListener<ResponseData<Account>> callback);

    void verifyPhoneNumber(VerifyPhoneNumberRequest request, ResultListener<ResponseData<Account>> callback);
}
