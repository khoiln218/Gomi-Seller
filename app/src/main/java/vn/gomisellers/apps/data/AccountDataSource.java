package vn.gomisellers.apps.data;

import vn.gomisellers.apps.data.source.model.api.AccountChangePasswordRequest;
import vn.gomisellers.apps.data.source.model.api.AccountRequest;
import vn.gomisellers.apps.data.source.model.api.AccountUpdateRequest;
import vn.gomisellers.apps.data.source.model.api.ChangeAvatarRequest;
import vn.gomisellers.apps.data.source.model.api.ForgetPwdRequest;
import vn.gomisellers.apps.data.source.model.api.ResetPwdRequest;
import vn.gomisellers.apps.data.source.model.api.ResponseData;
import vn.gomisellers.apps.data.source.model.api.SignInRequest;
import vn.gomisellers.apps.data.source.model.api.SignOutRequest;
import vn.gomisellers.apps.data.source.model.api.SignUpRequest;
import vn.gomisellers.apps.data.source.model.api.VerifyPhoneNumberRequest;
import vn.gomisellers.apps.data.source.model.data.Account;

/**
 * Created by KHOI LE on 3/12/2020.
 */
public interface AccountDataSource {
    void signin(SignInRequest request, ResultListener<ResponseData<Account>> callback);

    void signup(SignUpRequest request, ResultListener<ResponseData<Account>> callback);

    void forgetPwd(ForgetPwdRequest request, ResultListener<ResponseData<Account>> callback);

    void resetPwd(ResetPwdRequest request, ResultListener<ResponseData<Account>> callback);

    void verifyPhoneNumber(VerifyPhoneNumberRequest request, ResultListener<ResponseData<Account>> callback);

    void findbyid(AccountRequest request, ResultListener<ResponseData<Account>> callback);

    void updateinfo(AccountUpdateRequest request, ResultListener<ResponseData<Account>> callback);

    void changepassword(AccountChangePasswordRequest request, ResultListener<ResponseData<Account>> callback);

    void changeavatar(ChangeAvatarRequest request, ResultListener<ResponseData<Account>> callback);

    void logout(SignOutRequest request, ResultListener<ResponseData<Account>> callback);
}
